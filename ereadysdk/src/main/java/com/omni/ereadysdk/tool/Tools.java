package com.omni.ereadysdk.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.omni.ereadysdk.R;

public class Tools {

    private static Tools mTools;

    public static int STATUS_BAR = 0;
    private static final float beaconTrigger10 = 10f;

    public float getBeaconTrigger() {
        return beaconTrigger10;
    }

    public static Tools getInstance() {
        if (mTools == null) {
            mTools = new Tools();
        }
        return mTools;
    }

    private int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }

    public int getNotificationSmallIcon() {
        if (getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            return R.mipmap.ic_launcher_round;
        } else {
            return R.mipmap.ic_launcher_round;
        }
    }

    public String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public void hideKeyboard(Context context, View rootView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }

    public int getTabBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else {
            return dpToIntPx(context, 55);
        }
    }

    public int dpToIntPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public Drawable getDrawable(Context context, int drawableId) {
        return ContextCompat.getDrawable(context, drawableId);
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
