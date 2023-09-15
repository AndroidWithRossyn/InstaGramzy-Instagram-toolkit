package com.google;

import android.content.Context;
import android.widget.LinearLayout;

import com.google.admob_advanced_native_recyvlerview.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BannerAdsUtil {
    static AdView adView;

    public  static void  loadBannerAds(Context context){
        adView = new AdView(context);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(context.getString(R.string.admob_banner_medium_id));
        adView.loadAd(adRequest);
    }

    public static void  addBannerAds( LinearLayout layout){
        if (layout.getChildCount()!= 0){
            layout.removeAllViews();
        }
        layout.addView(adView);
    }
}
