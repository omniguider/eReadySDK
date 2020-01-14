package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReligionInfoFeedback implements Serializable {

    @SerializedName("rl_id")
    private int rl_id;
    @SerializedName("p_id")
    private int p_id;
    @SerializedName("name")
    private String name;
    @SerializedName("name_en")
    private String name_en;
    @SerializedName("area")
    private String area;
    @SerializedName("area_en")
    private String area_en;
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
    @SerializedName("image")
    private String image;
    @SerializedName("image_map")
    private String image_map;
    @SerializedName("image_map_w")
    private int image_map_w;
    @SerializedName("image_map_h")
    private int image_map_h;
    @SerializedName("pano")
    private String pano;
    @SerializedName("matterport")
    private String matterport;
    @SerializedName("google_comment")
    private String google_comment;
    @SerializedName("lat")
    private float lat;
    @SerializedName("lng")
    private float lng;
    @SerializedName("total_fav")
    private int total_fav;
    @SerializedName("exh")
    private ExhibitData[] exh;
    @SerializedName("flow")
    private FlowData flow;
    @SerializedName("intro")
    private IntroData intro;
    @SerializedName("miracle")
    private MiracleData miracle;

    public int getRl_id() {
        return rl_id;
    }

    public int getP_id() {
        return p_id;
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

    public String getImage_map() {
        return image_map;
    }

    public int getImage_map_w() {
        return image_map_w;
    }

    public int getImage_map_h() {
        return image_map_h;
    }

    public String getPano() {
        return pano;
    }

    public String getMatterport() {
        return matterport;
    }

    public String getGoogle_comment() {
        return google_comment;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public int getTotal_fav() {
        return total_fav;
    }

    public ExhibitData[] getExhibit() {
        return exh;
    }

    public FlowData getFlow() {
        return flow;
    }

    public IntroData getIntro() {
        return intro;
    }

    public MiracleData getMiracle() {
        return miracle;
    }
}