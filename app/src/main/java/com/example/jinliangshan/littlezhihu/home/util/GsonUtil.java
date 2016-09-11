package com.example.jinliangshan.littlezhihu.home.util;

import com.google.gson.Gson;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-09-11
 * Time: 15:14
 * <br/><br/>
 */
public class GsonUtil {
    public static final Gson GSON = new Gson();

    public static <T> T fromJson(String jsonString, Class<T> targetClazz){
        return GSON.fromJson(jsonString, targetClazz);
    }

    public static <T> String toJson(T t) {
        return GSON.toJson(t);
    }
}