package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TripInfoFeedback implements Serializable {

    @SerializedName("trip_info")
    private TripInfoData[] trip_info;
    @SerializedName("total_num")
    private int total_num;
    @SerializedName("total_min")
    private int total_min;
    @SerializedName("start_time")
    private String start_time;

    public String getStart_time() {
        return start_time;
    }

    public int getTotal_min() {
        return total_min;
    }

    public int getTotal_num() {
        return total_num;
    }

    public TripInfoData[] getTrip_info() {
        if (trip_info == null){
            List<TripInfoData> addTripInfoList = new ArrayList<>();
            addTripInfoList.add(new TripInfoData());
            trip_info = addTripInfoList.toArray((new TripInfoData[addTripInfoList.size()]));
        }
        return trip_info;
    }

    public void setTrip_info(TripInfoData[] trip_info) {
        this.trip_info = trip_info;
    }

}