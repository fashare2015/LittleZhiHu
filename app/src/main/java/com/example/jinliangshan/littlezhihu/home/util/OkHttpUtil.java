package com.example.jinliangshan.littlezhihu.home.util;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class OkHttpUtil {
    protected static final String TAG = OkHttpUtil.class.getSimpleName();

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    public static String get(String url){
        Request request = new Request.Builder().url(url).build();

        String responseStr;
        try {
            responseStr = mOkHttpClient.newCall(request).execute()
                    .body().string();
            Log.d(TAG, "get ===> " + responseStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseStr;
    }
}
