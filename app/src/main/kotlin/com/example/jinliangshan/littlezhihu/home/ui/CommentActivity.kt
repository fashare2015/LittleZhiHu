package com.example.jinliangshan.littlezhihu.home.ui

import android.app.Activity
import android.content.Intent
import com.example.jinliangshan.littlezhihu.R
import com.example.jinliangshan.littlezhihu.home.base.BaseFragmentActivity
import com.example.jinliangshan.littlezhihu.home.util.FragmentManagerUtil

class CommentActivity : BaseFragmentActivity() {
    private var mCommentFragment: CommentFragment? = null
    private var mArticleId: Int = 0

    override fun getLayoutRes(): Int {
        return R.layout.fragment_activity_comment
    }

    override fun initBundle() {
        super.initBundle()
        mArticleId = intent.getIntExtra(ARTICLE_ID, 0)
    }

    override fun initView() {
        super.initView()
    }

    override fun initFragment() {
        mCommentFragment = CommentFragment.getInstance(mArticleId)
        FragmentManagerUtil.addFragment(supportFragmentManager, R.id.fragment_container, mCommentFragment)
    }

    companion object {
        val ARTICLE_ID: String? = "ARTICLE_ID"

        fun startThis(from: Activity, articleId: Int) {
            from.startActivity(
                    Intent(from, CommentActivity::class.java)
                            .putExtra(ARTICLE_ID, articleId))
        }
    }
}
