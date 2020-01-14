package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserTripData implements Serializable {

    @SerializedName("t_id")
    private String t_id;
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

    public String getT_title() {
        return t_title;
    }

    public String getT_title_en() {
        return t_title_en;
    }

    public String getT_image() {
        return t_image;
    }

    public String getT_desc() {
        return t_desc;
    }

    public String getT_desc_en() {
        return t_desc_en;
    }

    public String getT_share() {
        return t_share;
    }

    public void setT_share(String t_share){
        this.t_share = t_share;
    }

    public String getT_enabled() {
        return t_enabled;
    }

    public String getT_views() {
        return t_views;
    }

    public String getT_likes() {
        return t_likes;
    }

    public String getT_del() {
        return t_del;
    }

    public String getT_start_date() {
        return t_start_date;
    }

    public String[] getT_start_time() {
        return t_start_time;
    }

    public String getT_create_timestamp() {
        return t_create_timestamp;
    }

    public int getPoint_nums() {
        return point_nums;
    }

}
