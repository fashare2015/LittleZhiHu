package com.example.jinliangshan.littlezhihu.home.widget;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseAppBarContainer;

import butterknife.BindView;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-09-06
 * Time: 19:23
 * <br/><br/>
 */
public class CommonToolBar<T> extends BaseAppBarContainer<T> {

    @BindView(R.id.banner_container)
    LinearLayout mBannerContainer;
    @BindView(R.id.tb_common)
    Toolbar mTbCommon;
    @BindView(R.id.layout_common_toolbar)
    CollapsingToolbarLayout mLayoutCommonToolbar;

    public CommonToolBar(Context context) {
        super(context);
    }

    public CommonToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.toolbar_common;
    }

    @Override
    public void initView() {

    }
}