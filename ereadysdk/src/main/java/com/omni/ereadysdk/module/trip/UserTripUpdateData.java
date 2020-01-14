package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserTripUpdateData implements Serializable {

    @SerializedName("t_id")
    private String t_id;
    @SerializedName("tc_id")
    private String tc_id;
    @SerializedName("t_title")
    private String t_title;
    @SerializedName("t_title_en")
    private String t_title_en;
    @SerializedName("t_image")
    private String t_image;
    @SerializedName("t_desc")
    private String t_desc;
    @SerializedName("t_desc_en")
    private String t_desc_en;
    @SerializedName("t_owner")
    private String t_owner;
    @SerializedName("t_share")
    private String t_share;
    @SerializedName("t_enabled")
    private String t_enabled;
    @SerializedName("t_views")
    private String t_views;
    @SerializedName("t_likes")
    private String t_likes;
    @SerializedName("t_del")
    private String t_del;
    @SerializedName("t_start_date")
    private String t_start_date;
    @SerializedName("t_start_time")
    private String[] t_start_time;
    @SerializedName("t_create_timestamp")
    private String t_create_timestamp;
    @SerializedName("point_nums")
    private int point_nums;

    public String getT_id() {
        return t_id;
    }

    public String getT_share() {
        return t_share;
    }
}
