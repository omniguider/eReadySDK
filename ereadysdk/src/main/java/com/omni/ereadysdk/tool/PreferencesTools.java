package com.omni.ereadysdk.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PreferencesTools {

    private static final String KEY_NMP_PREFERENCES_NAME = "key_preferences_nmp_preferences_name";
    public static final String KEY_PREFERENCES_FAVORITE = "key_preferences_favorite";
    public static final String KEY_BEACON_NOTIFICATION_HISTORY = "key_preferences_beacon_notification_history";
    public static final String KEY_BEACON_NOTIFICATION_HISTORY_UNREAD = "key_preferences_beacon_notification_history_unread";
    public static final String KEY_PREFERENCES_NOTIFICATION = "key_preferences_notification";
    public static final String KEY_PREFERENCES_ACTIVITY_NOTIFICATION = "key_preferences_activity_notification";
    public static final String KEY_GEO_FENCE_NOTIFICATION_HISTORY = "key_preferences_geo_fence_notification_history";
    public static final String KEY_GEO_FENCE_NOTIFICATION_HISTORY_UNREAD = "key_preferences_geo_fence_notification_history_unread";
    public static final String KEY_FIREBASE_NOTIFICATION_HISTORY = "key_preferences_firebase_notification_history";
    public static final String KEY_FIREBASE_NOTIFICATION_HISTORY_UNREAD = "key_preferences_firebase_notification_history_unread";
    public static final String KEY_PREFERENCES_LOCATION_PERMISSION = "key_preferences_location_permission";
    public static final String KEY_PREFERENCES_NOTIFICATION_PERMISSION = "key_preferences_notification_permission";
    public static final String KEY_PREFERENCES_PRIVACY = "key_preferences_privacy";

    private static PreferencesTools mPreferencesTools;
    private Gson mGson;

    public static PreferencesTools getInstance() {
        if (mPreferencesTools == null) {
            mPreferencesTools = new PreferencesTools();
        }
        return mPreferencesTools;
    }

    public Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    private SharedPreferences getPreferences(Context context) {
        if (context != null)
            return context.getSharedPreferences(KEY_NMP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return null;
    }

    public void removePreviousDaysBeaconProperty(Context context, String key, String dateStr) {
        SharedPreferences preferences = getPreferences(context);
        Map<String, ?> map = preferences.getAll();
        for (String k : map.keySet()) {
            if (k.contains(key) && !k.equals(key + dateStr)) {
                removeProperty(context, k);
            }
        }
    }

    public void saveProperty(Context context, String name, String value) {
        SharedPreferences.Editor e = getPreferences(context).edit();
        e.putString(name, value);
        e.apply();
    }

    public <T> void saveProperty(Context context, String name, T value) {
        SharedPreferences.Editor e = getPreferences(context).edit();
        e.putString(name, getGson().toJson(value));
        e.apply();
    }

    public void removeProperty(Context context, String name) {
        SharedPreferences.Editor e = getPreferences(context).edit();
        e.remove(name);
        e.apply();
    }

    public String getProperty(Context context, String name, String def) {
        return getPreferences(context).getString(name, def);
    }

    @Nullable
    public String getProperty(Context context, String name) {
        return getPreferences(context).getString(name, null);
    }

    @Nullable
    public <T> T getProperty(Context context, String name, Class<T> c) {
        String valueStr = getProperty(context, name);
        if (valueStr != null) {
            return getGson().fromJson(valueStr, c);
        }
        return null;
    }

    @NonNull
    public <T, X> Map getProperties(Context context, String name, Type type,
                                    Class<T> keyClass, Class<X> valueClass) {
        String valueStr = getProperty(context, name);
        Map<T, X> map;
        if (valueStr == null) {
            map = new HashMap<>();
        } else {
            map = getGson().fromJson(valueStr, type);
        }

        return map;
    }

    @Nullable
    public Set<String> getProperties(Context context, String name) {
        return getProperties(context, name, null);
    }

    @Nullable
    public Set<String> getProperties(Context context, String name, Set<String> def) {
        return getPreferences(context).getStringSet(name, def);
    }

    public void saveProperties(Context context, String name, Set<String> values) {
        SharedPreferences.Editor e = getPreferences(context).edit();
        e.putStringSet(name, values);
        e.apply();
    }

    public void addProperties(Context context, String name, String value) {
        Set<String> previousSet = getPreferences(context).getStringSet(name, null);
        Set<String> set;
        if (previousSet == null) {
            set = new HashSet<>();
        } else {
            set = new HashSet<>(previousSet);
        }

        if (!set.contains(value)) {
            set.add(value);
        }

        saveProperties(context, name, set);
    }

    public <T, X> boolean addProperties(Context context, String name, HashMap<T, X> newMap) {
        String valueStr = getProperty(context, name);
        HashMap<T, X> previousMap;
        if (TextUtils.isEmpty(valueStr)) {
            previousMap = new HashMap<>();
        } else {
            previousMap = getGson().fromJson(valueStr, newMap.getClass());
        }

        boolean haveNewKey = false;

        for (T key : newMap.keySet()) {
            if (!previousMap.containsKey(key)) {
                haveNewKey = true;
                break;
            }
        }

        newMap.keySet().removeAll(previousMap.keySet());
        previousMap.putAll(newMap);

        saveProperty(context, name, previousMap);

        return haveNewKey;
    }
}
