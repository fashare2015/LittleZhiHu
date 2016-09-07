package com.example.jinliangshan.littlezhihu.home.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.example.jinliangshan.littlezhihu.R;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by apple on 16-8-28.
 */

public class ImageLoaderUtil {
    private ImageLoaderUtil(){}

    public static DisplayImageOptions cachedOptions = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.img_default)
            .showImageOnFail(R.drawable.img_default)
            .showImageOnLoading(R.drawable.img_default)
            //是否设置在加载之前重置view
            .resetViewBeforeLoading(false)
            .delayBeforeLoading(1000)
            .cacheInMemory(true)   //是否缓存在内存中
            .cacheOnDisk(false) //是否缓存在文件中
//            .preProcessor(...)
//            .postProcessor(...)
//            .extraForDownloader(...)
            .considerExifParams(false)
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)    //Image的缩放类型
            .bitmapConfig(Bitmap.Config.ARGB_8888)  //bitmap 的config
//            .decodingOptions()
            .displayer(new SimpleBitmapDisplayer()) //设置显示，可以设置为圆角
            .handler(new Handler())
            .build();

    public static ImageLoaderConfiguration getSimpleConfig(Context context){
        return new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
//                .taskExecutor(...)
//                .taskExecutorForCachedImages(...)
//                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
//                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .defaultDisplayImageOptions(cachedOptions)
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
    }
}
