package com.example.jinliangshan.littlezhihu.home.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinliangshan.littlezhihu.home.MyApplication;

import butterknife.ButterKnife;
import rx.Observable;

public abstract class BaseFragment extends Fragment implements OnLifeCycle{
    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    protected OnLoadDataListener mOnLoadDataListener;

    public BaseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        loadData();
    }

    @Override
    public void initBundle() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnLoadDataListener) {
            mOnLoadDataListener = (OnLoadDataListener) context;
        } else {
            Log.i(TAG, context.toString()
                    + " must implement OnLoadDataListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoadDataListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.getRefWatcher().watch(this);  // 监测内存泄漏
    }

    protected void dispatch(Observable<?> dataObservable){
        if(mOnLoadDataListener != null)
            mOnLoadDataListener.loadingFromFragment(dataObservable);
    }

    public interface OnLoadDataListener{
        void loadingFromFragment(Observable<?> dataObservable);
    }
}
