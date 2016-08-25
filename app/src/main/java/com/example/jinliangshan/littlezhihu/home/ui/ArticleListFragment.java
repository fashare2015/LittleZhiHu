package com.example.jinliangshan.littlezhihu.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.model.Article;
import com.example.jinliangshan.littlezhihu.home.util.HidingAnimUtil;
import com.example.jinliangshan.littlezhihu.home.widget.SimpleOnScrollListener;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

public class ArticleListFragment extends BaseFragment{

    @BindView(R.id.layout_common_toolbar)
    public LinearLayout mLayoutCommonToolbar;
    @BindView(R.id.tb_common)
    public Toolbar mTbCommon;

    @BindView(R.id.rv_article_list)
    RecyclerView mRvArticleList;
    private com.example.jinliangshan.littlezhihu.home.ui.ArticleListAdapter mArticleAdapter;

    @BindView(R.id.fab_menu)
    FloatingActionButton mFabMenu;
    private HidingAnimUtil mTbHidingAnimUtil, mFabHidingAnimUtil;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_list;
    }

    @Override
    protected void initView(View view) {
        mRvArticleList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvArticleList.setAdapter(mArticleAdapter = new com.example.jinliangshan.littlezhihu.home.ui.ArticleListAdapter(mContext));

        mRvArticleList.addOnScrollListener(new SimpleOnScrollListener(){
            @Override
            public void onScrolledUp(int dy) {
                Log.d("ArticleListFragment", "onScrolledUp");
                mTbHidingAnimUtil.hide();
                mFabHidingAnimUtil.hide();
            }

            @Override
            public void onScrolledDown(int dy) {
                mTbHidingAnimUtil.show();
                mFabHidingAnimUtil.show();
            }
        });

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

    private void setRvPaddingTop(int paddingTop) {
        mRvArticleList.setPadding(0, paddingTop, 0, 0);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAnim(); // 等 view 的相对布局已定
    }

    private void initAnim() {
        mTbHidingAnimUtil = new HidingAnimUtil(mLayoutCommonToolbar)
                .setHidingMod(HidingAnimUtil.HIDING_MOD_TOP);
        mFabHidingAnimUtil = new HidingAnimUtil(mFabMenu)
                .setHidingMod(HidingAnimUtil.HIDING_MOD_BOTTOM);
    }
}
