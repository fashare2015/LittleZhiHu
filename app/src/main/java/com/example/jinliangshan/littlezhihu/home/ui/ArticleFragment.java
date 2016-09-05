package com.example.jinliangshan.littlezhihu.home.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment;
import com.example.jinliangshan.littlezhihu.home.rxjava.observable.Observables;

import butterknife.BindView;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class ArticleFragment extends BaseFragment {

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
    protected int getLayoutRes() {
        return R.layout.fragment_article;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView(View view) {
        mWebSettings = mWvArticle.getSettings();
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void loadData() {
        Observables.getArticleObservable(mArticleId)
                .subscribe(
                        // onNext, 请求成功
                        article -> {
                            Log.i("aaaaa", article.getCss().get(0));
//                            String cssUrl = article.getCss()!=null? article.getCss().get(0): null;
                            String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + article.getBody();
                            String cssUrl = "file:///android_asset/";
                            mWvArticle.loadDataWithBaseURL(cssUrl,
                                    htmlData,
                                    "text/html", "UTF-8", null);
                        },
                        // onError, 请求失败
                        throwable -> Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show()
                );


//        mWvArticle.loadData("<html><body><font color='red'>hello baidu!</font></body></html>", "text/html", "UTF-8");
    }
}
