package com.omni.ereadysdk.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.omni.ereadysdk.R;
import com.omni.ereadysdk.module.CommonArrayResponse;
import com.omni.ereadysdk.module.CommonResponse;
import com.omni.ereadysdk.tool.AeSimpleSHA1;
import com.omni.ereadysdk.tool.DialogTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.omni.ereadysdk.tool.eReadyText.LOG_TAG;

public class NetworkManager {

    public static final String DOMAIN_NAME = "https://ereadymap.omniguider.com/";
    public static final String API_RESULT_TRUE = "true";
    public static final int TIME_OUT = 120;

    private static NetworkManager sNetworkManager;

    private Gson mGson;
    private Retrofit mRetrofit;
    private RequestQueue mRequestQueue;
    private SSLContext sslContext;

    public interface NetworkManagerListener<T> {
        void onSucceed(T object);

        void onFail(String errorMsg, boolean shouldRetry);
    }

    public static NetworkManager getInstance() {
        if (sNetworkManager == null) {
            sNetworkManager = new NetworkManager();
        }
        return sNetworkManager;
    }

    public void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Retrofit getRetrofit() {

        if (mRetrofit == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .sslSocketFactory(sslContext.getSocketFactory())
//                    .certificatePinner(new CertificatePinner.Builder().add("religitrav.kcg.gov.tw", "sha256/es9yOsS3hPBICz0S3zYTZAeVyFSgkxViQRRxnwKlBG8=").build())
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(DOMAIN_NAME)
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public void setNetworkImage(Context context, NetworkImageView networkImageView, String url) {
//        if (url.contains("religitrav.kcg.gov.tw"))
//            url = KaoReligionText.URL_REDIRECTION + url;
        setNetworkImage(context, networkImageView, url, -1, -1);
    }

    public void setNetworkImage(Context context, NetworkImageView networkImageView, String url, @DrawableRes int errorIconResId) {
        setNetworkImage(context, networkImageView, url, -1, -1);
    }

    public void setNetworkImage(final Context context, final NetworkImageView networkImageView, final String url,
                                @DrawableRes int defaultIconResId, @DrawableRes int errorIconResId) {

        LruImageCache lruImageCache = LruImageCache.getInstance();
        ImageLoader imageLoader = new ImageLoader(getRequestQueue(context), lruImageCache);

        networkImageView.setDefaultImageResId(defaultIconResId);
        networkImageView.setErrorImageResId(errorIconResId == -1 ? R.mipmap.ic_launcher_round : errorIconResId);
        networkImageView.setImageUrl(url, imageLoader);
    }

    public boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo wifiNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {

                return true;
            }
            NetworkInfo mobileNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            }

            boolean isNetworkEnable = (manager != null &&
                    manager.getActiveNetworkInfo() != null &&
                    manager.getActiveNetworkInfo().isConnectedOrConnecting());

            return isNetworkEnable;
        } else {
            return false;
        }
    }

    public Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    private boolean checkNetworkStatus(Context context) {
        if (!isNetworkAvailable(context)) {
            DialogTools.getInstance().dismissProgress(context);
            DialogTools.getInstance().showNoNetworkMessage(context);
            return false;
        }
        return true;
    }

    private void sendResponseFailMessage(Context context, Response response, NetworkManagerListener listener) {
        String errorMsg = response.message();
        listener.onFail(errorMsg, false);
    }

    private void sendAPIFailMessage(Context context, String errorMsg, NetworkManagerListener listener) {
        listener.onFail(errorMsg, false);
    }

    public <T> void addGetRequestToCommonObj(final Activity activity,
                                             Call<CommonResponse> call,
                                             final Class<T> responseClass,
                                             final NetworkManagerListener<T> listener) {
        if (!checkNetworkStatus(activity)) {
            return;
        }

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, final Response<CommonResponse> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            if (response.body() == null) {
                                listener.onFail(activity.getString(R.string.error_dialog_title_text_unknown), false);
                            } else {
                                T object = getGson().fromJson(response.toString(), responseClass);
                                listener.onSucceed(object);
                            }
                        } else {
                            sendResponseFailMessage(activity, response, listener);
                        }

                        DialogTools.getInstance().dismissProgress(activity);
                    }
                });
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(activity.getString(R.string.dialog_message_network_connect_not_good), false);
                        DialogTools.getInstance().dismissProgress(activity);
                    }
                });
            }
        });
    }

    public <T> void addGetRequestToCommonArrayObj(final Activity activity,
                                                  Call<CommonArrayResponse> call,
                                                  final Class<T[]> responseClass,
                                                  final NetworkManagerListener<T[]> listener) {
        if (!checkNetworkStatus(activity)) {
            return;
        }

        call.enqueue(new Callback<CommonArrayResponse>() {
            @Override
            public void onResponse(Call<CommonArrayResponse> call, final Response<CommonArrayResponse> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            if (response.body() == null) {
                                Log.e(LOG_TAG, "#11");
                                listener.onFail(activity.getString(R.string.error_dialog_title_text_unknown), false);
                            } else {

                                Log.e(LOG_TAG, "response.body().getResult() : " + response.body().getResult() +
                                        "\nerrorMsg : " + response.body().getErrorMessage());
                                if (response.body().getResult().equals(API_RESULT_TRUE)) {
                                    String json = getGson().toJson(response.body().getData());
                                    T[] data = getGson().fromJson(json, responseClass);

                                    listener.onSucceed(data);

                                } else {
                                    sendAPIFailMessage(activity, response.body().getErrorMessage(), listener);
                                }
                            }

                        } else {
                            sendResponseFailMessage(activity, response, listener);
                        }

                        DialogTools.getInstance().dismissProgress(activity);
                    }
                });
            }

            @Override
            public void onFailure(Call<CommonArrayResponse> call, Throwable t) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(activity.getString(R.string.dialog_message_network_connect_not_good), false);
                        DialogTools.getInstance().dismissProgress(activity);
                    }
                });
            }
        });
    }

    public <T> void addPostRequest(final Activity activity,
                                   Call<T> call,
                                   final Class<T> responseClass,
                                   final NetworkManagerListener<T> listener) {
        addPostRequest(activity, true, call, responseClass, listener);
    }

    public <T> void addPostRequest(final Activity activity,
                                   final boolean shouldDismissProgress,
                                   Call<T> call,
                                   final Class<T> responseClass,
                                   final NetworkManagerListener<T> listener) {

        if (!checkNetworkStatus(activity)) {
            return;
        }

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, final Response<T> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            if (response.body() == null) {
                                listener.onFail(activity.getString(R.string.error_dialog_title_text_unknown), false);
                            } else {
                                listener.onSucceed(response.body());
                            }

                        } else {
                            sendResponseFailMessage(activity, response, listener);
                        }

                        if (shouldDismissProgress) {
                            DialogTools.getInstance().dismissProgress(activity);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<T> call, final Throwable t) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(activity.getString(R.string.dialog_message_network_connect_not_good), false);

                        if (shouldDismissProgress) {
                            DialogTools.getInstance().dismissProgress(activity);
                        }
                    }
                });
            }
        });
    }

    public <T> void addPostRequestToCommonObj(final Activity activity,
                                              Call<CommonResponse> call,
                                              final Class<T> responseClass,
                                              final NetworkManagerListener<T> listener) {

        if (!checkNetworkStatus(activity)) {
            return;
        }

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, final Response<CommonResponse> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            Log.e(LOG_TAG, "#11");
                            if (response.body() == null) {
                                listener.onFail(activity.getString(R.string.error_dialog_title_text_unknown), false);
                            } else {
                                Log.e(LOG_TAG, "response.body().getResult() : " + response.body().getResult() +
                                        "\nerrorMsg : " + response.body().getErrorMessage());
                                if (response.body().getResult().equals(API_RESULT_TRUE)) {
                                    if (response.body().getData().toString().length() != 0) {
                                        String json = getGson().toJson(response.body().getData());
                                        T data = getGson().fromJson(json, responseClass);

                                        listener.onSucceed(data);
                                    }

                                } else {
                                    sendAPIFailMessage(activity, response.body().getErrorMessage(), listener);
                                }
                            }

                        } else {
                            sendResponseFailMessage(activity, response, listener);
                        }

                        DialogTools.getInstance().dismissProgress(activity);
                    }
                });
            }

            @Override
            public void onFailure(Call<CommonResponse> call, final Throwable t) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(LOG_TAG, "localized msg : " + t.getLocalizedMessage() + ", msg : " + t.getMessage() + ", cause : " + t.getCause());
                        listener.onFail(activity.getString(R.string.dialog_message_network_connect_not_good), false);
                        DialogTools.getInstance().dismissProgress(activity);
                    }
                });
            }
        });
    }

    public <T> void addPostRequestToCommonArrayObj(final Activity activity,
                                                   Call<CommonArrayResponse> call,
                                                   final Class<T[]> responseClass,
                                                   final NetworkManagerListener<T[]> listener) {

        addPostRequestToCommonArrayObj(activity, true, call, responseClass, listener);
    }

    public <T> void addPostRequestToCommonArrayObj(final Activity activity,
                                                   final boolean shouldDismissProgress,
                                                   Call<CommonArrayResponse> call,
                                                   final Class<T[]> responseClass,
                                                   final NetworkManagerListener<T[]> listener) {

        if (!checkNetworkStatus(activity)) {
            return;
        }

        call.enqueue(new Callback<CommonArrayResponse>() {
            @Override
            public void onResponse(Call<CommonArrayResponse> call, final Response<CommonArrayResponse> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {

                            if (response.body() == null) {
                                Log.e(LOG_TAG, "#1");
                                listener.onFail(activity.getString(R.string.error_dialog_title_text_unknown), false);
                            } else {
                                Log.e(LOG_TAG, "response.body().getResult() : " + response.body().getResult() +
                                        "\nerrorMsg : " + response.body().getErrorMessage());
                                if (response.body().getResult().equals(API_RESULT_TRUE)) {
                                    String json = getGson().toJson(response.body().getData());
                                    T[] data = getGson().fromJson(json, responseClass);

                                    listener.onSucceed(data);

                                } else {
                                    Log.e(LOG_TAG, "#2");
                                    sendAPIFailMessage(activity, response.body().getErrorMessage(), listener);
                                }
                            }

                        } else {
                            Log.e(LOG_TAG, "#3 response : " + response);
                            sendResponseFailMessage(activity, response, listener);
                        }

                        if (shouldDismissProgress) {
                            DialogTools.getInstance().dismissProgress(activity);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<CommonArrayResponse> call, final Throwable t) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(LOG_TAG, "#4 localized msg : " + t.getLocalizedMessage() + ", msg : " + t.getMessage() + ", cause : " + t.getCause());
                        listener.onFail(activity.getString(R.string.dialog_message_network_connect_not_good), false);

                        if (shouldDismissProgress) {
                            DialogTools.getInstance().dismissProgress(activity);
                        }
                        Log.e(LOG_TAG, "#5");
                    }
                });
            }
        });
    }

    public String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getMacStr(long currentTimestamp) {
        try {
            return AeSimpleSHA1.SHA1("kaoreligionapp://" + currentTimestamp);
        } catch (NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, "NoSuchAlgorithmException cause : " + e.getCause());
            return "";
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "UnsupportedEncodingException cause : " + e.getCause());
            return "";
        }
    }

}
