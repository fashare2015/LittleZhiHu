package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.example.jinliangshan.littlezhihu.R;

import butterknife.BindView;

public abstract class BaseFragmentActivity extends BaseActivity implements BaseFragment.OnLoadDataListener {
    // toolBar
    @Nullable @BindView(R.id.tb_common)
    public Toolbar mTbCommon;

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
    public void initView() {
    }

    @Override
    public void loadData() {
    }
}

