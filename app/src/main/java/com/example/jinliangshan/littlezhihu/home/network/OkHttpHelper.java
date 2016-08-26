package com.example.jinliangshan.littlezhihu.home.network;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class OkHttpHelper {
    private static OkHttpHelper mInstance;

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private OkHttpHelper(){
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpHelper getInstance(){
        if(mInstance == null){
            synchronized (OkHttpHelper.class){
                if(mInstance == null)
                    mInstance = new OkHttpHelper();
            }
        }
        return mInstance;
    }

    public String get(String url){
        Request request = new Request.Builder().url(url).build();

        String responseStr = null;
        try {
            responseStr = mOkHttpClient.newCall(request).execute()
                    .body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
    }



}
