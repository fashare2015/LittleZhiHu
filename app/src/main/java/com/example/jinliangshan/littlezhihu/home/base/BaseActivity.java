package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        ButterKnife.bind(this);
        initBundle();
        initView();
        loadData();
    }

    protected abstract @LayoutRes int getLayoutRes();

    protected void initBundle(){}

    protected abstract void initView();

    protected abstract void loadData();

}

