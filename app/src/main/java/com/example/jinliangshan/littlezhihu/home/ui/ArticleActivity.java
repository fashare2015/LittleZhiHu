package com.example.jinliangshan.littlezhihu.home.ui;

import android.support.v4.app.Fragment;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;

public class ArticleActivity extends BaseFragmentActivity {

    Fragment mArticleFragment;

    @Override
    protected void initFragment() {
        mArticleFragment = new ArticleFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleFragment);
    }
}
