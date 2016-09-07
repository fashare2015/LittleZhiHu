package com.example.jinliangshan.littlezhihu.home.rxjava;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class ObservableUtil {
    private ObservableUtil(){}

    /**
     * newInstance: 创建一个 Observable
     * @param onLoadData 数据来源接口
     * @param <T> data 类型
     * @return
     */
    public static <T> Observable<T> newInstance(OnLoadData<T> onLoadData){
        return newInstance(new CommonOnSubscribe<T>(onLoadData));
    }

    private static <T> Observable<T> newInstance(CommonOnSubscribe<T> mCommonOnSubscribe) {
        return Observable.create(mCommonOnSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
                // 被观察的对象在 io 线程进行网络请求，也可以自己新开一个线程
    }

    /**
     * 获取 data: 来自网络任务或是别的...
     * @param <T> data 类型
     */
    public interface OnLoadData<T>{
        T loadData();
    }
}
