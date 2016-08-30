package com.example.stickyheaderrecyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.stickyheaderrecyclerview.cache.HeaderProvider;
import com.example.stickyheaderrecyclerview.cache.HeaderViewCache;

/**
 * Created by jinliangshan on 16/8/30.
 */
public class StickyRecyclerHeadersDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "StickyHeadersDecoration";

    private StickyRecyclerHeadersAdapter mAdapter;
    private HeaderProvider mHeaderProvider;

    public StickyRecyclerHeadersDecoration(StickyRecyclerHeadersAdapter adapter) {
        mAdapter = adapter;
        init();
    }

    private void init() {
        mHeaderProvider = new HeaderViewCache(mAdapter);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        if(mAdapter != null)
            drawVertical(canvas, parent);
    }

    public void drawVertical(Canvas canvas, RecyclerView parent) {
        Log.i(TAG, "drawVertical");
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int pos = 0; pos < childCount; pos++) {
            if(!hasHeader(pos))
                continue;

            View headerView = getHeader(parent, pos);

            final View child = parent.getChildAt(pos);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int bottom = child.getTop() - params.topMargin;
            final int top = bottom - headerView.getHeight();

            drawHeader(canvas, parent, pos, new Rect(left, top, right, bottom));    // 给定一个 headOffset
        }
    }

    public void drawHeader(Canvas canvas, RecyclerView parent, int position, Rect headOffset) {
        canvas.save();
        canvas.translate(headOffset.left, headOffset.top);
        getHeader(parent, position).draw(canvas);
        canvas.restore();
    }

    public View getHeader(RecyclerView parent, int position) {
        return mHeaderProvider.getHeader(parent, position);
    }

    private boolean hasHeader(int pos){
        return isFirstOfGroup(pos);
    }

    /**
     * 按照 headerId 分组, 相同的分为一组
     * @param pos
     * @return
     */
    private boolean isFirstOfGroup(int pos){
        int curId = mAdapter.getSectionForPosition(pos);
        int preId = mAdapter.getSectionForPosition(pos-1);
        return pos == 0 || curId != preId;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        if(!hasHeader(pos))
            return ;

        View header = getHeader(parent, pos);
        outRect.set(0, header.getMeasuredHeight(), 0, 0);
    }
}
