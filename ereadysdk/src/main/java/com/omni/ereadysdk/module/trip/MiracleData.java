package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MiracleData implements Serializable {

    @SerializedName("desc")
    private String desc;
    @SerializedName("desc_en")
    private String desc_en;
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

    public String getImage() {
        return image;
    }

    public String getAudio() {
        return audio;
    }

}
