package com.omni.ereadysdk.module;

import android.os.Bundle;

public class OmniEvent {

    public static final int TYPE_CLICK_HOME = 0;
    public static final int TYPE_USER_LOCATION = 1;
    public static final int TYPE_LOGIN_STATUS_CHANGED = 2;
    public static final int TYPE_FAVORITE_CHANGED = 3;
    public static final int TYPE_NOTIFICATION_HISTORY_STATUS_CHANGED = 4;
    public static final int TYPE_FIREBASE_TOKEN_CHANGED = 5;
    public static final int TYPE_USER_TRIP_UPDATE = 6;
    public static final int TYPE_ADD_POINT_FAVORITE = 7;
    public static final int TYPE_ADD_POINT_MAP = 8;
    public static final int TYPE_ADD_COVER_DEFAULT = 9;
    public static final int TYPE_MASTER_SHARE_UPDATE = 10;
    public static final int TYPE_TRAFFIC_TYPE_TIME_UPDATE = 11;

    private int mType;
    private String mContent;
    private Object mObj;
    private Bundle mArgs;

    public OmniEvent(int type, String content) {
        mType = type;
        mContent = content;
    }

    public OmniEvent(int type, Object obj) {
        mType = type;
        mObj = obj;
    }

    public OmniEvent(int type, Bundle args) {
        mType = type;
        mArgs = args;
    }

    public int getType() {
        return mType;
    }

    public String getContent() {
        return mContent;
    }

    public Object getObj() {
        return mObj;
    }

    public Bundle getArguments() {
        return mArgs;
    }
}
