package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExhibitData implements Serializable {

    @SerializedName("re_id")
    private int re_id;
    @SerializedName("name")
    private String name;
    @SerializedName("name_en")
    private String name_en;
    @SerializedName("intro")
    private String intro;
    @SerializedName("intro_en")
    private String intro_en;
    @SerializedName("image")
    private String image;
    @SerializedName("audio")
    private String audio;
    @SerializedName("audio_en")
    private String audio_en;
    @SerializedName("lat")
    private float lat;
    @SerializedName("lng")
    private float lng;
    @SerializedName("image_map_x")
    private float image_map_x;
    @SerializedName("image_map_y")
    private float image_map_y;
    @SerializedName("image_map_r")
    private int image_map_r;


    public int getRe_id() {
        return re_id;
    }

    public String getName() {
        return name;
    }

    public String getName_en() {
        return name_en;
    }

    public String getIntro() {
        return intro;
    }

    public String getIntro_en() {
        return intro_en;
    }

    public String getImage() {
        return image;
    }

    public String getAudio() {
        return audio;
    }

    public String getAudio_en() {
        return audio_en;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public float getImage_map_x() {
        return image_map_x;
    }

    public float getImage_map_y() {
        return image_map_y;
    }

    public int getImage_map_r() {
        return image_map_r;
    }
}
