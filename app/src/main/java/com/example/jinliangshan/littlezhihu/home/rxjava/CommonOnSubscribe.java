package com.example.jinliangshan.littlezhihu.home.rxjava;

import rx.Observable;
import rx.Subscriber;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-26
 * Time: 23:50
 * <br/><br/>
 */
public class CommonOnSubscribe<T> implements Observable.OnSubscribe<T> {
    private ObservableUtil.OnLoadData<T> mOnLoadData;

    private CommonOnSubscribe() {
    }

    public CommonOnSubscribe(ObservableUtil.OnLoadData<T> mOnLoadData) {
        this.mOnLoadData = mOnLoadData;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        T data = null;
        if (mOnLoadData != null)
            data = mOnLoadData.loadData();
        if (data == null) {          // 获取失败
            subscriber.onError(new Throwable("data 为空"));
        } else                       // 获取成功
            subscriber.onNext(data);
        subscriber.onCompleted();
    }
}