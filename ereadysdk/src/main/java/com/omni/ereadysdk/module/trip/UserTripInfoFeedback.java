package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserTripInfoFeedback implements Serializable {

    @SerializedName("trip_days")
    private TripInfoFeedback[] trip_days;
    @SerializedName("total_num")
    private int total_num;
    @SerializedName("total_min")
    private int total_min;

    public int getTotal_min() {
        return total_min;
    }

    public int getTotal_num() {
        return total_num;
    }

    public TripInfoFeedback[] getTrip_days() {
        return trip_days;
    }

}