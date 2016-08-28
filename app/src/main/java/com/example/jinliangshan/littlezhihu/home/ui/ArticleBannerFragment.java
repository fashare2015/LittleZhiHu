package com.example.jinliangshan.littlezhihu.home.ui;

import android.view.View;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;

import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class ArticleBannerFragment extends BaseFragment {
    @BindView(R.id.iv_banner)
    GifImageView mIvBanner;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_banner;
    }

    @Override
    protected void initView(View view) {
        mIvBanner.setImageResource(R.drawable.banner_default);
    }

    @Override
    protected void loadData() {

    }
}
