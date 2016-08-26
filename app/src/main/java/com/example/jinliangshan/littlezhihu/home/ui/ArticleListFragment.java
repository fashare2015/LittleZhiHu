package com.example.jinliangshan.littlezhihu.home.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.base.BaseRecyclerViewAdapter;
import com.example.jinliangshan.littlezhihu.home.model.Article;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.rxjava.observable.BaseObservable;
import com.example.jinliangshan.littlezhihu.home.util.HidingAnimUtil;
import com.example.jinliangshan.littlezhihu.home.util.TransitionUtils;
import com.example.jinliangshan.littlezhihu.home.widget.SimpleOnScrollListener;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArticleListFragment extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener{

    @BindView(R.id.layout_common_toolbar)
    public LinearLayout mLayoutCommonToolbar;
    @BindView(R.id.tb_common)
    public Toolbar mTbCommon;

    @BindView(R.id.rv_article_list)
    RecyclerView mRvArticleList;
    private ArticleListAdapter mArticleAdapter;

    @BindView(R.id.fab_menu)
    FloatingActionButton mFabMenu;
    private HidingAnimUtil mTbHidingAnimUtil, mFabHidingAnimUtil;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_list;
    }

    @Override
    protected void initView(View view) {
        mRvArticleList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvArticleList.setAdapter(mArticleAdapter = new ArticleListAdapter(mContext));

        mArticleAdapter.setOnItemClickListener(this);
        mRvArticleList.addOnScrollListener(new MyOnScrollListener());

        mArticleAdapter.setDataList(new ArrayList<>(Arrays.asList(
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article(),
                new Article()
        )));

        initAnim(); // 等 view 的相对布局已定
    }

    @Override
    protected void loadData() {
//        OkHttpHelper.getInstance().get(Apis.URL_LATEST_NEWS);

//        new LatestNewsObservable(null).newInstance()
        Observable.create(
                        new BaseObservable.BaseOnSubscribe<>(new LatestNews())
                ).subscribeOn(Schedulers.io())
                
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext, 请求成功
                        data -> Toast.makeText(mContext, data.getDate(), Toast.LENGTH_SHORT).show(),
                        // onError, 请求失败
                        throwable -> Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void setRvPaddingTop(int paddingTop) {
        mRvArticleList.setPadding(0, paddingTop, 0, 0);
    }

    private void initAnim() {
        mTbHidingAnimUtil = new HidingAnimUtil(mLayoutCommonToolbar)
                .setHidingMod(HidingAnimUtil.HIDING_MOD_TOP);
        mFabHidingAnimUtil = new HidingAnimUtil(mFabMenu)
                .setHidingMod(HidingAnimUtil.HIDING_MOD_BOTTOM);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Activity activity = getActivity();
        Intent intent = new Intent(activity, ArticleActivity.class);
        Bundle options = TransitionUtils.makeActivityOptionsBundle(activity, itemView);
        ActivityCompat.startActivity(activity, intent, options);
    }

    private class MyOnScrollListener extends SimpleOnScrollListener{
        @Override
        public void onScrolledUp(int dy) {
            Log.d("ArticleListFragment", "onScrolledUp");
            mTbHidingAnimUtil.hide();
            mFabHidingAnimUtil.hide();
        }

        @Override
        public void onScrolledDown(int dy) {
            mTbHidingAnimUtil.show();
            mFabHidingAnimUtil.show();
        }
    }
}
