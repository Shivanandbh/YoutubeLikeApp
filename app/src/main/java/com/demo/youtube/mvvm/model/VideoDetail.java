package com.demo.youtube.mvvm.model;

/**
 * Created by ${Shivanand} on 6/15/2019.
 */
public class VideoDetail {
    String VideoName;
    String VideoDesc;
    String URL;
    String VideoId;

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getVideoDesc() {
        return VideoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        VideoDesc = videoDesc;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }
}
