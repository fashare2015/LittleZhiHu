package com.example.jinliangshan.littlezhihu.home.ui;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.MyApplication;
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
    public int getLayoutRes() {
        return R.layout.fragment_article_banner;
    }

    @Override
    public void initView() {
        mIvBanner.setImageResource(R.drawable.banner_default);
    }

    @Override
    public void loadData() {

    }

    public void upDateBanner(String imgUrl) {
        MyApplication.getImageLoader()
                .displayImage(imgUrl, mIvBanner);
    }
}
