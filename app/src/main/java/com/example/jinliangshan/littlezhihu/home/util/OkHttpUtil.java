package com.example.jinliangshan.littlezhihu.home.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class OkHttpUtil {
    public static final String TAG = "OkHttpUtil";

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    public static String get(String url){
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
