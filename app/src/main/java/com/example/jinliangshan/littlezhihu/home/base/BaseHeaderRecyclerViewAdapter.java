package com.example.jinliangshan.littlezhihu.home.base;

import android.content.Context;
import android.view.View;
import android.util.Log;

import com.example.stickyheaderrecyclerview.StickyRecyclerHeadersAdapter;

/**
 * Created by jinliangshan on 16/8/25.
 */
public abstract class BaseHeaderRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T>
        implements StickyRecyclerHeadersAdapter<BaseHeaderRecyclerViewAdapter.BaseHeaderViewHolder<T>>{

    private static final String TAG = "BaseHeaderAdapter";
    private OnHeaderClickListener mOnHeaderClickListener;

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        mOnHeaderClickListener = onHeaderClickListener;
    }

    public BaseHeaderRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindHeaderViewHolder(BaseHeaderViewHolder<T> holder, int position) {
        Log.i(TAG, "onBindHeaderViewHolder");
        holder.onBind(getDataList().get(position), position);
        holder.itemView.setOnClickListener(v -> {
            Log.i(TAG, "onHeaderClick");
            if(mOnHeaderClickListener != null)
                mOnHeaderClickListener.onHeaderClick(holder.itemView, getSectionForPosition(position));
        });
    }

    @Override
    public Object[] getSections() {
        return new Object[0];   // 未使用
    }

    public abstract static class BaseHeaderViewHolder<T> extends BaseRecyclerViewAdapter.BaseViewHolder<T> {
        public BaseHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnHeaderClickListener{
        void onHeaderClick(View Header, int headId);
    }
}
