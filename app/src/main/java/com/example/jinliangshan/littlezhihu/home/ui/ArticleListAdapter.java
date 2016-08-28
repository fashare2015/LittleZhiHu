package com.example.jinliangshan.littlezhihu.home.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.MyApplication;
import com.example.jinliangshan.littlezhihu.home.base.BaseRecyclerViewAdapter;
import com.example.jinliangshan.littlezhihu.home.cache.BitmapCache;
import com.example.jinliangshan.littlezhihu.home.model.Article;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindBitmap;
import butterknife.BindView;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class ArticleListAdapter extends BaseRecyclerViewAdapter<Article> {
    private BitmapCache mBitmapCache = new BitmapCache();
    // 一个 adapter 持有一个 cache, 为 viewHolder 所共有

    public ArticleListAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<Article> getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    public class ArticleViewHolder extends BaseViewHolder<Article> {
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
        public void onBind(Article data, int pos) {
            mTvTitle.setText(data.getTitle());
            mIvImage.setImageBitmap(mDefaultBitmap);    // 先显示默认图片, 下面再次异步加载图片
            if (data.getImages().size() > 0) {
                String url = data.getImages().get(0);

                if(mBitmapCache.get(pos + "") != null) {  // 先从缓存加载
                    Log.i(TAG, "get bitmap " + pos + " from cache");
                    bitmap = mBitmapCache.get(pos + "");
                    mIvImage.setImageBitmap(bitmap);
                }else{
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
}
