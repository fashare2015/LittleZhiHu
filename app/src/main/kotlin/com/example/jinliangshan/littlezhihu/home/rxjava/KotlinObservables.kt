package com.example.jinliangshan.littlezhihu.home.rxjava

import com.example.jinliangshan.littlezhihu.home.api.KotlinApis
import com.example.jinliangshan.littlezhihu.home.model.Comments
import rx.Observable

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-09
 * Time: 23:19
 *
 */
class KotlinObservables: Observables(){
    companion object {  // 即 static

        fun getCommentObservable(articleId: Int): Observable<Comments> {
            return ObservableUtil.newInstance {
                loadDataFrom(KotlinApis.getLongCommentUrl(articleId), Comments:: class.java)
            }
        }
    }
}