package com.example.jinliangshan.littlezhihu.home.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jinliangshan on 16/8/25.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<T>>
        implements ReferencesManager{
    protected final String TAG = this.getClass().getSimpleName();
    protected static final int TYPE_NORMAL = 0;

//    protected Context mContext;
    private WeakReference<Context> mContextWeakReference;   // 弱引用
    protected RecyclerView mRecyclerView;
    protected List<T> mDataList;
    private OnItemClickListener<T> mOnItemClickListener;

    protected Context getContext(){
//        return mContext;
        return mContextWeakReference.get();
    }

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

    public BaseRecyclerViewAdapter(Context context, RecyclerView recyclerView) {
//        mContext = context;
        mContextWeakReference = new WeakReference<>(context);
        mRecyclerView = recyclerView;
        mDataList = new ArrayList<>();
    }

    @Override
    public void clearReferences() {
        Log.i(TAG, "clearReferences");
        clearAllViewHolder();
        mContextWeakReference = null;
        mRecyclerView = null;
        mDataList = null;
        mOnItemClickListener = null;
    }

    protected void clearAllViewHolder(){
        for(int i=0; i<getItemCount(); i++)
            if(getViewHolder(i) != null)
                getViewHolder(i).clearReferences();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_NORMAL;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return newViewHolder(parent, viewType);
    }

    protected abstract BaseViewHolder<T> newViewHolder(ViewGroup parent, int viewType);

    /**
     * ViewHolder 是复用的, 有些 position 可能返回 null
     * @param position
     * @return
     */
    protected BaseViewHolder<T> getViewHolder(int position){
        return (BaseViewHolder<T>) mRecyclerView.findViewHolderForAdapterPosition(position);
    }

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
    public void onViewDetachedFromWindow(BaseViewHolder<T> holder) {
        super.onViewDetachedFromWindow(holder);
//        Log.d(TAG, "onViewDetachedFromWindow " + holder.getAdapterPosition() + ": " + holder);
    }

    @Override
    public int getItemCount() {
        return mDataList == null? 0: mDataList.size();
    }

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder implements ReferencesManager{
        protected final String TAG = this.getClass().getSimpleName();

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void onBind(T data, int pos);

        public void onRecycled(){
            Log.d(TAG + getAdapterPosition(), "onRecycled");
        }

        @Override
        public void clearReferences() {
            Log.d(TAG + this, "clearReferences");
        }
    }

}
