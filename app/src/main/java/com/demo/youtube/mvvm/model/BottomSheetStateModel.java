package com.demo.youtube.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.BottomSheetBehavior;

import com.demo.youtube.mvvm.BR;

/**
 * Created by ${Shivanand} on 6/27/2019.
 */
public class BottomSheetStateModel extends BaseObservable {
    public int myState=BottomSheetBehavior.STATE_HIDDEN;

    @Bindable
    public int getMyState() {
        return myState;
    }

    public void setMyState(int myState) {
        this.myState = myState;
        notifyPropertyChanged(BR.myState);
    }

}
