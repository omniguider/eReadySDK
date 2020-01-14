package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ThemeFeedback implements Serializable {

    @SerializedName("tc_id")
    private String tc_id;
    @SerializedName("tc_title")
    private String tc_title;
    @SerializedName("tc_img_web")
    private String tc_img_web;
    @SerializedName("tc_img_mobile")
    private String tc_img_mobile;
    @SerializedName("trips")
    private ThemeData[] trips;

    public String getTc_id() {
        return tc_id;
    }

    public String getTc_title() {
        return tc_title;
    }

    public String getTc_img_web() {
        return tc_img_web;
    }

    public String getTc_img_mobile() {
        return tc_img_mobile;
    }

    public ThemeData[] getTrips() {
        return trips;
    }

}