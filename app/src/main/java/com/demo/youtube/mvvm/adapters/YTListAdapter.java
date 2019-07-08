package com.demo.youtube.mvvm.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.youtube.mvvm.BR;
import com.demo.youtube.mvvm.model.VideoDetail;
import com.demo.youtube.mvvm.viewmodel.YTViewModel;

import java.util.List;

/**
 * Created by ${Shivanand} on 6/15/2019.
 */
public class YTListAdapter extends RecyclerView.Adapter<YTListAdapter.GenericViewHolder>{
    private int layoutId;
    private YTViewModel ytViewModel;
    private List<VideoDetail> videoDetailList;
    public YTListAdapter(@LayoutRes int layoutId, YTViewModel ytViewModel) {
        this.layoutId = layoutId;
        this.ytViewModel = ytViewModel;
    }
    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return videoDetailList == null ? 0 : videoDetailList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.bind(ytViewModel, position);
    }

    public void setVideoList(List<VideoDetail> videoDetailList) {
        this.videoDetailList = videoDetailList;
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(YTViewModel viewModel, Integer position) {
            binding.setVariable(BR.ytmodel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

    }
}
