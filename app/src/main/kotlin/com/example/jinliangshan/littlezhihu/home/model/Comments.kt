package com.example.jinliangshan.littlezhihu.home.model

import com.google.gson.Gson

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-09
 * Time: 23:47
 *
 */
class Comments {
    var comments: List<CommentsBean>? = null

    /**
     * author : Xiaole说
     * id : 545721
     * content : 就吃了个花生米，呵呵
     * likes : 0
     * time : 1413600071
     * avatar : http://pic1.zhimg.com/c41f035ab_im.jpg
     */
    class CommentsBean {
        var author: String? = null
        var id: Int = 0
        var content: String? = null
        var likes: Int = 0
        var time: Int = 0
        var avatar: String? = null

        override fun toString(): String {
            return Gson().toJson(this)
        }
    }
}