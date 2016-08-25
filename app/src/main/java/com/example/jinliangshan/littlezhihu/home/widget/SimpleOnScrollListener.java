package com.example.jinliangshan.littlezhihu.home.widget;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jinliangshan on 16/8/25.
 */
public abstract class SimpleOnScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
            onScrolledUp(dy);
        } else if (dy < 0) {
            onScrolledDown(dy);
        }
    }

    abstract public void onScrolledUp(int dy);

    abstract public void onScrolledDown(int dy);
}
