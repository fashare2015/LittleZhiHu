//package com.example.jinliangshan.littlezhihu.home.rxjava;
//
//import rx.Observable;
//import rx.Subscriber;
//
///**
// * Created by jinliangshan on 16/8/26.
// */
//public class BaseOnSubscribe<T> implements Observable.OnSubscribe<T> {
//    @Override
//    public void call(Subscriber<? super T> subscriber) {
//        String data = getData(); // 获取数据: 网络任务或是别的...
//        if(data == null){       // 获取失败
//            subscriber.onError(new Throwable("data 为空"));
//        }else                   // 获取成功
//            subscriber.onNext(data);
//        subscriber.onCompleted();
//    }
//}
