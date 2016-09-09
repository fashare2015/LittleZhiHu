package com.example.jinliangshan.littlezhihu.home.base;

/**
 * Created by jinliangshan on 16/9/9.
 * 引用管理
 */
public interface ReferencesManager {
    /**
     * 重写它, 以释放类中的一些引用.<br/>
     * 同时, 该类的持有者需要手动调用该函数.
     */
    void clearReferences();
}
