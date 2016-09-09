package com.example.jinliangshan.littlezhihu.home.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.HomeBannerAdapter;
import com.example.jinliangshan.littlezhihu.home.MyApplication;
import com.example.jinliangshan.littlezhihu.home.base.BaseHeaderRecyclerViewAdapter;
import com.example.jinliangshan.littlezhihu.home.base.OnItemClickListener;
import com.example.jinliangshan.littlezhihu.home.cache.BitmapCache;
import com.example.jinliangshan.littlezhihu.home.model.ArticlePreview;
import com.example.jinliangshan.littlezhihu.home.model.TopArticle;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindView;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class ArticleListAdapter extends BaseHeaderRecyclerViewAdapter<List<TopArticle>, ArticlePreview>{

    private BitmapCache mBitmapCache = new BitmapCache();
    // 一个 adapter 持有一个 cache, 为 viewHolder 所共有

    public ArticleListAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override
    public void clearReferences() {
        super.clearReferences();
        mBitmapCache.clear();
        mBitmapCache = null;
    }

    @Override
    public BaseViewHolder<ArticlePreview> newViewHolder(ViewGroup parent, int viewType) {
        if(getContext() == null)
            return null;
        View itemView = LayoutInflater.from(getContext())
                .inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    public class ArticleViewHolder extends BaseViewHolder<ArticlePreview> {
        private static final String TAG = "ArticleViewHolder";
        @BindView(R.id.cv_article)
        CardView mCvArticle;

        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        @BindBitmap(R.drawable.img_default)
        Bitmap mDefaultBitmap;

        Bitmap bitmap;

        public ArticleViewHolder(View itemView) {
            super(itemView);
//            initView();
        }

        private void initView() {
            mIvImage.setImageBitmap(mDefaultBitmap);
        }

        @Override
        public void onBind(ArticlePreview data, int pos) {
            mTvTitle.setText(data.getTitle());
            mIvImage.setImageBitmap(mDefaultBitmap);    // 先显示默认图片, 下面再次异步加载图片
            if (data.getImages().size() > 0) {
                String url = data.getImages().get(0);

                if (mBitmapCache.get(pos + "") != null) {  // 先从缓存加载
                    Log.i(TAG, "get bitmap " + pos + " from cache");
                    bitmap = mBitmapCache.get(pos + "");
                    mIvImage.setImageBitmap(bitmap);
                } else {
                    Log.i(TAG, "get bitmap " + pos + " from network");
                    MyApplication.getImageLoader()
                            .loadImage(url, new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    bitmap = loadedImage;
                                    mBitmapCache.put(pos + "", bitmap); // 缓存图片
                                    mIvImage.setImageBitmap(bitmap);
                                }
                            });
                }
            }
        }

        @Override
        public void onRecycled() {
            mIvImage.setImageDrawable(null);    // 复用时清空图片
        }

        @Override
        public void clearReferences() {
            super.clearReferences();
            mCvArticle = null;
            mIvImage = null;
            mTvTitle = null;
            mDefaultBitmap = null;
            bitmap = null;
        }
    }

    // --- header ---
    @Override
    public BaseHeaderViewHolder<List<TopArticle>> onCreateHeaderViewHolder(ViewGroup parent) {
        if(getContext() == null)
            return null;
        View itemView = LayoutInflater.from(getContext())
                .inflate(R.layout.header_home_banner, parent, false);
        return new ArticleHeaderViewHolder(itemView);
    }

    public class ArticleHeaderViewHolder extends BaseHeaderViewHolder<List<TopArticle>>
            implements OnItemClickListener<TopArticle>{
        private static final String TAG = "ArticleHeaderViewHolder";
        @BindView(R.id.vp_banner)
        ViewPager mVpBanner;
        public HomeBannerAdapter mHomeBannerAdapter;

        @BindBitmap(R.mipmap.ic_launcher)
        Bitmap mDefaultBitmap;

        private List<TopArticle> mDefaultBannerDatas = Arrays.asList(
                new TopArticle(),
                new TopArticle(),
                new TopArticle()
        );

        public ArticleHeaderViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        public void initView() {
            if(getContext() == null)
                return ;
            mHomeBannerAdapter = new HomeBannerAdapter(getContext());
            mVpBanner.setAdapter(mHomeBannerAdapter);
            mVpBanner.setOnTouchListener((view, event) -> { // 重写 onTouch: 触摸时暂停轮播
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // ACTION_DOWN 被 child 消费了, 不重写 onInterceptTouchEvent 的话
                        // 是获取不到的.
                    case MotionEvent.ACTION_MOVE:
                        Log.i(TAG, "MOVE");
                        mHomeBannerAdapter.setTouching(true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        Log.i(TAG, "UP");
                        mHomeBannerAdapter.setTouching(false);
                        break;
                }
                return false;
            });

            mHomeBannerAdapter.setOnTimerSchedule(() -> {
                if(mHomeBannerAdapter == null || mHomeBannerAdapter.getCount() == 0)
                    return ;
                mVpBanner.post(() -> {    // 图片轮播, post 到 MainThread
                    int nextPageOffset = (mVpBanner.getCurrentItem()+1) % mHomeBannerAdapter.getCount();
                    Log.i(TAG, "select: " + nextPageOffset);
                    mVpBanner.setCurrentItem(nextPageOffset);
                });
            });
            mHomeBannerAdapter.setOnItemClickListener(this);

            upDateBanner(mDefaultBannerDatas);
        }

        @Override
        public void onBind(List<TopArticle> data, int pos) {
            upDateBanner(data);
        }

        @Override
        public void onRecycled() {
            super.onRecycled();
            mHomeBannerAdapter.stop();  // header 回收时, 停止 banner 轮播
        }

        @Override
        public void clearReferences() {
            super.clearReferences();
            mVpBanner = null;
            mHomeBannerAdapter.clearReferences();
            mHomeBannerAdapter = null;

            mDefaultBitmap = null;
            mDefaultBannerDatas = null;
        }

        @Override
        public void onItemClick(View itemView, TopArticle data, int position) {
            if(getContext() == null)
                return ;
            ArticleActivity.startThis((Activity) getContext(), data.getId(), itemView);
        }

        /**
         * HorizontalInfiniteCycleViewPager 有个 bug
         * mHomeBannerAdapter 的数据加载必须先与 setAdapter
         * @param topStories
         */
        public void upDateBanner(List<TopArticle> topStories){
            mHomeBannerAdapter.stop();

            mHomeBannerAdapter.setDataList(topStories);
            mVpBanner.setAdapter(mHomeBannerAdapter);
            mVpBanner.setCurrentItem(0);

            mHomeBannerAdapter.start();
        }
    }
}
