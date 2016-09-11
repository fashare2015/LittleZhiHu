package com.example.jinliangshan.littlezhihu.home.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements OnLifeCycle{
    protected final String TAG = this.getClass().getSimpleName();

    // toolBar
    @Nullable @BindView(R.id.tb_common)
    public Toolbar mTbCommon;

    private OnWidgetClickListener mOnWidgetClickListener;

    public void setOnWidgetClickListener(OnWidgetClickListener onWidgetClickListener) {
        mOnWidgetClickListener = onWidgetClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        ButterKnife.bind(this);
        initBundle();
        initView();
        initListener();
        loadData();
    }

    public void initBundle(){}

    @Override
    public void initView() {}

    @Override
    public void initListener() {
        if(mTbCommon != null)
            mTbCommon.setOnClickListener((view) -> {
                if(mOnWidgetClickListener != null)
                    mOnWidgetClickListener.clickToolBar();
            });
    }

    @Override
    public void loadData() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getRefWatcher().watch(this);  // 监测内存泄漏
    }

    public interface OnWidgetClickListener{
        void clickToolBar();
    }
}

