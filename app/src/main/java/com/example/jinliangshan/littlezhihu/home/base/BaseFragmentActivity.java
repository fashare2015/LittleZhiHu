package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;

import com.example.jinliangshan.littlezhihu.R;

import rx.Observable;

public abstract class BaseFragmentActivity extends BaseActivity implements BaseFragment.OnLoadDataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    protected abstract void initFragment();

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_activity_base;
    }

    @Override
    public void loadingFromFragment(Observable<?> dataObservable) {}
}

