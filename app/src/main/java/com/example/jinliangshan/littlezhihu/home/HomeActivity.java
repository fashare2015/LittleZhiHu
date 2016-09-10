package com.example.jinliangshan.littlezhihu.home;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.ui.ArticleListFragment;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;
import com.example.jinliangshan.littlezhihu.home.util.HidingAnimUtil;

import java.util.List;

import butterknife.BindView;
import rx.Observable;

public class HomeActivity extends BaseFragmentActivity implements ArticleListFragment.OnArticleListScrollListener{
    private ArticleListFragment mArticleListFragment;

    @BindView(R.id.fab_menu)
    FloatingActionButton mFabMenu;
    private HidingAnimUtil mTbHidingAnimUtil;
    private HidingAnimUtil mFabHidingAnimUtil;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_activity_home;
    }

    @Override
    protected void initFragment() {
        mArticleListFragment = new ArticleListFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleListFragment);

        mArticleListFragment.setOnArticleListScrollListener(this);
    }

    @Override
    public void initView() {
        initAnim();
        simpleLeak();
    }

    /**
     * 内存泄漏的示例
     * 10s内, HomeActivity 将被 handler 引用着
     */
    private void simpleLeak() {
        new Handler().postDelayed(() -> {}, 10000);
    }

    private void initAnim() {
        mTbHidingAnimUtil = new HidingAnimUtil(mTbCommon)
                .setHidingMod(HidingAnimUtil.HIDING_MOD_TOP);
        mFabHidingAnimUtil = new HidingAnimUtil(mFabMenu)
                .setHidingMod(HidingAnimUtil.HIDING_MOD_BOTTOM);
    }

    @Override
    public void onScrolledUp() {
        mTbHidingAnimUtil.hide();
        mFabHidingAnimUtil.hide();
    }

    @Override
    public void onScrolledDown() {
        mTbHidingAnimUtil.show();
        mFabHidingAnimUtil.show();
    }

    @Override
    public void loadingFromFragment(Observable<?> dataObservable) {
//        dataObservable.cast(LatestNews.class)
//                .map(LatestNews::getTop_stories)
//                .subscribe(this::notifyBannerUpdate);
    }

    private void notifyBannerUpdate(List topStories) {
//        mHomeBannerFragment.upDateBanner(topStories);
    }
}

