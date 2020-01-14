package com.omni.ereadysdk.view.items;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.omni.ereadysdk.module.point.PointInfo;
import com.omni.ereadysdk.module.trip.TripInfoData;

public class OmniClusterItem<T> implements ClusterItem {

    private LatLng mPosition;
    private String mTitle;
    private String mAddress;
    private String mArea;
    private String mIconUrl;
    private TripInfoData mPOI_tripInfo;
    private PointInfo mPOI_point;
    private String order;
    private int total_fav;
    private boolean is_fav;
    private String poiType;

    public OmniClusterItem(TripInfoData poi) {
        mPOI_tripInfo = poi;
        mPosition = new LatLng(Double.parseDouble(poi.getV_lat()), Double.parseDouble(poi.getV_lng()));
        mTitle = poi.getV_name();
        mAddress = poi.getV_address();
        mArea = poi.getA_name();
        mIconUrl = poi.getV_image();
        order = String.valueOf(poi.getOrder() + 1);
    }

    public OmniClusterItem(PointInfo poi, String type) {
        mPOI_point = poi;
        mPosition = new LatLng(poi.getlat(), poi.getlng());
        mTitle = poi.getname();
        mAddress = poi.getaddress();
        mArea = poi.getArea();
        mIconUrl = poi.getImage();
        order = "1";
        total_fav = poi.getTotal_fav();
        is_fav = poi.getIs_fav();
        poiType = type;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OmniClusterItem)) {
            return false;
        }

        return super.equals(obj);
    }

    public int getTotal_fav() {
        return total_fav;
    }

    public boolean getIs_fav() {
        return is_fav;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getType() {
        return poiType;
    }

    public String getOrder() {
        return order;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getArea() {
        return mArea;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public TripInfoData getPOITripInfo() {
        return mPOI_tripInfo;
    }

    public PointInfo getPOIPoint() {
        return mPOI_point;
    }

}
