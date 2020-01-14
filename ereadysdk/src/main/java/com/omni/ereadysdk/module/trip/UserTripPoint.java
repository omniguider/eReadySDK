package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserTripPoint implements Serializable {

    @SerializedName("p_id")
    private String p_id;
    @SerializedName("tr_title")
    private String tr_title;
    @SerializedName("tr_stay")
    private int tr_stay;
//    @SerializedName("traffic_tool")
//    private String traffic_tool;
//    @SerializedName("traffic_time")
//    private int traffic_time;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getTr_title() {
        return tr_title;
    }

    public void setTr_title(String tr_title) {
        this.tr_title = tr_title;
    }

    public int getTr_stay() {
        return tr_stay;
    }

    public void setTr_stay(int tr_stay) {
        this.tr_stay = tr_stay;
    }

//    public String getTraffic_tool() {
//        return traffic_tool;
//    }
//
//    public void setTraffic_tool(String traffic_tool) {
//        this.traffic_tool = traffic_tool;
//    }
//
//    public int getTraffic_time() {
//        return traffic_time;
//    }
//
//    public void setTraffic_time(int traffic_time) {
//        this.traffic_time = traffic_time;
//    }

    @Override
    public String toString() {
        return "UserTripPoint : { p_id : " + getP_id()
                + ",\ntr_title : " + getTr_title()
                + ",\ntr_stay : " + getTr_stay();
//                + ",\ntraffic_tool : " + getTraffic_tool()
//                + ",\ntraffic_time : " + getTraffic_time() + " }";
    }

    public static class Builder {

        private UserTripPoint mUserTripPoint;

        public Builder() {
            mUserTripPoint = new UserTripPoint();
        }

        public Builder setP_id(String p_id) {
            mUserTripPoint.setP_id(p_id);
            return this;
        }

        public Builder setTr_title(String tr_title) {
            mUserTripPoint.setTr_title(tr_title);
            return this;
        }

        public Builder setTr_stay(int tr_stay) {
            mUserTripPoint.setTr_stay(tr_stay);
            return this;
        }

//        public Builder setTraffic_tool(String traffic_tool) {
//            mUserTripPoint.setTraffic_tool(traffic_tool);
//            return this;
//        }
//
//        public Builder setTraffic_time(int traffic_time) {
//            mUserTripPoint.setTraffic_time(traffic_time);
//            return this;
//        }

        public UserTripPoint build() {
            return mUserTripPoint;
        }
    }
}
