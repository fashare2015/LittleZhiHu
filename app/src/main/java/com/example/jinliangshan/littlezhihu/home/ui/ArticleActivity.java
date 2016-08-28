package com.example.jinliangshan.littlezhihu.home.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;

import butterknife.BindBitmap;
import rx.Observable;

public class ArticleActivity extends BaseFragmentActivity {

    private Fragment mArticleFragment;
    private Fragment mArticleBannerFragment;

    @BindBitmap(R.mipmap.ic_launcher)
    Bitmap mDefaultBitmap;

    @Override
    protected void initFragment() {
        mArticleFragment = new ArticleFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleFragment);
        mArticleBannerFragment = new ArticleBannerFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.banner_container, mArticleBannerFragment);
    }

    @Override
    protected void initView() {
//        load(this, R.drawable.banner_default, mIvBanner);
    }

    public static void load(Context context, @DrawableRes int imageRes, ImageView view) {
        Glide.with(context).load(imageRes).crossFade().into(view);
    }

    @Override
    public void loadingFromFragment(Observable<?> dataObservable) {

    }
}
