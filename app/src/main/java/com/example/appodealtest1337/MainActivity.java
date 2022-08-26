package com.example.appodealtest1337;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int bannerCount = 0, videoCount = 3;
    Button bannerButton, interstitialButton, rewardedVideoButton, nativeButton;
    String bannerString, interstitialString, rewardVideoString;
    boolean interstitialLock = false;

    public RecyclerView recyclerView;
    public List<NativeAd> nativeBuffer;
    public List<NativeAd> nativeAds = new ArrayList<>();
    private final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new NativeAdapter(this.nativeAds);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerButton = findViewById(R.id.banner_button);
        interstitialButton = findViewById(R.id.interstitial_button);
        rewardedVideoButton = findViewById(R.id.rewarded_button);
        nativeButton = findViewById(R.id.native_button);

        bannerString = getString(R.string.banner_button);
        interstitialString = getString(R.string.interstitial_button);
        rewardVideoString = getString(R.string.rewarded_button);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        configureAds(); //Все конфигурации вынесены в нижнюю функцию
    }


    /*
        Стандартные событийные функции
    */
    public void showBanner(View view){
        Appodeal.show(this, Appodeal.BANNER_TOP);
        bannerCount = 0;
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
        hideAds();
        recyclerView.setVisibility(View.VISIBLE);
        bannerButton.setVisibility(View.GONE);
        interstitialButton.setVisibility(View.GONE);
        rewardedVideoButton.setVisibility(View.GONE);
        nativeButton.setVisibility(View.GONE);
    }


    public void hideAdsButton(View view){
        hideAds();
    }


    public void hideAds(){
        Appodeal.hide(this,Appodeal.INTERSTITIAL | Appodeal.BANNER | Appodeal.REWARDED_VIDEO | Appodeal.NATIVE);
        recyclerView.setVisibility(View.GONE);
        bannerButton.setVisibility(View.VISIBLE);
        interstitialButton.setVisibility(View.VISIBLE);
        rewardedVideoButton.setVisibility(View.VISIBLE);
        nativeButton.setVisibility(View.VISIBLE);
    }


    /*
            Конфигурация + все калбеки
    */
    public void configureAds()
    {

        Appodeal.initialize(this,"efc6d60da1164727c4c4ddded5aa9ae1a178d16efd197f28", Appodeal.BANNER | Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.NATIVE);
        Appodeal.cache(this, Appodeal.NATIVE, 4);

        //При развороте экрана, наши активити перезагружаются. При перевороте заново их включаем
        if(Appodeal.isLoaded(Appodeal.BANNER)){
            bannerButton.setEnabled(true);
        }
        if(Appodeal.isLoaded(Appodeal.INTERSTITIAL)){
            interstitialButton.setEnabled(true);
        }
        if(Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)){
            rewardedVideoButton.setEnabled(true);
        }
        if(Appodeal.isLoaded(Appodeal.NATIVE)||nativeAds.size()>0){
            nativeButton.setEnabled(true);
        }

        Appodeal.setNativeCallbacks(new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {
                nativeButton.setEnabled(true);

                nativeBuffer = Appodeal.getNativeAds(1);
                if(nativeBuffer.size() > 0 && nativeAds.size() < 4){
                    nativeAds.add(nativeBuffer.get(nativeBuffer.size()-1));
                    adapter.notifyItemInserted(0);
                    nativeBuffer.remove(nativeBuffer.size()-1);
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

        Appodeal.setBannerCallbacks(new BannerCallbacks() {

            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {

                bannerButton.setEnabled(true);
            }

            @Override
            public void onBannerFailedToLoad() {

            }

            @Override
            public void onBannerShown() {
                bannerCount++;
                bannerButton.setText(bannerString +" ("+ bannerCount +")");
                if(bannerCount > 5){
                    hideAds();
                    bannerButton.setText(bannerString);
                    bannerCount = 0;
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
                if(!interstitialLock){
                    interstitialButton.setEnabled(true);
                }
            }

            @Override
            public void onInterstitialFailedToLoad() {
            }

            @Override
            public void onInterstitialShown() {
                interstitialButton.setEnabled(false);
                interstitialLock = true;
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
                        interstitialButton.setText(interstitialString +" ("+millisUntilFinished / 1000+")");
                        // logic to set the EditText could go here
                    }
                    public void onFinish() {
                        interstitialButton.setText(interstitialString);
                        interstitialButton.setEnabled(true);
                        interstitialLock = false;
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
                if(videoCount > 0) rewardedVideoButton.setEnabled(true);
                rewardedVideoButton.setText(rewardVideoString +" ("+(videoCount)+")");
            }

            @Override
            public void onRewardedVideoFailedToLoad() {

            }

            @Override
            public void onRewardedVideoShown() {
                videoCount--;
                if(videoCount <= 0){
                    rewardedVideoButton.setEnabled(false);
                }
                rewardedVideoButton.setText(rewardVideoString +" ("+(videoCount)+")");
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
    }
}