package com.example.jinliangshan.littlezhihu.home;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-27
 * Time: 03:01
 * <br/><br/>
 */
public class MyApplication extends Application {
    private static MyApplication sApplication;

    private ImageLoader mImageLoader;

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private MyApplication(){
        super();
    }

    public static MyApplication getInstance() {
        return sApplication = new MyApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    private void initImageLoader() {
        // Create global configuration and initialize ImageLoader with this config
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(new ImageLoaderConfiguration.Builder(this).build());
    }
}