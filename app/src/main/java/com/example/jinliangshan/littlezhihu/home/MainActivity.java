package com.example.jinliangshan.littlezhihu.home;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.ui.ArticleListFragment;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindView;
import rx.Observable;

public class MainActivity extends BaseFragmentActivity {
    private static final String TAG = "MainActivity";
    Fragment mArticleListFragment;

    @BindView(R.id.icvp_banner)
    HorizontalInfiniteCycleViewPager mIcvpBanner;
    private BannerAdapter mBannerAdapter;
    private int mBasePageIndex = -1;    // 第一页的基准下标

    @BindBitmap(R.mipmap.ic_launcher)
    Bitmap mDefaultBitmap;

    private List<LatestNews.TopArticle> mDefaultBannerDatas = Arrays.asList(
            new LatestNews.TopArticle(),
            new LatestNews.TopArticle(),
            new LatestNews.TopArticle()
    );

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_activity_article_list;
    }

    @Override
    protected void initFragment() {
        mArticleListFragment = new ArticleListFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleListFragment);
    }

    @Override
    protected void initView() {
        mBannerAdapter = new BannerAdapter(this);
        mIcvpBanner.setAdapter(mBannerAdapter);
        upDateBanner(mDefaultBannerDatas);

        mBannerAdapter.setOnTimerSchedule(() -> {
            if(mBannerAdapter == null || mBannerAdapter.getCount() == 0)
                return ;
            mIcvpBanner.post(() -> {    // 图片轮播, post 到 MainThread
                int nextPageOffset = mIcvpBanner.getRealItem()+1;   // getRealItem() 代替 getCurrentItem()
                Log.i(TAG, "select: " + nextPageOffset);
                mIcvpBanner.setCurrentItem(nextPageOffset);
            });
        });

        if(mBasePageIndex == -1)
            mBasePageIndex = mIcvpBanner.getCurrentItem();
        debugPageIndex();
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void loadingFromFragment(Observable<?> dataObservable) {
        dataObservable.cast(LatestNews.class)
                .map(LatestNews:: getTop_stories)
//                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this:: upDateBanner
                );
    }

    /**
     * HorizontalInfiniteCycleViewPager 有个 bug
     * mBannerAdapter 的数据加载必须先与 setAdapter
     * @param topStories
     */
    private void upDateBanner(List topStories){
        mBannerAdapter.stop();

        mBannerAdapter.setDataList(topStories);
        mIcvpBanner.setAdapter(mBannerAdapter);
        mIcvpBanner.notifyDataSetChanged(); // 用这个代替 adapter 的 notify...()
        mIcvpBanner.setCurrentItem(0);

        mBannerAdapter.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBannerAdapter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBannerAdapter.stop();
    }

    private void debugPageIndex() {
        mIcvpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "curPage: " + position + " - " + mIcvpBanner.getCurrentItem() + " - " + mIcvpBanner.getRealItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}

