package com.example.jinliangshan.littlezhihu.home.cache;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-08-27
 * Time: 21:53
 * <br/><br/>
 */
public class BitmapCache {
    protected final String TAG = this.getClass().getSimpleName();

    private Map<String, SoftReference<Bitmap>> cache = Collections
            .synchronizedMap(new HashMap<>());

    public Bitmap get(String id) {
        if (!cache.containsKey(id) || cache.get(id) == null)
            return null;
        SoftReference<Bitmap> ref = cache.get(id);
        return ref.get();
    }

    public void put(String id, Bitmap bitmap) {
        cache.put(id, new SoftReference<>(bitmap));
    }

    public void clear() {
        cache.clear();
    }

}

