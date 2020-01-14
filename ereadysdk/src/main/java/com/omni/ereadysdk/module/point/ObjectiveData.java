package com.omni.ereadysdk.module.point;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectiveData implements Serializable {

    @SerializedName("ro_id")
    private String ro_id;
    @SerializedName("title")
    private String title;
    @SerializedName("title_en")
    private String title_en;

    public String getRo_id() {
        return ro_id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_en() {
        return title_en;
    }

}
