package com.example.jinliangshan.littlezhihu.home.model;

import com.example.jinliangshan.littlezhihu.home.base.BaseJavaBean;

import java.util.List;

/**
 * Created by jinliangshan on 16/8/26.
 */
public class LatestNews extends BaseJavaBean {

    /**
     * date : 20140523
     * stories : [{"title":"中国古代家具发展到今天有两个高峰，一个两宋一个明末（多图）","ga_prefix":"052321","images":["http://p1.zhimg.com/45/b9/45b9f057fc1957ed2c946814342c0f02.jpg"],"type":0,"id":3930445}]
     * top_stories : [{"title":"商场和很多人家里，竹制家具越来越多（多图）","image":"http://p2.zhimg.com/9a/15/9a1570bb9e5fa53ae9fb9269a56ee019.jpg","ga_prefix":"052315","type":0,"id":3930883}]
     */

    private String date = "aaaaa";
    private List<ArticlePreview> stories;
    private List<TopArticle> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ArticlePreview> getStories() {
        return stories;
    }

    public void setStories(List<ArticlePreview> stories) {
        this.stories = stories;
    }

    public List<TopArticle> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopArticle> top_stories) {
        this.top_stories = top_stories;
    }
}
