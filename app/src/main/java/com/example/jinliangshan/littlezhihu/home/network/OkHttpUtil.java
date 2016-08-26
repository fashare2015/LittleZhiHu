package com.example.jinliangshan.littlezhihu.home.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class OkHttpUtil {
    public static final String TAG = "OkHttpUtil";
    private static OkHttpUtil mInstance;

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private OkHttpUtil(){
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpUtil getInstance(){
        if(mInstance == null){
            synchronized (OkHttpUtil.class){
                if(mInstance == null)
                    mInstance = new OkHttpUtil();
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
            Log.d(TAG, "get ===> " + responseStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
    }
}
