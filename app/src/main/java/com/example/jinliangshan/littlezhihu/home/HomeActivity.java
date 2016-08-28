package com.example.jinliangshan.littlezhihu.home;

import android.support.v4.app.Fragment;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.ui.ArticleListFragment;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;

import java.util.List;

import rx.Observable;

public class HomeActivity extends BaseFragmentActivity {
    private static final String TAG = "HomeActivity";
    private Fragment mArticleListFragment;
    private HomeBannerFragment mHomeBannerFragment;

    @Override
    protected void initFragment() {
        mArticleListFragment = new ArticleListFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleListFragment);

        mHomeBannerFragment = new HomeBannerFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.banner_container, mHomeBannerFragment);
    }

    @Override
    public void loadingFromFragment(Observable<?> dataObservable) {
        dataObservable.cast(LatestNews.class)
                .map(LatestNews:: getTop_stories)
                .subscribe(this:: notifyBannerUpdate);
    }

    private void notifyBannerUpdate(List topStories){
        mHomeBannerFragment.upDateBanner(topStories);
    }
}

