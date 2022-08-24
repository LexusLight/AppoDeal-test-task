package com.example.appodealtest1337;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;
import com.appodeal.ads.RewardedVideoCallbacks;
import com.appodeal.ads.native_ad.views.NativeAdViewAppWall;
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream;
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int banner_count = 0, video_count = 3;
    Button banner_button, interstitial_button, rewarded_video_button, native_button;
    String banner_string, interstitial_string, reward_video_string;
    boolean interstitial_lock,native_lock = false;
    List<NativeAd> nativeAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAds(); //Все конфигурации вынесены в нижнюю функцию

        banner_button           = findViewById(R.id.banner_button);
        interstitial_button     = findViewById(R.id.interstitial_button);
        rewarded_video_button   = findViewById(R.id.rewarded_button);
        native_button           = findViewById(R.id.native_button);

        banner_string       = getString(R.string.banner_button);
        interstitial_string = getString(R.string.interstitial_button);
        reward_video_string = getString(R.string.rewarded_button);
    }
    /*
        Функции на клик кнопок
    */
    public void showBanner(View view){
        Appodeal.show(this, Appodeal.BANNER_TOP);
        banner_count = 0;
    }
    public void showInterstitial(View view){
        hideAds();
        Appodeal.show(this, Appodeal.INTERSTITIAL);
    }
    public void showRewardedVideo(View view){
        hideAds();
        Appodeal.show(this, Appodeal.REWARDED_VIDEO);
    }
    public void showNativeAd(View view){
        Appodeal.show(this, Appodeal.NATIVE);
    }
    public void hideAdsButton(View view){
        hideAds();
    }
    public void hideAds(){
        Appodeal.hide(this,Appodeal.INTERSTITIAL | Appodeal.BANNER | Appodeal.REWARDED_VIDEO | Appodeal.NATIVE);
    }
    /*
            Функция с конфигурацией и поведением рекламы.
    */
    public void configureAds(){
        Appodeal.initialize(this,"efc6d60da1164727c4c4ddded5aa9ae1a178d16efd197f28",Appodeal.INTERSTITIAL | Appodeal.BANNER |Appodeal.REWARDED_VIDEO | Appodeal.NATIVE);
        Appodeal.cache(this, Appodeal.NATIVE, 3);

        Appodeal.setBannerCallbacks(new BannerCallbacks() {

            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {

                banner_button.setEnabled(true);
            }

            @Override
            public void onBannerFailedToLoad() {

            }

            @Override
            public void onBannerShown() {
                banner_count++;
                banner_button.setText(banner_string+" ("+banner_count+")");
                if(banner_count > 5){
                    hideAds();
                    banner_button.setText(banner_string);
                    banner_count = 0;
                }
            }

            @Override
            public void onBannerShowFailed() {

            }

            @Override
            public void onBannerClicked() {

            }

            @Override
            public void onBannerExpired() {

            }

        });



        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean isPrecache) {
                if(!interstitial_lock){
                    interstitial_button.setEnabled(true);
                }
            }

            @Override
            public void onInterstitialFailedToLoad() {
            }

            @Override
            public void onInterstitialShown() {
                interstitial_button.setEnabled(false);
                interstitial_lock = true;
            }

            @Override
            public void onInterstitialShowFailed() {

            }

            @Override
            public void onInterstitialClicked() {
            }

            @Override
            public void onInterstitialClosed() {
                new CountDownTimer(60000,1000) {
                    public void onTick(long millisUntilFinished) {
                        interstitial_button.setText(interstitial_string+" ("+millisUntilFinished / 1000+")");
                        // logic to set the EditText could go here
                    }
                    public void onFinish() {
                        interstitial_button.setText(interstitial_string);
                        interstitial_button.setEnabled(true);
                        interstitial_lock = false;
                    }
                }.start();
            }

            @Override
            public void onInterstitialExpired() {

            }
        });

        Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
            @Override
            public void onRewardedVideoLoaded(boolean isPrecache) {
                if(video_count > 0) rewarded_video_button.setEnabled(true);
                rewarded_video_button.setText(reward_video_string+" ("+(video_count)+")");
            }

            @Override
            public void onRewardedVideoFailedToLoad() {

            }

            @Override
            public void onRewardedVideoShown() {
                video_count--;
                if(video_count <= 0){
                    rewarded_video_button.setEnabled(false);
                }
                rewarded_video_button.setText(reward_video_string+" ("+(video_count)+")");
            }

            @Override
            public void onRewardedVideoShowFailed() {

            }

            @Override
            public void onRewardedVideoFinished(double amount, @Nullable String name) {

            }

            @Override
            public void onRewardedVideoClosed(boolean finished) {

            }

            @Override
            public void onRewardedVideoExpired() {

            }

            @Override
            public void onRewardedVideoClicked() {

            }
        });

        Appodeal.setNativeCallbacks(new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {
                if(!native_lock){
                    NativeAdViewNewsFeed nav_nf = (NativeAdViewNewsFeed) findViewById(R.id.native_ad_view_news_feed);
                    nativeAds = Appodeal.getNativeAds(3);
                    for (NativeAd ad: nativeAds) {
                        nav_nf.setNativeAd(ad);
                    }
                    native_lock = true;
                }

            }

            @Override
            public void onNativeFailedToLoad() {

            }

            @Override
            public void onNativeShown(NativeAd nativeAd) {

            }

            @Override
            public void onNativeShowFailed(NativeAd nativeAd) {

            }

            @Override
            public void onNativeClicked(NativeAd nativeAd) {

            }

            @Override
            public void onNativeExpired() {

            }
        });
    }
}