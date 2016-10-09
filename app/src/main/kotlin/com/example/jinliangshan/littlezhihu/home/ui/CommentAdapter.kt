package com.example.jinliangshan.littlezhihu.home.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jinliangshan.littlezhihu.R
import com.example.jinliangshan.littlezhihu.home.base.BaseRecyclerViewAdapter
import com.example.jinliangshan.littlezhihu.home.model.Comments
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(context: Context, recyclerView: RecyclerView):
        BaseRecyclerViewAdapter<Comments.CommentsBean>(context, recyclerView) {

    override fun newViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<Comments.CommentsBean> {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View): BaseViewHolder<Comments.CommentsBean>(itemView){
        override fun onBind(data: Comments.CommentsBean?, pos: Int) {
            itemView.tv_title.text = data?.author
            itemView.tv_content.text = data?.content
        }
    }
}
