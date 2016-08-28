package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import com.example.jinliangshan.littlezhihu.R;

import butterknife.BindView;

public abstract class BaseFragmentActivity extends BaseActivity implements BaseFragment.OnLoadDataListener {

    // toolBar
    @Nullable @BindView(R.id.tb_common)
    Toolbar mTbCommon;
    @Nullable @BindView(R.id.layout_common_toolbar)
    CollapsingToolbarLayout mLayoutCommonToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    protected abstract void initFragment();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_activity_base;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void loadData() {
    }
}

