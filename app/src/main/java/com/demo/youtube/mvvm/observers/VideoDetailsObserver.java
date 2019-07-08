package com.demo.youtube.mvvm.observers;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.demo.youtube.mvvm.BR;


/**
 * Created by ${Shivanand} on 6/27/2019.
 */
public class VideoDetailsObserver extends BaseObservable {
    String VideoName;
    String VideoDesc;
    String URL;
    String VideoId;
    @Bindable
    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
        notifyPropertyChanged(BR.videoName);
    }
    @Bindable
    public String getVideoDesc() {
        return VideoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        VideoDesc = videoDesc;
        notifyPropertyChanged(BR.videoDesc);
    }
    @Bindable
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
        notifyPropertyChanged(BR.uRL);
    }

    @Bindable
    public String getVideoId() {
        return VideoId;

    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
        notifyPropertyChanged(BR.videoId);
    }
}
