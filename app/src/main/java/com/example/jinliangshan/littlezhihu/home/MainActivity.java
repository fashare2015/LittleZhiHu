package com.example.jinliangshan.littlezhihu.home;

import android.support.v4.app.Fragment;

import com.example.jinliangshan.littlezhihu.R;
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity;
import com.example.jinliangshan.littlezhihu.home.ui.ArticleListFragment;
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil;

public class MainActivity extends BaseFragmentActivity {

    Fragment mArticleListFragment;

    @Override
    protected void initFragment() {
        mArticleListFragment = new ArticleListFragment();
        FragmentManagerUtil.addFragment(getSupportFragmentManager(), R.id.fragment_container, mArticleListFragment);
    }
}

