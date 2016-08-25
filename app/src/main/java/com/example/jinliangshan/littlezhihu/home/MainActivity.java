package com.example.jinliangshan.littlezhihu.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.ui.ArticleListFragment;

import butterknife.BindView;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.layout_common_toolbar)
    public LinearLayout mLayoutCommonToolbar;

    @BindView(R.id.tb_common)
    public Toolbar mTbCommon;

    Fragment mArticleListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initFragment() {
        mArticleListFragment = new ArticleListFragment();
    }
}

