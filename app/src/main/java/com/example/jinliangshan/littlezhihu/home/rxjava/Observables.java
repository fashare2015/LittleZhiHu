package com.example.jinliangshan.littlezhihu.home.rxjava;


import android.annotation.TargetApi;
import android.os.Build;

import com.annimon.stream.Stream;
import com.example.jinliangshan.littlezhihu.home.api.Apis;
import com.example.jinliangshan.littlezhihu.home.model.ArticleDetail;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.util.GsonUtil;
import com.example.jinliangshan.littlezhihu.home.util.ObjectUtil;
import com.example.jinliangshan.littlezhihu.home.util.OkHttpUtil;

import rx.Observable;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-27
 * Time: 19:11
 * <br/><br/>
 */
public class Observables {
    private Observables(){}

    public static Observable<LatestNews> getLatestNewsObservable(){
        return ObservableUtil.newInstance(() ->
                loadDataFrom(Apis.URL_LATEST_NEWS, LatestNews.class)
        );
    }

    public static Observable<ArticleDetail> getArticleObservable(int articleId){
        return ObservableUtil.newInstance(() ->
                loadDataFrom(Apis.URL_NEWS + articleId, ArticleDetail.class)
        );
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static <T> T loadDataFrom(String url, Class<T> targetClass){
        return Stream.of(OkHttpUtil.get(url))
                .filter(ObjectUtil:: nonNull)
                .map(responseString -> GsonUtil.fromJson(responseString, targetClass))
                .findFirst().orElse(null);
    }
}  