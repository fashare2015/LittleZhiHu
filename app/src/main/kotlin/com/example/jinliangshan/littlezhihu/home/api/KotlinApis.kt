package com.example.jinliangshan.littlezhihu.home.api

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-09
 * Time: 23:38
 *
 */
class KotlinApis: Apis(){
    companion object {
        fun getLongCommentUrl(articleId: Int): String {
            return BASE_URL + "story/${articleId}/long-comments"
        }

        fun getShortCommentUrl(articleId: Int): String {
            return BASE_URL + "story/${articleId}/short-comments"
        }
    }
}