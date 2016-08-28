package com.example.jinliangshan.littlezhihu.home.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-27
 * Time: 17:04
 * <br/><br/>
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {
    private static final int MAX_PAGE_SIZE = 10;
    protected Context mContext;
    private List<T> mDataList;
    private List<View> mViewList;
    private OnItemClickListener mOnItemClickListener;

    protected View mItemView;

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

    public BasePagerAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
        initViewList(MAX_PAGE_SIZE);
    }

    private void initViewList(int size) {
        if(mViewList == null)
            mViewList = new ArrayList<>();
        mViewList.clear();
        for(int i=0; i<size; i++)
            mViewList.add(LayoutInflater.from(mContext).inflate(getLayoutRes(), null));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO mItemView 复用优化
        mItemView = mViewList.get(position);
        ButterKnife.bind(this, mItemView);
        if(mItemView.getParent() == null)
            container.addView(mItemView);

        onBind(mDataList.get(position), position);
        if(mOnItemClickListener != null)
            mItemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(mItemView, position));

        return mItemView;   // 返回 view 作为 key
    }

    protected abstract @LayoutRes int getLayoutRes();

    protected abstract void onBind(T t, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO ???
//        container.removeViewAt(position);
//        mViewList.remove(position);
    }

    @Override
    public int getCount() {
        return mDataList==null? 0: mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object key) {
        return key == view;
    }

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }
}  