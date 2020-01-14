package com.omni.ereadysdk.service;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.omni.ereadysdk.module.OmniEvent;
import com.omni.ereadysdk.tool.PreferencesTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class OGService implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Activity mActivity;
    private EventBus mEventBus;
    private GoogleApiClientConnectCallBack mGoogleApiClientConnectCallBack;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    public static Location mLastLocation;

    public interface GoogleApiClientConnectCallBack {
        void onGoogleApiClientConnected();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OmniEvent event) {
        switch (event.getType()) {
        }
    }

    public OGService(Activity activity) {
        mActivity = activity;

        if (mEventBus == null) {
            mEventBus = EventBus.getDefault();
        }
        mEventBus.register(this);
    }

    public void startService(GoogleApiClientConnectCallBack callBack) {

        mGoogleApiClientConnectCallBack = callBack;

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mActivity).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(Places.GEO_DATA_API)
                    .build();
        }

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    public void destroy() {
        if (mEventBus != null) {
            mEventBus.unregister(this);
        }

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            if (mLocationRequest == null) {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(1000);
                mLocationRequest.setFastestInterval(1000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            if (mGoogleApiClientConnectCallBack != null) {
                mGoogleApiClientConnectCallBack.onGoogleApiClientConnected();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (Objects.equals(PreferencesTools.getInstance().getProperty(mActivity, PreferencesTools.KEY_PREFERENCES_LOCATION_PERMISSION), "true")) {
            mLastLocation = location;
            EventBus.getDefault().post(new OmniEvent(OmniEvent.TYPE_USER_LOCATION, location));
        }
    }
}
