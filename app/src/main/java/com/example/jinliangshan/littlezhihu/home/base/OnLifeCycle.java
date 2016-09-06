package com.example.jinliangshan.littlezhihu.home.base;

import android.support.annotation.LayoutRes;

/**
 * Created by apple on 16-9-6.
 */
public interface OnLifeCycle {
    @LayoutRes int getLayoutRes();

    void initBundle();

    void initView();

    void loadData();
}
