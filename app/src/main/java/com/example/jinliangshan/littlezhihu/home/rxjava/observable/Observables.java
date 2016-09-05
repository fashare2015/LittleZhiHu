package com.example.jinliangshan.littlezhihu.home.rxjava.observable;


import com.annimon.stream.Stream;
import com.example.jinliangshan.littlezhihu.home.model.Article;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.network.OkHttpUtil;
import com.example.jinliangshan.littlezhihu.home.network.api.Apis;
import com.google.gson.Gson;

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

    public static Observable<Article> getArticleObservable(int articleId){
        return ObservableUtil.newInstance(() ->
                loadDataFrom(Apis.URL_NEWS + articleId, Article.class)
        );
    }

    private static <T> T loadDataFrom(String url, Class<T> targetClass){
        return Stream.of(OkHttpUtil.getInstance().get(url))
                .map(responseString -> new Gson().fromJson(responseString, targetClass))
                .findFirst().orElse(null);
    }
}  