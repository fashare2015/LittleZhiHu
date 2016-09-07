package com.example.jinliangshan.littlezhihu.home.widget;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class CommonRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {   // dy > 0 -> scroll distance increase -> Scroll Up
            onScrolledUp(dy);
        } else if (dy < 0) {
            onScrolledDown(dy);
        }
    }

    protected void onScrolledUp(int dy){}

    protected void onScrolledDown(int dy){}

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState){
            case RecyclerView.SCROLL_STATE_IDLE:
                onIdle();
                break;

            case RecyclerView.SCROLL_STATE_DRAGGING:
                onDragging();
                break;

            case RecyclerView.SCROLL_STATE_SETTLING:
                onSettling();
                break;
        }
    }

    protected void onIdle() {}

    protected void onDragging() {}

    protected void onSettling() {}
}
