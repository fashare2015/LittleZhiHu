package com.example.jinliangshan.littlezhihu.home;

import android.app.Application;

import com.example.jinliangshan.littlezhihu.home.imageloader.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-27
 * Time: 03:01
 * <br/><br/>
 * 需要在 manifest 里配置 application name
 */
public class MyApplication extends Application {
    private static MyApplication sApplication;

    private ImageLoader mImageLoader;

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static MyApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = MyApplication.this;
        initImageLoader();
    }

    private void initImageLoader() {
        // Create global configuration and initialize ImageLoader with this config
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderUtil.getSimpleConfig(this));
    }
}