package com.omni.ereadysdk.module.point;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AreaData implements Serializable {

    @SerializedName("a_id")
    private String a_id;
    @SerializedName("title")
    private String title;
    @SerializedName("title_en")
    private String title_en;

    public String getA_id() {
        return a_id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_en() {
        return title_en;
    }

}
