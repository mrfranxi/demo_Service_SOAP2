package com.soap.franxi.demo_service_soap;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by Admin on 17/12/2016.
 */

public class NewsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        WebView wvNews = (WebView) findViewById(R.id.wvContent);

        wvNews.loadUrl("http://tiengnhatthatde.com");


    }
}
