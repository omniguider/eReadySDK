package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PointInfoFeedback implements Serializable {

    @SerializedName("p_id")
    private String p_id;
    @SerializedName("p_type")
    private String p_type;
    @SerializedName("place_id")
    private int place_id;
    @SerializedName("name")
    private String name;
    @SerializedName("name_en")
    private String name_en;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;
    @SerializedName("image")
    private String image;
    @SerializedName("address")
    private String address;
    @SerializedName("address_en")
    private String address_en;
    @SerializedName("web")
    private String web;
    @SerializedName("tel")
    private String tel;
    @SerializedName("opentime")
    private String opentime;
    @SerializedName("area")
    private String area;
    @SerializedName("area_en")
    private String area_en;
    @SerializedName("total_fav")
    private int total_fav;

    public String getP_id() {
        return p_id;
    }

    public String getP_type() {
        return p_type;
    }

    public int getPlace_id() {
        return place_id;
    }

    public String getName() {
        return name;
    }

    public String getName_en() {
        return name_en;
    }

    public String getArea() {
        return area;
    }

    public String getArea_en() {
        return area_en;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress_en() {
        return address_en;
    }

    public String getWeb() {
        return web;
    }

    public String getTel() {
        return tel;
    }

    public String getOpentime() {
        return opentime;
    }

    public String getImage() {
        return image;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getTotal_fav() {
        return total_fav;
    }
}