package com.example.jinliangshan.littlezhihu.home.base;

import com.example.jinliangshan.littlezhihu.home.util.GsonUtil;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-09-11
 * Time: 15:57
 * <br/><br/>
 */
public class BaseJavaBean {

    @Override
    public String toString() {
        return GsonUtil.toJson(this);
    }
}  