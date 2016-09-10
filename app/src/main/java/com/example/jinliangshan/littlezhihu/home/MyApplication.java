package com.example.jinliangshan.littlezhihu.home;

import android.app.Application;

import com.example.jinliangshan.littlezhihu.home.imageloader.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-27
 * Time: 03:01
 * <br/><br/>
 * 需要在 manifest 里配置 application name
 */
public class MyApplication extends Application {
    protected static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication instance;

    private ImageLoader mImageLoader;

    private RefWatcher mRefWatcher;

    public static RefWatcher getRefWatcher() {
        return instance.mRefWatcher;
    }

    public static ImageLoader getImageLoader() {
        return instance.mImageLoader;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = MyApplication.this;
        initImageLoader();
        initLeakCanary();
    }

    private void initImageLoader() {
        // Create global configuration and initialize ImageLoader with this config
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderUtil.getSimpleConfig(this));
    }

    private void initLeakCanary() {
        mRefWatcher = LeakCanary.install(this);
    }
}
