package com.example.jinliangshan.littlezhihu.home;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindView;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class HomeBannerFragment extends BaseFragment {
    private static final String TAG = "ArticleBannerFragment";
    @BindView(R.id.icvp_banner)
    HorizontalInfiniteCycleViewPager mIcvpBanner;
    private HomeBannerAdapter mHomeBannerAdapter;

    @BindBitmap(R.mipmap.ic_launcher)
    Bitmap mDefaultBitmap;

    private List<LatestNews.TopArticle> mDefaultBannerDatas = Arrays.asList(
            new LatestNews.TopArticle(),
            new LatestNews.TopArticle(),
            new LatestNews.TopArticle()
    );

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home_banner;
    }

    @Override
    protected void initView(View view) {
        mHomeBannerAdapter = new HomeBannerAdapter(mContext);
        mIcvpBanner.setAdapter(mHomeBannerAdapter);
        upDateBanner(mDefaultBannerDatas);

        mHomeBannerAdapter.setOnTimerSchedule(() -> {
            if(mHomeBannerAdapter == null || mHomeBannerAdapter.getCount() == 0)
                return ;
            mIcvpBanner.post(() -> {    // 图片轮播, post 到 MainThread
                int nextPageOffset = mIcvpBanner.getRealItem()+1;   // getRealItem() 代替 getCurrentItem()
                Log.i(TAG, "select: " + nextPageOffset);
                mIcvpBanner.setCurrentItem(nextPageOffset);
            });
        });

        debugPageIndex();
    }

    @Override
    protected void loadData() {
    }

    /**
     * HorizontalInfiniteCycleViewPager 有个 bug
     * mHomeBannerAdapter 的数据加载必须先与 setAdapter
     * @param topStories
     */
    public void upDateBanner(List topStories){
        mHomeBannerAdapter.stop();

        mHomeBannerAdapter.setDataList(topStories);
        mIcvpBanner.setAdapter(mHomeBannerAdapter);
        mIcvpBanner.notifyDataSetChanged(); // 用这个代替 adapter 的 notify...()
        mIcvpBanner.setCurrentItem(0);

        mHomeBannerAdapter.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        mHomeBannerAdapter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHomeBannerAdapter.stop();
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
