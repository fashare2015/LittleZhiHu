package com.example.jinliangshan.littlezhihu.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.cache.BitmapCache;
import com.example.jinliangshan.littlezhihu.home.model.TopArticle;
import com.example.jinliangshan.littlezhihu.home.widget.TimerPagerAdapter;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindBitmap;
import butterknife.BindView;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-28
 * Time: 19:15
 * <br/><br/>
 */
public class HomeBannerAdapter extends TimerPagerAdapter<TopArticle> {
    private static final String TAG = "HomeBannerAdapter";
    private BitmapCache mBitmapCache = new BitmapCache();

    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.cv_banner_item)
    CardView mCvBannerItem;

    @BindBitmap(R.drawable.img_default)
    Bitmap mDefaultBitmap;

    Bitmap bitmap;

    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }

    public HomeBannerAdapter(Context context) {
        super(context);
    }

    @Override
    public void clearReferences() {
        super.clearReferences();
        mBitmapCache.clear();
        mBitmapCache = null;
        mIvImage = null;
        mCvBannerItem = null;
        mDefaultBitmap = null;
        bitmap = null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.item_banner;
    }

    @Override
    protected void onBind(TopArticle topArticle, int pos) {
        mIvImage.setImageBitmap(mDefaultBitmap);    // 先显示默认图片, 下面再次异步加载图片

        if (topArticle.getImage() != null) {
            /**
             * 妈的, ImageLoader 的缓存配置不起作用,
             * debug 一会儿就烧我上百流量,
             * 撸个 BitmapCache 压压惊
             */
            if (mBitmapCache.get(pos + "") != null) {  // 先从缓存加载
                Log.i(TAG, "get bitmap " + pos + " from cache");
                bitmap = mBitmapCache.get(pos + "");
                mIvImage.setImageBitmap(bitmap);
            } else {
                Log.i(TAG, "get bitmap " + pos + " from network");
                MyApplication.getImageLoader()
                        .displayImage(topArticle.getImage(), mIvImage, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                bitmap = loadedImage;
                                mBitmapCache.put(pos + "", bitmap); // 缓存图片
                            }
                        });
            }
        }
    }
}
