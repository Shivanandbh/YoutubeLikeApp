package com.demo.youtube.mvvm.bindings;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.youtube.mvvm.R;
import com.demo.youtube.mvvm.views.SimpleDividerItemDecoration;

/**
 * Created by ${Shivanand} on 6/15/2019.
 */

public class CustomViewBindings {

    @BindingAdapter("imageUrl")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            if (imageView.getTag(R.id.image_url) == null || !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                Glide.with(imageView).load(imageUrl).into(imageView);
            }
        } else {
            imageView.setTag(R.id.image_url, null);
            imageView.setImageBitmap(null);
        }
    }
    @BindingAdapter({"android:ytadapter"})
    public static void bindYtVideoRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }
    @BindingAdapter({"android:viewBottomSheet"})
    public static void viewBottomSheet(View v, int bottomSheetBehaviorState) {
        BottomSheetBehavior<View> viewBottomSheetBehavior = BottomSheetBehavior.from(v);
        viewBottomSheetBehavior.setState(bottomSheetBehaviorState);
    }
    /*If you are using webview to show video*/
    @BindingAdapter("playVideo")
    public static void bindYoutubeVideos(WebView youTubePlayerView, final String videoId) {
        youTubePlayerView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = youTubePlayerView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        youTubePlayerView.loadUrl("https://www.youtube.com/watch?v="+videoId);
    }

}
