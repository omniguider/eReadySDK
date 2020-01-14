package com.omni.ereadysdk.module.point;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrafficInfo implements Serializable {

    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("image")
    private String image;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

}
