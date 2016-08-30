package com.example.stickyheaderrecyclerview.cache;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.stickyheaderrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of {@link HeaderProvider} that creates and caches header views
 */
public class HeaderViewCache implements HeaderProvider {
    private final StickyRecyclerHeadersAdapter mAdapter;
    private final Map<Integer, View> mHeaderCache = new HashMap<>();

    public HeaderViewCache(StickyRecyclerHeadersAdapter adapter) {
        mAdapter = adapter;
    }

    public View getHeader(RecyclerView parent, int position) {
        int headerId = mAdapter.getSectionForPosition(position);
        View header = mHeaderCache.get(headerId);

        if(header != null){ // recycle views
            return header;
        }

        RecyclerView.ViewHolder viewHolder = mAdapter.onCreateHeaderViewHolder(parent); // callback the onCreate...()
        mAdapter.onBindHeaderViewHolder(viewHolder, position);  // callback the onBind...()
        header = viewHolder.itemView;

        // RecyclerView 作为 parent,
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY),
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        // header 作为 RecyclerView 的 child, 获取其宽高
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);

        // 按照绘制流程: measure -> layout -> draw
        header.measure(childWidth, childHeight);
        header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());

        mHeaderCache.put(headerId, header); // cache the header
        return header;
    }

    @Override
    public void invalidate() {
        mHeaderCache.clear();
    }
}
