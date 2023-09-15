package com.templatemela.instagramzy.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.activitys.TMLoginActivity;

public class WebViewDialog {
    Context context;
    Dialog logindialog;
    WebView loginwebView;
    Button logout;

    public WebViewDialog(Context context2) {
        context = context2;
    }

    public void showWebView(String str) {
        logindialog= new Dialog(context);

        logindialog.setCancelable(true);
        logindialog.setContentView(R.layout.login_browser);
        loginwebView = (WebView) logindialog.findViewById(R.id.webview);
        logout = (Button) logindialog.findViewById(R.id.logout_btn);
        loginwebView.getSettings().setJavaScriptEnabled(true);
        loginwebView.setWebViewClient(new MyBrowser());
        loginwebView.loadUrl(str);
        logindialog.show();
        logout.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                loginwebView.loadUrl("https://mbasic.facebook.com/login/save-password-interstitial");
            }
        });
    }

    private class MyBrowser extends WebViewClient {
        int i;

        private MyBrowser() {
            i = 0;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            if (str.equals("https://m.facebook.com/profile.php")) {
                logout.setVisibility(View.VISIBLE);
            } else {
                logout.setVisibility(View.GONE);
            }
        }


        @Override
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (i == 1 && !str.equals("https://mbasic.facebook.com/login/save-password-interstitial")) {
                i = 2;
                context.startActivity(new Intent(context, TMLoginActivity.class));
            }
            if (str.equals("https://mbasic.facebook.com/login/save-password-interstitial")) {
                i = 1;
            }
        }
    }
}
