package com.example.jinliangshan.littlezhihu.home.ui;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;

import butterknife.BindBitmap;
import butterknife.BindView;
import rx.Observable;

public class ArticleActivity extends BaseFragmentActivity {

    private Fragment mArticleFragment;

    @BindView(R.id.icvp_banner)
    ViewPager mIcvpBanner;

    @BindBitmap(R.mipmap.ic_launcher)
    Bitmap mDefaultBitmap;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_activity_article;
    }

    @Override
    protected void initFragment() {
        mArticleFragment = new ArticleFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleFragment);
    }

    @Override
    protected void initView() {
//        mIcvpBanner.setAdapter(new PagerAdapter() {
//            Bitmap[] mBitmaps= new Bitmap[3];
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                // 返回 view 作为 key
////                return LayoutInflater.from(ArticleActivity.this)
////                        .inflate(R.layout.item_banner, container, true);
//                // 设置 attachToRoot 为 true, 免去手动 add
//                View itemView = getLayoutInflater().inflate(R.layout.item_banner, null);
//                container.addView(itemView);
//                return itemView;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeViewAt(position);
//            }
//
//            @Override
//            public int getCount() {
//                return 3;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object key) {
//                return key == view;
//            }
//        });
    }

    @Override
    public void loadingFromFragment(Observable<?> dataObservable) {

    }
}
