package com.demo.youtube.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.demo.youtube.mvvm.BR;

/**
 * Created by ${Shivanand} on 6/25/2019.
 */
public  class VisibilityModel  extends BaseObservable {
    public boolean isLoading;
    public String progress;

    @Bindable
    public boolean isLoading() {
        return isLoading;
    }
    public void setLoading(boolean loading) {
        this.isLoading = loading;
        notifyPropertyChanged(BR.loading);
    }
    @Bindable
    public String getProgress() {
        return progress;
    }
    public void setProgress(String progress) {
        this.progress = progress;
        notifyPropertyChanged(BR.progress);
    }
}
