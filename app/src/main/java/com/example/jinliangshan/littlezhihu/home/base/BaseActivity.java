package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jinliangshan.littlezhihu.home.MyApplication;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements OnLifeCycle{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        ButterKnife.bind(this);
        initBundle();
        initView();
        loadData();
    }

    public void initBundle(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getRefWatcher().watch(this);  // 监测内存泄漏
    }
}

