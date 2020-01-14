package com.omni.ereadysdk.network.api;

import android.app.Activity;

import com.omni.ereadysdk.module.CommonResponse;
import com.omni.ereadysdk.module.point.PointData;
import com.omni.ereadysdk.network.NetworkManager;
import com.omni.ereadysdk.tool.DialogTools;
import com.omni.ereadysdk.tool.Tools;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class eReadySDKAPI {

    private static eReadySDKAPI meReadySDKAPI;

    public static eReadySDKAPI getInstance() {
        if (meReadySDKAPI == null) {
            meReadySDKAPI = new eReadySDKAPI();
        }
        return meReadySDKAPI;
    }

    interface eReadySDKService {

        @FormUrlEncoded
        @POST("api/get_points")
        Call<CommonResponse> getPoints(@Field("type") String type,
                                       @Field("a_id") String a_id,
                                       @Field("keyword") String keyword,
                                       @Field("user_lat") String user_lat,
                                       @Field("user_lng") String user_lng,
                                       @Field("range") String range,
                                       @Field("device_id") String deviceId,
                                       @Field("login_token") String login_token,
                                       @Field("timestamp") String timestamp,
                                       @Field("mac") String mac);

    }

    private eReadySDKService geteReadySDKService() {
        return NetworkManager.getInstance().getRetrofit().create(eReadySDKService.class);
    }

    public void getPoints(Activity activity, String type, String a_id, String keyword, String user_lat, String user_lng, String range,
                          String loginToken, NetworkManager.NetworkManagerListener<PointData> listener) {
        DialogTools.getInstance().showProgress(activity);

        long currentTimestamp = System.currentTimeMillis() / 1000L;
        String mac = NetworkManager.getInstance().getMacStr(currentTimestamp);

        Call<CommonResponse> call = geteReadySDKService().getPoints(type, a_id, keyword, user_lat, user_lng, range,
                Tools.getInstance().getDeviceId(activity), loginToken, currentTimestamp + "", mac);
        NetworkManager.getInstance().addPostRequestToCommonObj(activity, call, PointData.class, listener);
    }

}
