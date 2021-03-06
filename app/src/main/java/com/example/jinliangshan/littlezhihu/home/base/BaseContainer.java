package com.example.jinliangshan.littlezhihu.home.base;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-09-06
 * Time: 19:27
 * <br/><br/>
 * a container to hold a ViewGroup
 */
public abstract class BaseContainer<T> extends CoordinatorLayout implements OnLifeCycle{
    protected final String TAG = this.getClass().getSimpleName();

    protected Context mContext;
    protected View mItemView;
    protected T data;

    public BaseContainer(Context context) {
        super(context);
        init();
        initView();
    }

    public BaseContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initView();
    }

    public BaseContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initView();
    }

    private void init() {
        mContext = getContext();
        mItemView = LayoutInflater.from(mContext)
                .inflate(getLayoutRes(), this);
        ButterKnife.bind(this);
    }

    @Override
    public void initBundle() {}

    @Override
    public void loadData() {}
}