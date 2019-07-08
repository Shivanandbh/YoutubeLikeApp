package com.demo.youtube.mvvm.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ${Shivanand} on 6/15/2019.
 */
public class Api {

    private static ApiInterface api;

    //https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=CHANNEL ID&maxResults=25&key=GOOGLE YOU TUBE API KEY
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static ApiInterface getApi() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }

    public interface ApiInterface {
        /*To get the Video list from the Google Api by passing required parameters*/
        @GET("search")
        Call<ResponseBody> getVideos(@Query("key") String developerKey, @Query("channelId") String channelId, @Query("part") String part, @Query("maxResults") int maxResults);
    }
}