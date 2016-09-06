package com.example.jinliangshan.littlezhihu.home.base;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jinliangshan on 16/8/25.
 */
public abstract class BaseHeaderRecyclerViewAdapter<H, T> extends BaseRecyclerViewAdapter<T>
        implements HeaderAdapter<BaseHeaderRecyclerViewAdapter.BaseHeaderViewHolder<H>>{

    private static final String TAG = "BaseHeaderAdapter";
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public static final int POS_HEADER = 0;

    private H mHeaderData;
    private OnHeaderClickListener mOnHeaderClickListener;

    public H getHeaderData() {
        return mHeaderData;
    }

    public void setHeaderData(H headerData) {
        mHeaderData = headerData;
        notifyDataSetChanged();
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        mOnHeaderClickListener = onHeaderClickListener;
    }

    public BaseHeaderRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return position==POS_HEADER? TYPE_HEADER: TYPE_NORMAL;
    }

    @Override
    final public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == TYPE_HEADER?
                this.onCreateHeaderViewHolder(parent):
                super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER){   // pos==0
            if(holder instanceof BaseHeaderViewHolder)
                onBindHeaderViewHolder((BaseHeaderViewHolder<H>)holder);
        }else   // pos>0 -> pos-1
            super.onBindViewHolder(holder, position-1);
    }

    @Override
    public void onBindHeaderViewHolder(BaseHeaderViewHolder<H> holder) {
        Log.i(TAG, "onBindHeaderViewHolder");
        if(mHeaderData != null)
            holder.onBind(mHeaderData, POS_HEADER);
        holder.itemView.setOnClickListener(v -> {
            Log.i(TAG, "onHeaderClick");
            if(mOnHeaderClickListener != null)
                mOnHeaderClickListener.onHeaderClick(holder.itemView);
        });
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;    // 重写它, 多一个 header
    }

    public abstract static class BaseHeaderViewHolder<H> extends BaseRecyclerViewAdapter.BaseViewHolder<H> {
        public BaseHeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onRecycled() {}
    }

    public interface OnHeaderClickListener{
        void onHeaderClick(View Header);
    }
}
