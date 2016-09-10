package com.example.jinliangshan.littlezhihu.home.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jinliangshan on 16/8/25.
 *
 * 带 Header 的 adapter
 * @param <H> Header 的数据类型
 * @param <T> RecyclerView 的数据类型
 */
public abstract class BaseHeaderRecyclerViewAdapter<H, T> extends BaseRecyclerViewAdapter<T>
        implements HeaderAdapter<BaseHeaderRecyclerViewAdapter.BaseHeaderViewHolder<H>>{

    protected static final int TYPE_HEADER = 1;

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

    public BaseHeaderRecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override
    public void clearReferences() {
        super.clearReferences();
        mHeaderData = null;
        mOnHeaderClickListener = null;
    }

    @Override
    public int getItemViewType(int position) {
        return position==POS_HEADER? TYPE_HEADER: super.getItemViewType(position);
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
            super.onBindViewHolder(holder, getNonHeaderPosition(position));
    }

    protected int getNonHeaderPosition(int pos){
        return pos>0? pos-1: -1;
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
    }

    public interface OnHeaderClickListener{
        void onHeaderClick(View Header);
    }
}
