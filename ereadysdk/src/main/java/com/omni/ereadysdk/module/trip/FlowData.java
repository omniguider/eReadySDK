package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FlowData implements Serializable {

    @SerializedName("desc")
    private String desc;
    @SerializedName("desc_en")
    private String desc_en;
    @SerializedName("god")
    private String god;
    @SerializedName("god_en")
    private String god_en;
    @SerializedName("god_image")
    private String god_image;
    @SerializedName("god_audio")
    private String god_audio;
    @SerializedName("god_audio_en")
    private String god_audio_en;
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

    public String getGod() {
        return god;
    }

    public String getGod_en() {
        return god_en;
    }

    public String getImage() {
        return image;
    }

    public String getGod_image() {
        return god_image;
    }

    public String getGod_audio() {
        return god_audio;
    }

    public String getGod_audio_en() {
        return god_audio_en;
    }

    public String getAudio() {
        return audio;
    }

    public String getAudio_en() {
        return audio_en;
    }

}
