package com.example.jinliangshan.littlezhihu.home.network.loaddata;

import com.annimon.stream.Stream;
import com.example.jinliangshan.littlezhihu.home.model.LatestNews;
import com.example.jinliangshan.littlezhihu.home.network.OkHttpUtil;
import com.example.jinliangshan.littlezhihu.home.network.api.Apis;
import com.google.gson.Gson;

/**
 * Created by apple on 16-8-27.
 */

public class OnLoadLatestNews implements OnLoadData<LatestNews> {
    @Override
    public LatestNews loadData() {
        return Stream.of(OkHttpUtil.getInstance().get(Apis.URL_LATEST_NEWS))
                .map(responseString -> new Gson().fromJson(responseString, LatestNews.class))
                .findFirst().orElse(null);
    }
}
