package com.example.jinliangshan.littlezhihu.home.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.MyApplication;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.widget.CommonRecyclerViewOnScrollListener;
import com.example.jinliangshan.littlezhihu.home.base.OnItemClickListener;
import com.example.jinliangshan.littlezhihu.home.model.ArticlePreview;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.rxjava.Observables;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;

public class ArticleListFragment extends BaseFragment implements OnItemClickListener<ArticlePreview> {

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
//        StickyRecyclerHeadersDecoration itemDecoration;
        mRvArticleList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvArticleList.setAdapter(mArticleAdapter = new ArticleListAdapter(mContext, mRvArticleList));
//        mRvArticleList.addItemDecoration(itemDecoration = new StickyRecyclerHeadersDecoration(mArticleAdapter));

        mArticleAdapter.setOnItemClickListener(this);
//        mArticleAdapter.setOnHeaderClickListener((header, headerId) -> Toast.makeText(mContext, "adapter: headId = " + headerId, Toast.LENGTH_SHORT).show());
        mRvArticleList.addOnScrollListener(new MyOnScrollListener());

//        StickyRecyclerHeadersTouchListener touchListener = new StickyRecyclerHeadersTouchListener(mRvArticleList, itemDecoration);
//        touchListener.setOnHeaderClickListener((header, position, headerId) -> Toast.makeText(mContext, "headId = " + headerId, Toast.LENGTH_SHORT).show());
//        mRvArticleList.addOnItemTouchListener(touchListener);
    }

    @Override
    public void loadData() {
        Observables.getLatestNewsObservable()
                .doOnNext(articles -> mArticleAdapter.setHeaderData(articles.getTop_stories()))
                .map(LatestNews:: getStories)
                .subscribe(
                        // onNext, 请求成功
                        mArticleAdapter:: setDataList,
                        // onError, 请求失败
                        throwable -> Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO bannerTimer
//        mArticleAdapter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mArticleAdapter.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mArticleAdapter.clearReferences();
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
            Log.i("ArticleListFragment", "onScrolledUp");
            if(mOnArticleListScrollListener != null)
                mOnArticleListScrollListener.onScrolledUp();
        }

        @Override
        public void onScrolledDown(int dy) {
            if(mOnArticleListScrollListener != null)
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

    public interface OnArticleListScrollListener{
        void onScrolledUp();
        void onScrolledDown();
    }
}
