package com.demo.youtube.mvvm.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.demo.youtube.mvvm.R;
import com.demo.youtube.mvvm.databinding.FragmentHomeBinding;
import com.demo.youtube.mvvm.model.VideoDetail;
import com.demo.youtube.mvvm.utils.Constants;
import com.demo.youtube.mvvm.viewmodel.YTViewModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import java.util.List;

/**
 * Created by ${Shivanand} on 6/20/2019.
 */
public class HomeFragment extends Fragment  {
    private View rootView;
    public YTViewModel ytViewModel;
    public FragmentHomeBinding fragmentHomeBinding;
    YouTubePlayerSupportFragment youTubePlayerFragment;
    private YouTubePlayer YPlayer;
    LinearLayout youTubeLayout;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        ytViewModel = ViewModelProviders.of(this).get(YTViewModel.class);
        if(savedInstanceState==null){
            ytViewModel.init();
        }
        fragmentHomeBinding.setYtmodel(ytViewModel);
        fragmentHomeBinding.setLifecycleOwner(this);

        rootView = fragmentHomeBinding.getRoot();
        youTubePlayerFragment = (YouTubePlayerSupportFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        youTubeLayout = rootView.findViewById(R.id.youTubeLayout);
        setupVideoData();
        setupOnItemClick();
        setupBottomSheetListener();
        return rootView;
    }
    private void setupVideoData() {
        ytViewModel.fetchList(10);
        ytViewModel.getVideos().observe(this, new Observer<List<VideoDetail>>() {
            @Override
            public void onChanged(@Nullable List<VideoDetail> videoDetails) {
                ytViewModel.setVideosInAdapter(videoDetails);
            }
        });
    }

    private void setupOnItemClick() {
        ytViewModel.getSelectedVideoDetail().observe(this, new Observer<VideoDetail>() {
            @Override
            public void onChanged(@Nullable final VideoDetail videoDetail) {
                 youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
                 youTubePlayerFragment.initialize(Constants.API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

                        if (!wasRestored) {
                            YPlayer = player;
                            YPlayer.loadVideo(videoDetail.getVideoId());
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                            YPlayer.play();
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
                        System.out.println("Inside the error"+provider);
                        if (errorReason.isUserRecoverableError()) {
                            errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
                        } else {
                            Toast.makeText(getActivity(), errorReason.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                if(getChildFragmentManager().findFragmentById(R.id.youtube_fragment) != null) {
                    getChildFragmentManager().beginTransaction().remove(getChildFragmentManager().findFragmentById(R.id.youtube_fragment)).commit();
                }
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
            }
        });
    }
    private void setupBottomSheetListener() {
        ytViewModel.getBottomSheetState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Toast.makeText(getActivity(),"Status="+integer,Toast.LENGTH_LONG).show();
                if(youTubeLayout!=null && integer==4){
                    YPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                }
            }
        });
    }

}
