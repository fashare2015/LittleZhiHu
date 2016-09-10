package com.example.jinliangshan.littlezhihu.home.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.model.ArticleDetail;
import com.example.jinliangshan.littlezhihu.home.rxjava.Observables;

import butterknife.BindView;
import rx.Observable;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class ArticleFragment extends BaseFragment {
    public static final String CSS_URL = "file:///android_asset/" + "style.css";
    public static final String USE_CSS_LINK = String.format("<link rel=\"stylesheet\"" +
            " type=\"text/css\" href=\"%s\" />", CSS_URL);

    @BindView(R.id.wv_article)
    WebView mWvArticle;

    private int mArticleId;
    private WebSettings mWebSettings;

    public static Fragment getInstance(int articleId){
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView() {
        mWebSettings = mWvArticle.getSettings();
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void loadData() {
        final Observable<ArticleDetail> observable = Observables.getArticleObservable(mArticleId);
                observable.doOnNext(article -> dispatch(observable)) // 下发 observable 给 activity
                .subscribe(
                        // onNext, 请求成功
                        article -> {
                            Log.i(TAG, article.getCss().get(0));
                            mWvArticle.loadDataWithBaseURL(null,
                                    USE_CSS_LINK + article.getBody(),
                                    "text/html", "UTF-8", null);
                        },
                        // onError, 请求失败
                        throwable -> Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show()
                );


//        mWvArticle.loadData("<html><body><font color='red'>hello baidu!</font></body></html>", "text/html", "UTF-8");
    }
}
