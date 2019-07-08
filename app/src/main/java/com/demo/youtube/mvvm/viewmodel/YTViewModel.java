package com.demo.youtube.mvvm.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.demo.youtube.mvvm.R;
import com.demo.youtube.mvvm.adapters.YTListAdapter;
import com.demo.youtube.mvvm.model.VideoDetail;
import com.demo.youtube.mvvm.observers.VideoDetailsObserver;
import com.demo.youtube.mvvm.observers.VideoObserver;


import java.util.List;

/**
 * Created by ${Shivanand} on 6/15/2019.
 */
public class YTViewModel extends ViewModel {
    public YTListAdapter ytListAdapter;
    public VideoObserver videoObserver;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public RecyclerView.OnScrollListener onScrollChangeListener;
    public MutableLiveData<VideoDetail> selectedVideoDetail;
    public BottomSheetBehavior.BottomSheetCallback bottomSheetBehavior;
    public int size;
    public VideoDetailsObserver videoDetailsObserver;
    public MutableLiveData<Integer> bottomSheetState;
    public void init() {
        videoObserver = new VideoObserver();
        videoDetailsObserver = new VideoDetailsObserver();
        selectedVideoDetail = new MutableLiveData<>();
        bottomSheetState = new MutableLiveData<Integer>();
        ytListAdapter = new YTListAdapter(R.layout.yt_list, this);
        onScrollChangeListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= size) {
                            loading = false;
                            size = size + 10;
                            fetchList(size);
                            loading = true;
                        }
                    }
                }
            }
        };
        bottomSheetBehavior = new BottomSheetBehavior.BottomSheetCallback(){

            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        videoObserver.setVisibilityToBottomSheet(BottomSheetBehavior.STATE_EXPANDED);
                        bottomSheetState.setValue(BottomSheetBehavior.STATE_EXPANDED);
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        videoObserver.setVisibilityToBottomSheet(BottomSheetBehavior.STATE_COLLAPSED);
                        bottomSheetState.setValue(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                    break;
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) { }
        };
    }

    public RecyclerView.OnScrollListener getOnScrollChangeListener() {
        return onScrollChangeListener;
    }

    public BottomSheetBehavior.BottomSheetCallback getBottomSheetBehavior() {
        return bottomSheetBehavior;
    }

    public void setVideosInAdapter(List<VideoDetail> videoDetailList) {
        this.ytListAdapter.setVideoList(videoDetailList);
        this.ytListAdapter.notifyDataSetChanged();
    }

    public YTListAdapter getYtListAdapter() {
        return ytListAdapter;
    }

    public void fetchList(int size) {

        this.size = size;
        videoObserver.fetchList(size);
    }

    public MutableLiveData<List<VideoDetail>> getVideos() {
        return videoObserver.getMutableVideoDetail();
    }

    public MutableLiveData<VideoDetail> getSelectedVideoDetail() {
        return selectedVideoDetail;
    }

    public void onItemClick(Integer index) {

        VideoDetail videoDetail = getVideoAt(index);
        videoDetailsObserver.setVideoName(videoDetail.getVideoName());
        videoDetailsObserver.setURL(videoDetail.getURL());
        videoDetailsObserver.setVideoDesc(videoDetail.getVideoDesc());
        videoDetailsObserver.setVideoId(videoDetail.getVideoId());

        videoObserver.setVisibilityToBottomSheet(BottomSheetBehavior.STATE_EXPANDED);
        selectedVideoDetail.setValue(videoDetail);
    }

    public VideoDetail getVideoAt(Integer index) {
        if (videoObserver.getMutableVideoDetail().getValue() != null && index != null &&
                videoObserver.getMutableVideoDetail().getValue().size() > index) {
            return videoObserver.getMutableVideoDetail().getValue().get(index);
        }
        return null;
    }

    public VideoObserver getVideoObserver() {
        return videoObserver;
    }
    public MutableLiveData<Integer> getBottomSheetState() {
        return bottomSheetState;
    }

    @BindingAdapter({"android:onScroll"})
    public static void bindOnScrollListener(RecyclerView recyclerView, RecyclerView.OnScrollListener onScrollChangeListener) {
        recyclerView.setOnScrollListener(onScrollChangeListener);
    }
    @BindingAdapter({"android:viewBottomSheetBehavior"})
    public static void viewBottomSheet(View v, BottomSheetBehavior.BottomSheetCallback bottomSheetCallback) {
        BottomSheetBehavior<View> viewBottomSheetBehavior = BottomSheetBehavior.from(v);
        viewBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
    }
}
