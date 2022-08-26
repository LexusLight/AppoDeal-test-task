package com.example.appodealtest1337;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appodeal.ads.NativeAd;
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed;
import com.example.appodealtest1337.databinding.NativeItemBinding;

import java.util.List;

public class NativeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<NativeAd> nativeAds;

    public NativeAdapter(List<NativeAd> ads){
        this.nativeAds = ads;
    }

    //    Эта штука берёт xml элемента-айтема
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.native_item,parent, false)
        ){};
    }

    //    Эта штука берёт сам айтем и пичкает его чем хочет.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NativeAdViewNewsFeed native_el = holder.itemView.findViewById(R.id.native_ad_view_news_feed);
        native_el.setNativeAd(this.nativeAds.get(position));
    }

    @Override
    public int getItemCount() {
        return this.nativeAds.size();
    }


}
