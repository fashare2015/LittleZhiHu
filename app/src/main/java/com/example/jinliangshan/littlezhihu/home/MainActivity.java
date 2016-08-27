package com.example.jinliangshan.littlezhihu.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.ui.ArticleListFragment;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;
import com.example.jinliangshan.littlezhihu.home.widget.CarouselAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindView;
import rx.Observable;

public class MainActivity extends BaseFragmentActivity {
    private static final String TAG = "MainActivity";
    Fragment mArticleListFragment;

    @BindView(R.id.icvp_banner)
    ViewPager mIcvpBanner;
    private BannerAdapter mBannerAdapter;

    @BindBitmap(R.mipmap.ic_launcher)
    Bitmap mDefaultBitmap;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_activity_article;
    }

    @Override
    protected void initFragment() {
        mArticleListFragment = new ArticleListFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleListFragment);
    }

    @Override
    protected void initView() {
        mBannerAdapter = new BannerAdapter(this);
        mBannerAdapter.setDataList(Arrays.asList(
                new LatestNews.TopArticle(),
                new LatestNews.TopArticle(),
                new LatestNews.TopArticle()
        ));
        // HorizontalInfiniteCycleViewPager 有个 bug
        // mBannerAdapter 的数据加载必须先与 setAdapter
        mIcvpBanner.setAdapter(mBannerAdapter);

        mBannerAdapter.setOnTimerSchedule(() -> {
            if(mBannerAdapter == null || mBannerAdapter.getCount() == 0)
                return ;
            mIcvpBanner.post(() -> {    // 图片轮播, post 到 MainThread
                Log.i(TAG, "post");
                mIcvpBanner.setCurrentItem(
                        (mIcvpBanner.getCurrentItem()+1) % mBannerAdapter.getCount()
                );
            });
        });
        mBannerAdapter.start();
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

    private void upDateBanner(List topStories){
        mBannerAdapter.setDataList(topStories);
        mIcvpBanner.setAdapter(mBannerAdapter);
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

    public static class BannerAdapter extends CarouselAdapter<LatestNews.TopArticle> {
        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.cv_banner_item)
        CardView mCvBannerItem;

        @BindBitmap(R.mipmap.ic_launcher)
        Bitmap mDefaultBitmap;

        public BannerAdapter(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutRes() {
            return R.layout.item_banner;
        }

        @Override
        protected void onBind(LatestNews.TopArticle topArticle, int position) {
            mIvImage.setImageBitmap(mDefaultBitmap);    // 先显示默认图片, 下面再次异步加载图片
            if(topArticle.getImage() != null)
                MyApplication.getInstance().getImageLoader()
                        .displayImage(topArticle.getImage(), mIvImage);
        }
    }
}

