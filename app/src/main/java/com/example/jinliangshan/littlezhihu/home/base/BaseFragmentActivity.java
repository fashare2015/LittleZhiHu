package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;

import com.example.jinliangshan.littlezhihu.R;

public abstract class BaseFragmentActivity extends BaseActivity implements BaseFragment.OnLoadDataListener{

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
    protected void initView() {}

    @Override
    protected void loadData() {}
}

