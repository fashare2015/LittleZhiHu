package com.example.jinliangshan.littlezhihu.home.network.loaddata;

/**
 * 获取 data: 来自网络任务或是别的...
 * @param <T> data 类型
 */
public interface OnLoadData<T>{
    T loadData();
}