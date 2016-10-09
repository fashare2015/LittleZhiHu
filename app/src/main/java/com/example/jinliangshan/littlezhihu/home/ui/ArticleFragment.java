package com.example.jinliangshan.littlezhihu.home.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.model.ArticleDetail;
import com.example.jinliangshan.littlezhihu.home.rxjava.Observables;
import com.example.jinliangshan.littlezhihu.home.util.WebViewUtil;

import butterknife.BindView;
import rx.Observable;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class ArticleFragment extends BaseFragment {

    @BindView(R.id.nsv_article_layout)
    NestedScrollView mNsvArticleLayout;

    @BindView(R.id.wv_article)
    WebView mWvArticle;

    @BindView(R.id.fab_lookup_comment)
    FloatingActionButton mFabLookUpComment;

    private int mArticleId;
    private WebSettings mWebSettings;

    public static Fragment getInstance(int articleId) {
        Bundle params = new Bundle();
        params.putInt(ArticleActivity.ARTICLE_ID, articleId);
        Fragment fragment = new ArticleFragment();
        fragment.setArguments(params);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticleId = getArguments().getInt(ArticleActivity.ARTICLE_ID, 0);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_article;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void initView() {
        // TODO: 设置滚动条
        mWvArticle.setVerticalScrollBarEnabled(true);
//        mWvArticle.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mWvArticle.setOnLongClickListener(view -> true);    // 屏蔽长按事件

        mWebSettings = mWvArticle.getSettings();
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void initListener() {
        super.initListener();
        mFabLookUpComment.setOnClickListener(v ->
                CommentActivity.Companion.startThis(mActivity, ((ArticleActivity)mActivity).mArticleId));
    }

    @Override
    public void loadData() {
        final Observable<ArticleDetail> observable = Observables.getArticleObservable(mArticleId);
        observable.doOnNext(article -> dispatch(observable)) // 下发 observable 给 ArticleActivity
                .subscribe(
                        // onNext, 请求成功
                        article -> WebViewUtil.loadDataWithCss(mWvArticle, article.getBody()),
                        // onError, 请求失败
                        throwable -> Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    /**
     * 点击 ToolBar, 滑动到顶部. <br/>
     * 直接 scrollTo() 不起作用: <br/>
     * <a href = 'http://stackoverflow.com/questions/12884572/scrollview-scrollto-doesnt-work'>解决方案</a>
     * // TODO: 滑动依然无效
     */
    @Override
    public void clickToolBar() {
        super.clickToolBar();
//        mNsvArticleLayout.post(() -> mNsvArticleLayout.smoothScrollTo(100, 1000));

        mNsvArticleLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(() -> mNsvArticleLayout.scrollTo(0, 0));
    }
}
