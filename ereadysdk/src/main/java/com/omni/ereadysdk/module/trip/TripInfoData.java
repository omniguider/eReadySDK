package com.omni.ereadysdk.module.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TripInfoData implements Serializable {

    @SerializedName("t_id")
    private String t_id;
    @SerializedName("p_id")
    private String p_id;
    @SerializedName("tr_title")
    private String tr_title;
    @SerializedName("tr_stay")
    private String tr_stay;
    @SerializedName("tr_order")
    private String tr_order;
    @SerializedName("tr_traffic")
    private String tr_traffic;
    @SerializedName("tr_traffic_time")
    private String tr_traffic_time;
    @SerializedName("tr_traffic_gtime")
    private String tr_traffic_gtime;
    @SerializedName("p_type")
    private String p_type;
    @SerializedName("p_place_id")
    private String p_place_id;
    @SerializedName("v_name")
    private String v_name;
    @SerializedName("v_name_en")
    private String v_name_en;
    @SerializedName("v_lat")
    private String v_lat;
    @SerializedName("v_lng")
    private String v_lng;
    @SerializedName("v_image")
    private String v_image;
    @SerializedName("v_address")
    private String v_address;
    @SerializedName("v_address_en")
    private String v_address_en;
    @SerializedName("a_name")
    private String a_name;
    @SerializedName("a_name_en")
    private String a_name_en;
    @SerializedName("t_image")
    private String t_image;

    private int Order = 0;

    public int getOrder() {
        return Order;
    }

    public void setOrder(int Order) {
        this.Order = Order;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

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

    public String getTr_stay() {
        return tr_stay;
    }

    public void setTr_stay(String tr_stay) {
        this.tr_stay = tr_stay;
    }

    public String getTr_order() {
        return tr_order;
    }

    public void setTr_order(String tr_order) {
        this.tr_order = tr_order;
    }

    public String getTr_traffic() {
        return tr_traffic;
    }

    public void setTr_traffic(String tr_traffic) {
        this.tr_traffic = tr_traffic;
    }

    public String getTr_traffic_time() {
        return tr_traffic_time;
    }

    public void setTr_traffic_time(String tr_traffic_time) {
        this.tr_traffic_time = tr_traffic_time;
    }

    public String getTr_traffic_gtime() {
        return tr_traffic_gtime;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public String getP_place_id() {
        return p_place_id;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_name_en() {
        return v_name_en;
    }

    public String getV_lat() {
        return v_lat;
    }

    public void setV_lat(String v_lat) {
        this.v_lat = v_lat;
    }

    public String getV_lng() {
        return v_lng;
    }

    public void setV_lng(String v_lng) {
        this.v_lng = v_lng;
    }

    public String getV_image() {
        return v_image;
    }

    public void setV_image(String v_image) {
        this.v_image = v_image;
    }

    public String getV_address() {
        return v_address;
    }

    public String getA_name() {
        return a_name;
    }

    public String getV_address_en() {
        return v_address_en;
    }

    public void setV_address(String v_address) {
        this.v_address = v_address;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getT_image() {
        return t_image;
    }
}
