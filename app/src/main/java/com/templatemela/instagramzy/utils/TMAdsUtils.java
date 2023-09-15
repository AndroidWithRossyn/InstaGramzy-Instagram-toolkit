package com.templatemela.instagramzy.utils;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.ads.nativead.NativeAdOptions.ADCHOICES_TOP_RIGHT;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.nativetemplates.NativeTemplateStyle;
import com.google.nativetemplates.TemplateView;
import com.templatemela.instagramzy.R;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TMAdsUtils {

    public static boolean isShowAds = true;
    public static int adCounter = 1;
    static InterstitialAd mInterstitialAd;
    static RewardedInterstitialAd rewardedInterstitialAd;

    public static void initAd(Context context) {
        if (isShowAds) {
            String id= getDeviceId(context).toUpperCase(Locale.ROOT);
            Log.e("devices id",id);
            RequestConfiguration.Builder requestConfiguration=new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList(id));
            MobileAds.initialize(context, initializationStatus -> {
            });
             MobileAds.setRequestConfiguration( requestConfiguration.build() );
        }
    }

    public static String getDeviceId(Context context){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String androidId = Settings.Secure.getString((ContentResolver)context.getContentResolver(),"android_id");
        messageDigest.update(androidId.getBytes());
        byte[] arrby = messageDigest.digest();
        StringBuffer sb = new StringBuffer();
        int n = arrby.length;
        for(int i=0; i<n; ++i){
            String oseamiya = Integer.toHexString((int)(255 & arrby[i]));
            while(oseamiya.length() < 2){
                oseamiya = "0" + oseamiya;
            }
            sb.append(oseamiya);
        }
        String result = sb.toString();
        return result;

    }


    public static void loadBannerAd(Context context, LinearLayout adContainer) {
        if (isShowAds) {
            AdView adView = new AdView(context);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, getAdsWidth((Activity) context)));
            adView.setAdUnitId(context.getString( R.string.admob_banner_id));
            adView.loadAd(adRequest);
            adContainer.addView(adView);
        }
    }


    public static void loadBigBannerAds(Context context, LinearLayout adContainer) {
        if (isShowAds) {
            AdView adView = new AdView(context);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdSize(AdSize.LARGE_BANNER);
            adView.setAdUnitId(context.getString( R.string.admob_banner_id));
            adView.loadAd(adRequest);
            adContainer.addView(adView);
        }
    }

    public static void loadInterAd(Context context) {
        if (isShowAds) {

            InterstitialAd.load(context, context.getString(R.string.admob_interstitial), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                public void onAdLoaded(InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                }

                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    mInterstitialAd = null;
                }
            });

        }
    }

    public static void showInterAd(Context context, final Intent intent) {
        if (isShowAds) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show((Activity) context);
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    public void onAdDismissedFullScreenContent() {
                        startActivity(context, intent);
                        Log.d("TAG", "The ad was dismissed.");
                    }

                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                    }

                    public void onAdShowedFullScreenContent() {
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            } else {
                startActivity(context, intent);
            }
        } else {
            startActivity(context, intent);
        }

        return;
    }

    public static void showInterAdwithResult(Activity context, final Intent intent, int resultcode) {
        if (isShowAds) {
            if (adCounter == 6) {
                adCounter = 1;
                if (mInterstitialAd != null) {
                    mInterstitialAd.show((Activity) context);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        public void onAdDismissedFullScreenContent() {
                            context.startActivityForResult(intent, resultcode);
                            Log.d("TAG", "The ad was dismissed.");
                        }

                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            Log.d("TAG", "The ad failed to show.");
                        }

                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd = null;
                            Log.d("TAG", "The ad was shown.");
                        }
                    });
                } else {
                    context.startActivityForResult(intent, resultcode);

                }
            } else {
                context.startActivityForResult(intent, resultcode);
            }
        } else {
            context.startActivityForResult(intent, resultcode);
        }

        return;
    }


    public static void loadRewardedAd(Context context) {

        if (isShowAds) {
            AdRequest adRequest = new AdRequest.Builder().build();
            // Use the test ad unit ID to load an ad.
            RewardedInterstitialAd.load(
                    context,
                    "ca-app-pub-3940256099942544/5354046379",
                    adRequest,
                    new RewardedInterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(RewardedInterstitialAd ad) {
                            Log.d(TAG, "onAdLoaded");
                            rewardedInterstitialAd = ad;

                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                        }
                    });
        }


    }


    public static void loadRewardedInterstitialAd(Context context, Dialog dialog, ProgressDialog loading_spinner) {

        if (rewardedInterstitialAd != null) {
            rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.i(TAG, "Ad failed to show.");
                    loading_spinner.dismiss();
                }


                @Override
                public void onAdShowedFullScreenContent() {
                    loading_spinner.dismiss();
                }


                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d(TAG, "onAdDismissedFullScreenContent");
                    loadRewardedAd(context);
                    dialog.show();
                }
            });
            rewardedInterstitialAd.show((Activity) context, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                }
            });

        } else {
            dialog.show();
        }

    }


    public static void loadNativeAd(Context context, TemplateView template) {
        AdLoader adLoader = new AdLoader.Builder(context, context.getString(R.string.admob_native_id)).forNativeAd(nativeAd -> {
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                }).withNativeAdOptions(new NativeAdOptions.Builder()
                        .setRequestCustomMuteThisAd(true)
                        .setAdChoicesPlacement(ADCHOICES_TOP_RIGHT)
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }


    static void startActivity(Context context, Intent intent) {
        if (intent != null) {
            context.startActivity(intent);
        }
    }

    private static int getAdsWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return adWidth;
    }

}
