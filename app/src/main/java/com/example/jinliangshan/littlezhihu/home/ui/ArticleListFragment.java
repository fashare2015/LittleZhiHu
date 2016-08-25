package com.example.jinliangshan.littlezhihu.home.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.model.Article;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

public class ArticleListFragment extends BaseFragment {

    @BindView(R.id.rv_article_list)
    RecyclerView mRvArticleList;
    private com.example.jinliangshan.littlezhihu.home.ui.ArticleListAdapter mArticleAdapter;

    @BindView(R.id.fab_menu)
    FloatingActionButton mFabMenu;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_list;
    }

    @Override
    protected void initView(View view) {
        mRvArticleList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvArticleList.setAdapter(mArticleAdapter = new com.example.jinliangshan.littlezhihu.home.ui.ArticleListAdapter(mContext));

        mArticleAdapter.setDataList(new ArrayList<>(Arrays.asList(
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article()
        )));
    }
}
