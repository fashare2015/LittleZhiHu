package com.example.jinliangshan.littlezhihu.home.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.MyApplication;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.base.OnItemClickListener;
import com.example.jinliangshan.littlezhihu.home.model.ArticlePreview;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.rxjava.Observables;
import com.example.jinliangshan.littlezhihu.home.widget.CommonRecyclerViewOnScrollListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindDimen;
import butterknife.BindView;

public class ArticleListFragment extends BaseFragment implements OnItemClickListener<ArticlePreview> {
    // SwipeRefreshLayout 刷新进度的起始, 终止位置
    @BindDimen(R.dimen.srl_refresh_start)
    int mDimenSrlRefreshStart;
    @BindDimen(R.dimen.srl_refresh_end)
    int mDimenSrlRefreshEnd;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    @BindView(R.id.rv_article_list)
    RecyclerView mRvArticleList;
    private ArticleListAdapter mArticleAdapter;

    private OnArticleListScrollListener mOnArticleListScrollListener;

    public void setOnArticleListScrollListener(OnArticleListScrollListener onArticleListScrollListener) {
        mOnArticleListScrollListener = onArticleListScrollListener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_article_list;
    }

    @Override
    public void initView() {
        mSrlRefresh.setProgressViewOffset(false, mDimenSrlRefreshStart, mDimenSrlRefreshEnd);

        mRvArticleList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvArticleList.setAdapter(mArticleAdapter = new ArticleListAdapter(mContext, mRvArticleList));
//        StickyRecyclerHeadersDecoration itemDecoration;
//        mRvArticleList.addItemDecoration(itemDecoration = new StickyRecyclerHeadersDecoration(mArticleAdapter));
    }

    @Override
    public void initListener() {
        super.initListener();
        mSrlRefresh.setOnRefreshListener(this:: loadData);
        mArticleAdapter.setOnItemClickListener(this);
        mRvArticleList.addOnScrollListener(new MyOnScrollListener());
//        StickyRecyclerHeadersTouchListener touchListener = new StickyRecyclerHeadersTouchListener(mRvArticleList, itemDecoration);
//        touchListener.setOnHeaderClickListener((header, position, headerId) -> Toast.makeText(mContext, "headId = " + headerId, Toast.LENGTH_SHORT).show());
//        mRvArticleList.addOnItemTouchListener(touchListener);
    }

    @Override
    public void loadData() {
//        mSrlRefresh.setRefreshing(true);
        Observables.getLatestNewsObservable()
                .doOnNext(articles -> mArticleAdapter.setHeaderData(articles.getTop_stories()))
                .map(LatestNews::getStories)
                .subscribe(
                        // onNext, 请求成功
                        mArticleAdapter::setDataList,
                        // onError, 请求失败
                        throwable -> Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show(),
                        // onComplete, 结束刷新
                        () -> mSrlRefresh.setRefreshing(false)
                );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mArticleAdapter.clearReferences();
        super.onDestroy();
    }

    @Override
    public void clickToolBar() {
        super.clickToolBar();
        mRvArticleList.smoothScrollToPosition(0);
    }

    @Override
    public void onItemClick(View itemView, ArticlePreview data, int position) {
        ArticleActivity.startThis(getActivity(), data.getId(), itemView);
    }

    /**
     * <p>
     * RecyclerView 滑动回调<br/>
     * 隐藏 toolBar 和 优化图片加载
     * </p>
     */
    private class MyOnScrollListener extends CommonRecyclerViewOnScrollListener {
        // 隐藏 toolBar
        @Override
        public void onScrolledUp(int dy) {
//            Log.i("ArticleListFragment", "onScrolledUp");
            if (mOnArticleListScrollListener != null)
                mOnArticleListScrollListener.onScrolledUp();
        }

        @Override
        public void onScrolledDown(int dy) {
            if (mOnArticleListScrollListener != null)
                mOnArticleListScrollListener.onScrolledDown();
        }

        // 优化图片加载
        private ImageLoader mImageLoader = MyApplication.getImageLoader();

        @Override
        protected void onDragging() {
            mImageLoader.pause();
        }

        @Override
        protected void onIdle() {
            mImageLoader.resume();
        }
    }

    public interface OnArticleListScrollListener {
        void onScrolledUp();
        void onScrolledDown();
    }
}
