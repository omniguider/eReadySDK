package com.omni.ereadysdk;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.omni.ereadysdk.module.OmniEvent;
import com.omni.ereadysdk.module.point.PointData;
import com.omni.ereadysdk.module.point.PointInfo;
import com.omni.ereadysdk.network.NetworkManager;
import com.omni.ereadysdk.network.api.eReadySDKAPI;
import com.omni.ereadysdk.service.OGService;
import com.omni.ereadysdk.tool.DialogTools;
import com.omni.ereadysdk.tool.PreferencesTools;
import com.omni.ereadysdk.tool.Tools;
import com.omni.ereadysdk.tool.eReadyText;
import com.omni.ereadysdk.view.CircleNetworkImageView;
import com.omni.ereadysdk.view.OmniEditText;
import com.omni.ereadysdk.view.items.OmniClusterItem;
import com.omni.ereadysdk.view.navi.OmniClusterRender;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class eReadySDKActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowCloseListener {

    private SupportMapFragment mMapFragment;
    private boolean mIsMapInited = false;
    private GoogleMap mMap;
    private ClusterManager<OmniClusterItem> mClusterManager;
    private OmniClusterRender mOmniClusterRender;
    private OmniClusterItem mOmniClusterItem;
    private Location mLastLocation;
    private Marker mUserMarker;
    private Circle mUserAccuracyCircle;
    private ImageView currentPositionIV;
    private EventBus mEventBus;
    private OGService mOGService;
    private RelativeLayout mPOIInfoHeaderLayout;
    private BottomSheetBehavior mBottomSheetBehavior;
    private FrameLayout mPOIInfoLayout;
    private CircleNetworkImageView mPOIInfoIconNIV;
    private TextView mPOIInfoTitleTV;

    private List<OmniClusterItem> itemList;
    private PointData mPointData;
    private PointInfo[] poiData;
    private String a_id = "";
    private String keyword = "";
    private int current_tab = 0;
    private AutofitTextView religion_tv, food_tv, view_tv, shopping_tv, hotel_tv, traffic_tv;
    private OmniEditText searchOET;
    private View mView;
    private Double START_LAT = 24.9527174;
    private Double START_LNG = 121.3393569;
    private LatLng latLng;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OmniEvent event) {
        switch (event.getType()) {
            case OmniEvent.TYPE_USER_LOCATION:
                mLastLocation = (Location) event.getObj();
                showUserPosition();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ereadysdk);
        mView = findViewById(android.R.id.content);

        if (mEventBus == null) {
            mEventBus = EventBus.getDefault();
        }
        mEventBus.register(this);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_point_map_map);

        findViewById(R.id.fragment_point_map_action_bar_back_fl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        currentPositionIV = findViewById(R.id.fragment_point_map_fab_current_position);
        currentPositionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null && mLastLocation != null) {
                    final LatLng current = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    addUserMarker(current, mLastLocation);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, eReadyText.MAP_ZOOM_LEVEL));
                        }
                    }, 1000);
                }
            }
        });

        checkLocationService();

        mPOIInfoLayout = findViewById(R.id.fragment_point_map_poi_info);
        mPOIInfoHeaderLayout = mPOIInfoLayout.findViewById(R.id.poi_info_view_header);
        mPOIInfoIconNIV = mPOIInfoLayout.findViewById(R.id.item_poi_header_niv);
        mPOIInfoTitleTV = mPOIInfoLayout.findViewById(R.id.poi_info_header_view_tv_title);

        FrameLayout fl = (FrameLayout) mPOIInfoLayout.getParent();
        mBottomSheetBehavior = BottomSheetBehavior.from(fl);
        mBottomSheetBehavior.setHideable(false);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    mPOIInfoLayout.requestLayout();
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mPOIInfoLayout.requestLayout();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        view_tv = findViewById(R.id.fragment_point_map_tab_view);
        food_tv = findViewById(R.id.fragment_point_map_tab_food);
        shopping_tv = findViewById(R.id.fragment_point_map_tab_shopping);
        hotel_tv = findViewById(R.id.fragment_point_map_tab_hotel);

        switch (current_tab) {
            case 0:
                view_tv.setBackgroundColor(getResources().getColor(R.color.blue_25));
                view_tv.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case 1:
                food_tv.setBackgroundColor(getResources().getColor(R.color.blue_25));
                food_tv.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case 2:
                shopping_tv.setBackgroundColor(getResources().getColor(R.color.blue_25));
                shopping_tv.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case 3:
                hotel_tv.setBackgroundColor(getResources().getColor(R.color.blue_25));
                hotel_tv.setTextColor(getResources().getColor(android.R.color.white));
                break;
        }

        view_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_tab = 0;
                setTabColorDefault();
                view_tv.setBackgroundColor(getResources().getColor(R.color.blue_25));
                view_tv.setTextColor(getResources().getColor(android.R.color.white));
                if (mPointData != null) {
                    poiData = mPointData.getView();
                    addPOIMarkers(poiData, "view");
                }
            }
        });


        food_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_tab = 1;
                setTabColorDefault();
                food_tv.setBackgroundColor(getResources().getColor(R.color.blue_25));
                food_tv.setTextColor(getResources().getColor(android.R.color.white));
                if (mPointData != null) {
                    poiData = mPointData.getFood();
                    addPOIMarkers(poiData, "food");
                }
            }
        });

        shopping_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_tab = 2;
                setTabColorDefault();
                shopping_tv.setBackgroundColor(getResources().getColor(R.color.blue_25));
                shopping_tv.setTextColor(getResources().getColor(android.R.color.white));
                if (mPointData != null) {
                    poiData = mPointData.getShopping();
                    addPOIMarkers(poiData, "shopping");
                }
            }
        });

        hotel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_tab = 3;
                setTabColorDefault();
                hotel_tv.setBackgroundColor(getResources().getColor(R.color.blue_25));
                hotel_tv.setTextColor(getResources().getColor(android.R.color.white));
                if (mPointData != null) {
                    poiData = mPointData.getHotel();
                    addPOIMarkers(poiData, "hotel");
                }
            }
        });

        eReadySDKAPI.getInstance().getPoints(this, "all", a_id, keyword,
                "", "", "300",
                "", new NetworkManager.NetworkManagerListener<PointData>() {
                    @Override
                    public void onSucceed(PointData pointData) {
                        mPointData = pointData;
                        switch (current_tab) {
                            case 0:
                                poiData = pointData.getView();
                                addPOIMarkers(poiData, "view");
                                break;
                            case 1:
                                poiData = pointData.getFood();
                                addPOIMarkers(poiData, "food");
                                break;
                            case 2:
                                poiData = pointData.getShopping();
                                addPOIMarkers(poiData, "shopping");
                                break;
                            case 3:
                                poiData = pointData.getHotel();
                                addPOIMarkers(poiData, "hotel");
                                break;
                        }
                    }

                    @Override
                    public void onFail(String errorMsg, boolean shouldRetry) {
                    }
                });

        searchOET = findViewById(R.id.fragment_point_map_search);
        searchOET.setOmniEditTextListener(new OmniEditText.OmniEditTextListener() {
            @Override
            public void onSoftKeyboardDismiss() {
                mView.setFocusableInTouchMode(true);
                mView.requestFocus();
            }

            @Override
            public void onTouch(MotionEvent event) {

            }
        });

        findViewById(R.id.fragment_point_map_search_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = searchOET.getText().toString().trim();
                Tools.getInstance().hideKeyboard(eReadySDKActivity.this, mView);
                updatePointMapList();
            }
        });

        findViewById(R.id.poi_info_header_view_rl_btns).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoGoogleMap();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mMap != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                        new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                            new LatLng(START_LAT, START_LNG),
                            eReadyText.MAP_ZOOM_LEVEL));
                }
            }
        }, 1000);

    }

    private void setTabColorDefault() {
        view_tv.setBackgroundColor(getResources().getColor(R.color.black_3d));
        view_tv.setTextColor(getResources().getColor(R.color.gray_a7));
        food_tv.setBackgroundColor(getResources().getColor(R.color.black_3d));
        food_tv.setTextColor(getResources().getColor(R.color.gray_a7));
        shopping_tv.setBackgroundColor(getResources().getColor(R.color.black_3d));
        shopping_tv.setTextColor(getResources().getColor(R.color.gray_a7));
        hotel_tv.setBackgroundColor(getResources().getColor(R.color.black_3d));
        hotel_tv.setTextColor(getResources().getColor(R.color.gray_a7));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mIsMapInited) {
            mIsMapInited = true;
            mMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEventBus != null) {
            mEventBus.unregister(this);
        }

        if (mOGService != null) {
            mOGService.destroy();
        }
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        switch (current_tab) {
            case 0:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_landscape_b));
                break;
            case 1:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_food_b));
                break;
            case 2:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_shopping_b));
                break;
            case 3:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_hotel_b));
                break;
            default:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_ping_bg_b));
                break;
        }
        mBottomSheetBehavior.setPeekHeight(0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowCloseListener(this);

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (mClusterManager != null) {
                    mClusterManager.cluster();
                }
            }
        });

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);

        setupClusterManager();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLastLocation != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                            new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                            new LatLng(START_LAT, START_LNG),
                            eReadyText.MAP_ZOOM_LEVEL));
                }
            }
        }, 1000);
    }

    private void showUserPosition() {
        if (!mIsMapInited) {
            mIsMapInited = true;
            mMapFragment.getMapAsync(this);
        }
        LatLng current = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        addUserMarker(current, mLastLocation);
    }

    private void setupClusterManager() {
        if (mClusterManager == null) {
            mClusterManager = new ClusterManager<>(this, mMap);
        }
        if (mOmniClusterRender == null) {
            mOmniClusterRender = new OmniClusterRender(this, mMap, mClusterManager);
        }
        mClusterManager.setRenderer(mOmniClusterRender);
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<OmniClusterItem>() {
            @Override
            public boolean onClusterItemClick(OmniClusterItem omniClusterItem) {
                mOmniClusterItem = omniClusterItem;
                Marker marker = mOmniClusterRender.getMarker(omniClusterItem);
                if (marker.getTag() == null) {
                    marker.setTag(omniClusterItem.getPOIPoint());
                }
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pin_pick_b));
                showPOIInfo(omniClusterItem.getTitle(), omniClusterItem.getAddress(), omniClusterItem.getIconUrl(),
                        omniClusterItem.getIs_fav(), omniClusterItem.getTotal_fav(), omniClusterItem.getPOIPoint().getP_id());

                latLng = omniClusterItem.getPosition();
                return false;
            }
        });
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<OmniClusterItem>() {
            @Override
            public boolean onClusterClick(Cluster<OmniClusterItem> cluster) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        cluster.getPosition(),
                        (float) Math.floor(mMap.getCameraPosition().zoom + 1)),
                        300,
                        null);
                return true;
            }
        });
        mMap.setOnMarkerClickListener(mClusterManager);
    }

    private void showPOIInfo(String title, String address, String imgUrl, boolean isFav, int totalFav, final int p_id) {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(address)) {

            int height = mPOIInfoHeaderLayout.getHeight();
            mBottomSheetBehavior.setPeekHeight(height);
            mPOIInfoHeaderLayout.requestLayout();
            mPOIInfoHeaderLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            NetworkManager.getInstance().setNetworkImage(this, mPOIInfoIconNIV, imgUrl);
            mPOIInfoTitleTV.setText(title);
        }
    }

    private void addUserMarker(LatLng position, Location location) {
        if (mMap == null) {
            return;
        }

        if (mUserMarker == null) {
            mUserMarker = mMap.addMarker(new MarkerOptions()
                    .flat(true)
                    .rotation(location.getBearing())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location))
                    .anchor(0.5f, 0.5f)
                    .position(position)
                    .zIndex(eReadyText.MARKER_Z_INDEX));

            mUserAccuracyCircle = mMap.addCircle(new CircleOptions()
                    .center(position)
                    .radius(location.getAccuracy() / 2)
                    .strokeColor(ContextCompat.getColor(this, R.color.map_circle_stroke_color))
                    .fillColor(ContextCompat.getColor(this, R.color.map_circle_fill_color))
                    .strokeWidth(5)
                    .zIndex(eReadyText.MARKER_Z_INDEX));
        } else {
            mUserMarker.remove();
            mUserMarker = mMap.addMarker(new MarkerOptions()
                    .flat(true)
                    .rotation(location.getBearing())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location))
                    .anchor(0.5f, 0.5f)
                    .position(position)
                    .zIndex(eReadyText.MARKER_Z_INDEX));

            mUserAccuracyCircle.remove();
            mUserAccuracyCircle = mMap.addCircle(new CircleOptions()
                    .center(position)
                    .radius(location.getAccuracy() / 2)
                    .strokeColor(ContextCompat.getColor(this, R.color.map_circle_stroke_color))
                    .fillColor(ContextCompat.getColor(this, R.color.map_circle_fill_color))
                    .strokeWidth(5)
                    .zIndex(eReadyText.MARKER_Z_INDEX));
        }
    }

    private void checkLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ensurePermissions();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("如要繼續，請開啟裝置定位功能(需使用Google定位服務)");
            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("不用了，謝謝", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    registerService();
                }
            });
            dialog.show();
        }
    }

    private void ensurePermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    99);

        } else {
            registerService();
        }
    }

    private void registerService() {
        if (mOGService == null) {
            mOGService = new OGService(this);
        }
        mOGService.startService(new OGService.GoogleApiClientConnectCallBack() {
            @Override
            public void onGoogleApiClientConnected() {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            PreferencesTools.getInstance().saveProperty(this, PreferencesTools.KEY_PREFERENCES_LOCATION_PERMISSION, "true");
        }
        registerService();
    }

    private void addPOIMarkers(PointInfo[] pointInfo, String type) {

        DialogTools.getInstance().showProgress(this);
        removePreviousMarkers();

        OmniClusterItem item;
        if (itemList == null) {
            itemList = new ArrayList<>();
        }

        for (PointInfo poi : pointInfo) {
            item = new OmniClusterItem(poi, type);
            itemList.add(item);
        }
        if (mClusterManager != null) {
            mClusterManager.addItems(itemList);
            mClusterManager.cluster();
        }
        DialogTools.getInstance().dismissProgress(this);

        if (mMap != null && mLastLocation != null) {
            final LatLng current = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            addUserMarker(current, mLastLocation);
        }
    }

    private void removePreviousMarkers() {

        if (mClusterManager != null) {
            mClusterManager.clearItems();
            mClusterManager.cluster();
        }

        if (itemList != null) {
            itemList.clear();
        }

        if (mMap != null)
            mMap.clear();
    }

    private void updatePointMapList() {
        if (mLastLocation != null) {
            eReadySDKAPI.getInstance().getPoints(this, "all", a_id, keyword,
                    "", "", "300",
                    "", new NetworkManager.NetworkManagerListener<PointData>() {
                        @Override
                        public void onSucceed(PointData pointData) {
                            keyword = "";
                            mPointData = pointData;
                            switch (current_tab) {
                                case 0:
                                    poiData = pointData.getView();
                                    addPOIMarkers(poiData, "view");
                                    break;
                                case 1:
                                    poiData = pointData.getFood();
                                    addPOIMarkers(poiData, "food");
                                    break;
                                case 2:
                                    poiData = pointData.getShopping();
                                    addPOIMarkers(poiData, "shopping");
                                    break;
                                case 3:
                                    poiData = pointData.getHotel();
                                    addPOIMarkers(poiData, "hotel");
                                    break;
                            }

//                            if (mMap != null && mLastLocation != null) {
//                                final LatLng current = new LatLng(START_LAT, START_LNG);
//                                addUserMarker(current, mLastLocation);
//                            }
                            if (mMap != null) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                        new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                                        new LatLng(START_LAT, START_LNG),
                                        eReadyText.MAP_ZOOM_LEVEL));
                            }
                        }

                        @Override
                        public void onFail(String errorMsg, boolean shouldRetry) {
                        }
                    });
        }
    }

    private void gotoGoogleMap() {
        checkLocationService();
        if (mLastLocation != null) {
            double startLatitude = mLastLocation.getLatitude();
            double startLongitude = mLastLocation.getLongitude();
            double endLatitude = latLng.latitude;
            double endLongitude = latLng.longitude;

            String saddr = "saddr=" + startLatitude + "," + startLongitude;
            String daddr = "daddr=" + endLatitude + "," + endLongitude;
            String uriString = "http://maps.google.com/maps?" + saddr + "&" + daddr;

            Uri uri = Uri.parse(uriString);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
    }
}
