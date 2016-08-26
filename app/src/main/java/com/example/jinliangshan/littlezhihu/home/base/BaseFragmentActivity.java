package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;

import com.example.jinliangshan.littlezhihu.R;

public abstract class BaseFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_activity_base;
    }

    protected abstract void initFragment();

}

