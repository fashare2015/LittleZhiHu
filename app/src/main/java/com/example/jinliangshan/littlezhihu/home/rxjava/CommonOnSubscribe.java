package com.example.jinliangshan.littlezhihu.home.rxjava;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-26
 * Time: 23:50
 * <br/><br/>
 */
public class CommonOnSubscribe<T> implements Observable.OnSubscribe<T> {
    protected final String TAG = this.getClass().getSimpleName();
    private ObservableUtil.OnLoadData<T> mOnLoadData;
    private T mData = null;

    private CommonOnSubscribe() {
    }

    public CommonOnSubscribe(ObservableUtil.OnLoadData<T> mOnLoadData) {
        this.mOnLoadData = mOnLoadData;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        if (mData == null && mOnLoadData != null)   // mData 拉取过数据, 就不再拉取
            mData = mOnLoadData.loadData();
        if (mData == null) {          // 获取失败
            Log.d(TAG, "onError: data is null");
            subscriber.onError(new Throwable("获取数据异常"));
        } else {                       // 获取成功
            Log.d(TAG, "onNext: data => " + mData);
            subscriber.onNext(mData);
        }
        Log.d(TAG, "onCompleted");
        subscriber.onCompleted();
    }
}