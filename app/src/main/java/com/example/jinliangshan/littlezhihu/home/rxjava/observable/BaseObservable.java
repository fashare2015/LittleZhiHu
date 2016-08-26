package com.example.jinliangshan.littlezhihu.home.rxjava.observable;


import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by jinliangshan on 16/8/26.
 */
public abstract class BaseObservable<T> extends Observable<T>{
    protected T mData;

    protected BaseObservable(OnSubscribe<T> f) {
        super(f);
    }

    public Observable<T> newInstance() {
        return Observable.create(
                new BaseOnSubscribe<T>(mData = loadData())
        ).subscribeOn(Schedulers.io());  // 被观察的对象在 io 线程进行网络请求，也可以自己新开一个线程
    }

    /**
     * 获取数据: 网络任务或是别的...
     */
    abstract public T loadData();

    public static class BaseOnSubscribe<T> implements Observable.OnSubscribe<T> {
        private T mData;

        public BaseOnSubscribe(T mData) {
            this.mData = mData;
        }

        @Override
        public void call(Subscriber<? super T> subscriber) {
            if(mData == null){          // 获取失败
                subscriber.onError(new Throwable("data 为空"));
            }else                       // 获取成功
                subscriber.onNext(mData);
            subscriber.onCompleted();
        }
    }
}
