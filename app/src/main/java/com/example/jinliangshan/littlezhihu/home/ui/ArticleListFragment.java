package com.example.jinliangshan.littlezhihu.home.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.MyApplication;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.base.BaseOnScrollListener;
import com.example.jinliangshan.littlezhihu.home.base.BaseRecyclerViewAdapter;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.rxjava.observable.Observables;
import com.example.jinliangshan.littlezhihu.home.util.HidingAnimUtil;
import com.example.jinliangshan.littlezhihu.home.util.TransitionUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import rx.Observable;

public class ArticleListFragment extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener{

//    @BindView(R.id.layout_common_toolbar)
//    public CollapsingToolbarLayout mLayoutCommonToolbar;
//    @BindView(R.id.tb_common)
//    public Toolbar mTbCommon;

    @BindView(R.id.rv_article_list)
    RecyclerView mRvArticleList;
    private ArticleListAdapter mArticleAdapter;

    @BindView(R.id.fab_menu)
    FloatingActionButton mFabMenu;
//    private HidingAnimUtil mTbHidingAnimUtil;
    private HidingAnimUtil mFabHidingAnimUtil;

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

//        mArticleAdapter.setDataList(new ArrayList<>(Arrays.asList(
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article(),
//                new Article()
//        )));

        initAnim(); // 等 view 的相对布局已定
    }

    @Override
    protected void loadData() {
        final Observable<LatestNews> observable = Observables.getLatestNewsObservable();
                observable
                .map(LatestNews:: getStories)
                .doOnNext(articles -> dispatch(observable)) // 下发 observable 给 activity
                .subscribe(
                        // onNext, 请求成功
//                        articles -> mArticleAdapter.setDataList(articles),
                        mArticleAdapter:: setDataList,
                        // onError, 请求失败
                        throwable -> Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private void setRvPaddingTop(int paddingTop) {
        mRvArticleList.setPadding(0, paddingTop, 0, 0);
    }

    private void initAnim() {
//        mTbHidingAnimUtil = new HidingAnimUtil(mLayoutCommonToolbar)
//                .setHidingMod(HidingAnimUtil.HIDING_MOD_TOP);
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

    /**
     * <p>
     * RecyclerView 滑动回调<br/>
     * 隐藏 toolBar 和 优化图片加载
     * </p>
     */
    private class MyOnScrollListener extends BaseOnScrollListener {
        // 隐藏 toolBar
        @Override
        public void onScrolledUp(int dy) {
            Log.d("ArticleListFragment", "onScrolledUp");
//            mTbHidingAnimUtil.hide();
            mFabHidingAnimUtil.hide();
        }

        @Override
        public void onScrolledDown(int dy) {
//            mTbHidingAnimUtil.show();
            mFabHidingAnimUtil.show();
        }

        // 优化图片加载
        private ImageLoader mImageLoader = MyApplication.getInstance().getImageLoader();

        @Override
        protected void onDragging() {
            mImageLoader.pause();
        }

        @Override
        protected void onIdle() {
            mImageLoader.resume();
        }
    }

}
