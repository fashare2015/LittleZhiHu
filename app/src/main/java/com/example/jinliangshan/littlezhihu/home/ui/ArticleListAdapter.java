package com.example.jinliangshan.littlezhihu.home.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.jinliangshan.littlezhihu.home.widget.TimerViewPager;
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
            // TODO 复用时, 异步加载图片还是有错乱的 bug
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
        @BindView(R.id.vp_banner)
        TimerViewPager mVpBanner;
        private HomeBannerAdapter mHomeBannerAdapter;

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
            setBannerTimerCallback();
            mHomeBannerAdapter.setOnItemClickListener(this);
            upDateBanner(mDefaultBannerDatas);
        }

        private void setBannerTimerCallback() {
            mVpBanner.setOnTimerSchedule(() -> {    // 图片轮播,
                int nextPageOffset = (mVpBanner.getCurrentItem()+1) % mHomeBannerAdapter.getCount();
                Log.i(TAG, "select: " + nextPageOffset);
                mVpBanner.setCurrentItem(nextPageOffset);
            });
        }

        @Override
        public void onBind(List<TopArticle> data, int pos) {
            upDateBanner(data);
        }

        @Override
        public void onRecycled() {
            super.onRecycled();
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
            mVpBanner.stop();

            mHomeBannerAdapter.setDataList(topStories);
            mVpBanner.setAdapter(mHomeBannerAdapter);
            mVpBanner.setCurrentItem(0);

            mVpBanner.start();
        }
    }
}
