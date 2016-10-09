package com.example.jinliangshan.littlezhihu.home.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.jinliangshan.littlezhihu.R
import com.example.jinliangshan.littlezhihu.home.base.BaseFragment
import com.example.jinliangshan.littlezhihu.home.rxjava.KotlinObservables
import kotlinx.android.synthetic.main.fragment_comment.*

/**
 * Created by apple on 16-10-9.
 */
class CommentFragment: BaseFragment{
    companion object{
        fun getInstance(articleId: Int): CommentFragment {
            val params = Bundle()
            params.putInt(CommentActivity.ARTICLE_ID, articleId)
            val fragment = CommentFragment()
            fragment.arguments = params
            return fragment
        }
    }

    var mCommentAdapter: CommentAdapter? = null
    private var mArticleId: Int = 0

    private constructor() : super()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mArticleId = arguments.getInt(ArticleActivity.ARTICLE_ID, 0)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_comment
    }

    override fun initView() {
        super.initView()

        mCommentAdapter = CommentAdapter(context, rv_comment_list)
        rv_comment_list.adapter = mCommentAdapter
        rv_comment_list.layoutManager = LinearLayoutManager(mContext)
    }

    override fun loadData() {
        super.loadData()
        KotlinObservables.getCommentObservable(mArticleId)
                .subscribe(
                        { mCommentAdapter?.dataList = it.comments },
                        { Toast.makeText(mContext, it.message, Toast.LENGTH_LONG).show() }
                )
    }
}

