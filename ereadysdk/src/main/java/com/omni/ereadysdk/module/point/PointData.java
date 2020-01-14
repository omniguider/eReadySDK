package com.omni.ereadysdk.module.point;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PointData implements Serializable {

    @SerializedName("religion")
    private PointInfo[] religion;
    @SerializedName("food")
    private PointInfo[] food;
    @SerializedName("hotel")
    private PointInfo[] hotel;
    @SerializedName("shopping")
    private PointInfo[] shopping;
    @SerializedName("view")
    private PointInfo[] view;
    @SerializedName("traffic")
    private TrafficInfo[] traffic;

    public PointInfo[] getReligion() {
        return religion;
    }

    public PointInfo[] getFood() {
        return food;
    }

    public PointInfo[] getHotel() {
        return hotel;
    }

    public PointInfo[] getShopping() {
        return shopping;
    }

    public PointInfo[] getView() {
        return view;
    }

    public TrafficInfo[] getTraffic() {
        return traffic;
    }

}
