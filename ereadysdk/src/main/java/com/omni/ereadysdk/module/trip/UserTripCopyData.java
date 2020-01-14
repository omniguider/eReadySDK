package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserTripCopyData implements Serializable {

    @SerializedName("tr_id")
    private String tr_id;
    @SerializedName("t_id")
    private String t_id;
    @SerializedName("p_id")
    private String p_id;
    @SerializedName("tr_title")
    private String tr_title;
    @SerializedName("tr_stay")
    private String tr_stay;
    @SerializedName("tr_order")
    private String tr_order;
    @SerializedName("tr_day")
    private String tr_day;
    @SerializedName("tr_traffic")
    private String tr_traffic;
    @SerializedName("tr_traffic_time")
    private String tr_traffic_time;
    @SerializedName("tr_traffic_gtime")
    private String tr_traffic_gtime;
    @SerializedName("tr_traffic_path")
    private String tr_traffic_path;
    @SerializedName("p_type")
    private String p_type;
    @SerializedName("v_id")
    private String v_id;
    @SerializedName("p_place_id")
    private String p_place_id;
    @SerializedName("p_gscore")
    private String p_gscore;
    @SerializedName("p_update_time")
    private String p_update_time;
    @SerializedName("v_name")
    private String v_name;
    @SerializedName("v_name_en")
    private String v_name_en;
    @SerializedName("v_lat")
    private String v_lat;
    @SerializedName("v_lng")
    private String v_lng;
    @SerializedName("v_image")
    private String v_image;
    @SerializedName("v_address")
    private String v_address;
    @SerializedName("t_image")
    private String t_image;

    public String getTr_id() {
        return tr_id;
    }

    public String getT_id() {
        return t_id;
    }

    public String getP_id() {
        return p_id;
    }

    public String getTr_title() {
        return tr_title;
    }

    public String getTr_stay() {
        return tr_stay;
    }

    public String getTr_order() {
        return tr_order;
    }

    public String getTr_traffic() {
        return tr_traffic;
    }

    public String getTr_traffic_time() {
        return tr_traffic_time;
    }

    public String getTr_traffic_gtime() {
        return tr_traffic_gtime;
    }

    public String getP_type() {
        return p_type;
    }

    public String getP_place_id() {
        return p_place_id;
    }

    public String getV_name() {
        return v_name;
    }

    public String getV_name_en() {
        return v_name_en;
    }

    public String getV_lat() {
        return v_lat;
    }

    public String getV_lng() {
        return v_lng;
    }

    public String getV_image() {
        return v_image;
    }

    public String getV_address() {
        return v_address;
    }

    public String getT_image() {
        return t_image;
    }
}
