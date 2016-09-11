package com.example.jinliangshan.littlezhihu.home.util;

import android.webkit.WebView;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-09-11
 * Time: 16:17
 * <br/><br/>
 */
public class WebViewUtil {
    public static final String CSS_URL = "file:///android_asset/" + "style.css";
    public static final String USE_CSS_LINK = String.format("<link rel=\"stylesheet\"" +
            " type=\"text/css\" href=\"%s\" />", CSS_URL);

    public static void loadDataWithCss(WebView webView, String htmlEntry){
        webView.loadDataWithBaseURL(null,
                USE_CSS_LINK + htmlEntry,
                "text/html", "UTF-8", null);
    }
}  