package com.example.jinliangshan.littlezhihu.home.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.model.ArticleDetail;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;
import com.example.jinliangshan.littlezhihu.home.util.TransitionUtils;

import butterknife.BindBitmap;
import rx.Observable;

public class ArticleActivity extends BaseFragmentActivity {
    public static final String ARTICLE_ID = "ARTICLE_ID";

    private Fragment mArticleFragment;
    private ArticleBannerFragment mArticleBannerFragment;

    @BindBitmap(R.mipmap.ic_launcher)
    Bitmap mDefaultBitmap;

    private int mArticleId;

    @Override
    public void initBundle() {
        mArticleId = getIntent().getIntExtra(ARTICLE_ID, 0);
    }

    @Override
    protected void initFragment() {
        mArticleFragment = ArticleFragment.getInstance(mArticleId);
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleFragment);
        mArticleBannerFragment = new ArticleBannerFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.banner_container, mArticleBannerFragment);
    }

    @Override
    public void initView() {
//        load(this, R.drawable.banner_default, mIvBanner);
    }

    @Override
    public void loadData() {

    }

    public static void load(Context context, @DrawableRes int imageRes, ImageView view) {
        Glide.with(context).load(imageRes).crossFade().into(view);
    }

    @Override
    public void loadingFromFragment(Observable<?> dataObservable) {
        dataObservable.cast(ArticleDetail.class)
                .map(ArticleDetail:: getImage)
                .subscribe(this:: notifyBannerUpdate);
    }

    private void notifyBannerUpdate(String imgUrl){
        mArticleBannerFragment.upDateBanner(imgUrl);
    }

    public static void startThis(Activity from, int articleId, View... sharedViews){
        Intent intent = new Intent(from, ArticleActivity.class);
        intent.putExtra(ARTICLE_ID, articleId);
        Bundle options = TransitionUtils.makeActivityOptionsBundle(from, sharedViews);
        ActivityCompat.startActivity(from, intent, options);
    }
}
