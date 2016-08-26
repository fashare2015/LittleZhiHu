//package com.example.jinliangshan.littlezhihu.home.rxjava.observable;
//
//import com.annimon.stream.Stream;
//import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
//import com.example.jinliangshan.littlezhihu.home.network.OkHttpUtil;
//import com.example.jinliangshan.littlezhihu.home.network.api.Apis;
//import com.google.gson.Gson;
//
//
///**
// * Created by jinliangshan on 16/8/26.
// */
//public class LatestNewsObservable extends ObservableUtil<LatestNews> {
//
//    public LatestNewsObservable(OnSubscribe<LatestNews> f) {
//        super(f);
//    }
//
//    @Override
//    public LatestNews loadData() {
//        return Stream.of(OkHttpUtil.getInstance().get(Apis.URL_LATEST_NEWS))
//                .map(responseString -> new Gson().fromJson(responseString, LatestNews.class))
//                .findFirst().orElse(null);
//    }
//}
