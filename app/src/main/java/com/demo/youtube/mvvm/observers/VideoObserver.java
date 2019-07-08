package com.demo.youtube.mvvm.observers;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.util.Log;

import com.demo.youtube.mvvm.model.BottomSheetStateModel;
import com.demo.youtube.mvvm.model.ProgressBarStatusModel;
import com.demo.youtube.mvvm.model.VideoDetail;
import com.demo.youtube.mvvm.net.Api;
import com.demo.youtube.mvvm.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${Shivanand} on 6/15/2019.
 */
public class VideoObserver extends BaseObservable {
    public MutableLiveData<List<VideoDetail>> mutableVideoDetail = new MutableLiveData<>();
    public List<VideoDetail> videoDetailList;

    public ProgressBarStatusModel progressBarStatusModel = new ProgressBarStatusModel();
    public BottomSheetStateModel bottomSheetStateModel = new BottomSheetStateModel();
    public void fetchList(int size) {
        videoDetailList = new ArrayList<VideoDetail>();
        progressBarStatusModel.setLoading(true);
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if(response.body()!=null){
                            String responseReceived = response.body().string();
                            JSONObject jsonObject=new JSONObject(responseReceived);
                            JSONArray jsonArray=jsonObject.getJSONArray("items");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonVideoId=jsonObject1.getJSONObject("id");
                                JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
                                JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
                                VideoDetail videoDetails=new VideoDetail();

                                String videoid=jsonVideoId.getString("videoId");

                                videoDetails.setURL(jsonObjectdefault.getString("url"));
                                videoDetails.setVideoName(jsonsnippet.getString("title"));
                                videoDetails.setVideoDesc(jsonsnippet.getString("description"));
                                videoDetails.setVideoId(videoid);
                                videoDetailList.add(videoDetails);
                            }
                            progressBarStatusModel.setLoading(false);
                            mutableVideoDetail.setValue(videoDetailList);
                        }else if(response.errorBody()!=null){
                            String responseReceived = response.errorBody().string();
                            JSONObject jsonObject=new JSONObject(responseReceived);
                            int errorCode = jsonObject.getJSONObject("error").getInt("code");
                            String errorMessage = jsonObject.getJSONObject("error").getString("message");
                            if(errorCode==Constants.ERROR_400){
                                progressBarStatusModel.setLoading(false);
                                progressBarStatusModel.setProgress("Yeah! You have reached end of the List");
                            }else if(errorCode==Constants.ERROR_403){
                                progressBarStatusModel.setLoading(false);
                                progressBarStatusModel.setProgress("The request cannot be completed because you have reached your daily limit quota.");
                            }
                            System.out.println("Error Response List==>"+jsonObject);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Test", t.getMessage(), t);
            }
        };
        Api.getApi().getVideos(Constants.API_KEY, Constants.CHANNEL_ID, Constants.PART, size).enqueue(callback);
    }
    /*This method helps to observe the Bottom sheet behavior*/
    public void setVisibilityToBottomSheet(int newState){
        bottomSheetStateModel.setMyState(newState);
    }
    public MutableLiveData<List<VideoDetail>> getMutableVideoDetail() {
        return mutableVideoDetail;
    }
//AIzaSyD9_jktgSHHg6E42PFeUrUte5YCHeRHpzw   //AIzaSyDY1f8kc70bVDbJYyIWOGprq0sZ55XYUfA
}
