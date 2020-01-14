package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IntroData implements Serializable {

    @SerializedName("desc")
    private String desc;
    @SerializedName("desc_en")
    private String desc_en;
    @SerializedName("festival")
    private String festival;
    @SerializedName("festival_en")
    private String festival_en;
    @SerializedName("festival_audio")
    private String festival_audio;
    @SerializedName("festival_audio_en")
    private String festival_audio_en;
    @SerializedName("image")
    private String image;
    @SerializedName("audio")
    private String audio;
    @SerializedName("audio_en")
    private String audio_en;

    public String getDesc() {
        return desc;
    }

    public String getDesc_en() {
        return desc_en;
    }

    public String getFestival() {
        return festival;
    }

    public String getFestival_en() {
        return festival_en;
    }

    public String getFestival_audio() {
        return festival_audio;
    }

    public String getFestival_audio_en() {
        return festival_audio_en;
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

}
