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
    private static final String TAG = "BaseRecyclerViewAdapter";
    protected Context mContext;
    protected List<T> mDataList;
    private OnItemClickListener<T> mOnItemClickListener;

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
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
                mOnItemClickListener.onItemClick(holder.itemView, getDataList().get(position), position);
        });
    }

    @Override
    public void onViewRecycled(BaseViewHolder<T> holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
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

        public abstract void onBind(T data, int pos);

        public abstract void onRecycled();
    }

}
