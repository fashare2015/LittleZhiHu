package com.example.jinliangshan.littlezhihu.home.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jinliangshan on 16/8/25.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<T>>{
    protected Context mContext;
    private List<T> mDataList;
    private OnItemClickListener mOnItemClickListener;

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    protected abstract BaseViewHolder<T> getViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        holder.onBind(getDataList().get(position), position);
        holder.itemView.setOnClickListener(view -> {
            if(mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(holder.itemView, position);
        });
    }

    @Override
    public int getItemCount() {
        return mDataList == null? 0: mDataList.size();
    }

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract public void onBind(T data, int pos);
    }

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }
}
