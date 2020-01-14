package com.omni.ereadysdk.view.navi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.omni.ereadysdk.R;
import com.omni.ereadysdk.tool.Tools;
import com.omni.ereadysdk.view.items.OmniClusterItem;

public class OmniClusterRender extends DefaultClusterRenderer<OmniClusterItem> {

    private Context mContext;
    private int mMinClusterSize = 10;

    public OmniClusterRender(Context context, GoogleMap map, ClusterManager<OmniClusterItem> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
        setMinClusterSize(mMinClusterSize);
    }

    @Override
    public void setMinClusterSize(int minClusterSize) {
        super.setMinClusterSize(minClusterSize);
    }

    @Override
    protected void onBeforeClusterItemRendered(OmniClusterItem item, MarkerOptions markerOptions) {

        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_ping_bg_b).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(icon);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mContext.getResources().getColor(R.color.colorPrimary)); // Text Color
        paint.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_normal)); //Text Size
        int x = (int) (canvas.getWidth() / 2 - paint.measureText(item.getOrder()) / 2);
        int y = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        canvas.drawText(item.getOrder(), x, y - 6, paint);

        if (item.getPOITripInfo() == null) {
            switch (item.getType()) {
                case "religion":
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_religion_b));
                    break;
                case "food":
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_food_b));
                    break;
                case "view":
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_landscape_b));
                    break;
                case "shopping":
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_shopping_b));
                    break;
                case "hotel":
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_hotel_b));
                    break;
                default:
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_ping_bg_b));
                    break;
            }
        } else {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }
        markerOptions.title(item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<OmniClusterItem> cluster, MarkerOptions markerOptions) {
        IconGenerator TextMarkerGen = new IconGenerator(mContext);

        Drawable marker = mContext.getResources().getDrawable(R.drawable.solid_circle_holo_orange_light);
        TextMarkerGen.setBackground(marker);

        TextMarkerGen.makeIcon(cluster.getSize() + "");
        TextMarkerGen.setTextAppearance(mContext,
                cluster.getSize() < 10 ?
                        R.style.ClusterViewTextAppearanceBig :
                        R.style.ClusterViewTextAppearanceMedium);
        if (cluster.getSize() >= 10) {
            TextMarkerGen.setContentPadding(Tools.getInstance().dpToIntPx(mContext, 10),
                    Tools.getInstance().dpToIntPx(mContext, 8),
                    Tools.getInstance().dpToIntPx(mContext, 10),
                    Tools.getInstance().dpToIntPx(mContext, 8));
        }

        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(TextMarkerGen.makeIcon());
        markerOptions.icon(icon);
    }

    @Override
    protected void onClusterRendered(Cluster<OmniClusterItem> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);
    }

}

