package com.example.jinliangshan.littlezhihu.home.base;

import android.view.View;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-09-06
 * Time: 18:25
 * <br/><br/>
 */
public interface OnItemClickListener<T> {
    void onItemClick(View itemView, T data, int position);
}