package com.example.jinliangshan.littlezhihu.home.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
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
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindView;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class ArticleListAdapter extends BaseHeaderRecyclerViewAdapter<List<LatestNews.TopArticle>, ArticlePreview>{

    private BitmapCache mBitmapCache = new BitmapCache();
    // 一个 adapter 持有一个 cache, 为 viewHolder 所共有

    public ArticleListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<ArticlePreview> getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
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
                    MyApplication.getInstance().getImageLoader()
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
    }

    // --- header ---
    @Override
    public BaseHeaderViewHolder<List<LatestNews.TopArticle>> onCreateHeaderViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.header_home_banner, parent, false);
        return new ArticleHeaderViewHolder(itemView);
    }

    public class ArticleHeaderViewHolder extends BaseHeaderViewHolder<List<LatestNews.TopArticle>>
            implements OnItemClickListener<LatestNews.TopArticle>{
        private static final String TAG = "ArticleHeaderViewHolder";
        @BindView(R.id.icvp_banner)
        HorizontalInfiniteCycleViewPager mIcvpBanner;
        public HomeBannerAdapter mHomeBannerAdapter;

        @BindBitmap(R.mipmap.ic_launcher)
        Bitmap mDefaultBitmap;

        private List<LatestNews.TopArticle> mDefaultBannerDatas = Arrays.asList(
                new LatestNews.TopArticle(),
                new LatestNews.TopArticle(),
                new LatestNews.TopArticle()
        );

        public ArticleHeaderViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        public void initView() {
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
            mHomeBannerAdapter.setOnItemClickListener(this);

            debugPageIndex();
        }

        @Override
        public void onBind(List<LatestNews.TopArticle> data, int pos) {
            upDateBanner(data);
        }

        @Override
        public void onItemClick(View itemView, LatestNews.TopArticle data, int position) {
            ArticleActivity.startThis((Activity) mContext, data.getId(), itemView);
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

        private void debugPageIndex() {
            mIcvpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

//                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onPageSelected(int position) {
                    Log.i(TAG, "curPage: " + position + " - " + mIcvpBanner.getCurrentItem() + " - " + mIcvpBanner.getRealItem());
//                    mIcvpBanner.setBackground(new BitmapDrawable(
//                            mHomeBannerAdapter.getBitmapCache().get(position+"")));
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        }
    }
}
