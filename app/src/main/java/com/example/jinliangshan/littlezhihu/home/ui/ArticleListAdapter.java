package com.example.jinliangshan.littlezhihu.home.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseRecyclerViewAdapter;
import com.example.jinliangshan.littlezhihu.home.model.Article;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.BindView;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class ArticleListAdapter extends BaseRecyclerViewAdapter<Article> {

    public ArticleListAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<Article> getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    public class ArticleViewHolder extends BaseViewHolder<Article> {
        @BindView(R.id.cv_article)
        CardView mCvArticle;

        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        public ArticleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Article data, int pos) {
            mTvTitle.setText(data.getTitle());
            if (data.getImages().size() > 0) {
//                MyApplication.getInstance().getImageLoader().displayImage(data.getImages().get(0), mIvImage);
                ImageLoader mImageLoader;
                mImageLoader = ImageLoader.getInstance();
                mImageLoader.init(new ImageLoaderConfiguration.Builder(mContext).build());
                mImageLoader.displayImage(data.getImages().get(0), mIvImage);
            }
        }
    }
}
