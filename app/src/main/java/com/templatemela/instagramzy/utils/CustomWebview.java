package com.templatemela.instagramzy.utils;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.templatemela.instagramzy.R;


public class CustomWebview extends AppCompatActivity {
    WebView webView;

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_custom_webview);
        webView = (WebView) findViewById(R.id.webview);
        String stringExtra = getIntent().getStringExtra("url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyBrowser());
        webView.loadUrl(stringExtra);
    }

    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }
    }
}
