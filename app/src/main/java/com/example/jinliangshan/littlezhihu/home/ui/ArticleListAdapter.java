package com.example.jinliangshan.littlezhihu.home.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseRecyclerViewAdapter;
import com.example.jinliangshan.littlezhihu.home.model.Article;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class ArticleListAdapter extends BaseRecyclerViewAdapter<Article> {
    public ArticleListAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder<Article> getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
        return new MyViewHolder(itemView);
    }

    private class MyViewHolder extends BaseRecyclerViewAdapter.ViewHolder<Article>{
        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Article data, int pos) {

        }
    }
}
