package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;

public abstract class BaseFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    protected abstract void initFragment();

}

