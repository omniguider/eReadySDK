package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ThemeData implements Serializable {

    @SerializedName("t_id")
    private String t_id;
    @SerializedName("t_title")
    private String t_title;
    @SerializedName("t_start_time")
    private String t_start_time;
    @SerializedName("t_image")
    private String t_image;
    @SerializedName("t_desc")
    private String t_desc;
    @SerializedName("t_views")
    private String t_views;
    @SerializedName("t_likes")
    private String t_likes;
    @SerializedName("point_nums")
    private String point_nums;

    public String getT_id() {
        return t_id;
    }

    public String getT_title() {
        return t_title;
    }

    public String getT_start_time() {
        return t_start_time;
    }

    public String getT_image() {
        return t_image;
    }

    public String getT_desc() {
        return t_desc;
    }

    public String getT_views() {
        return t_views;
    }

    public String getT_likes() {
        return t_likes;
    }

    public String getPoint_nums() {
        return point_nums;
    }

}
