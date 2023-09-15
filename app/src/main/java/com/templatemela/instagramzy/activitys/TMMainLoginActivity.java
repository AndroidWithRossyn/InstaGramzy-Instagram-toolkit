package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import com.templatemela.instagramzy.utils.CustomWebview;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Loader;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.handeler.TMUserDetails;

public class TMMainLoginActivity extends AppCompatActivity {
    Functions functions;
    Boolean isOn = true;
    Loader loader;
    TextView pp;
    TMUserDetails userDetails;
    WebView webView;
    int x;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main_login_screen);
        userDetails = new TMUserDetails(this);
        webView = new WebView(createConfigurationContext(new Configuration()));
        webView = (WebView) findViewById(R.id.webview);
        Loader loader2 = new Loader(this);
        loader = loader2;
        try {
            loader2.showOnlyAnimation();
        } catch (Exception unused) {
        }
        TextView textView = (TextView) findViewById(R.id.pp);
        pp = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(TMMainLoginActivity.this, CustomWebview.class);
                intent.putExtra("url", "file:///android_asset/privacy_policy.html");
                startActivity(intent);
            }
        });
        functions = new Functions(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyBrowser());
        webView.loadUrl("https://www.instagram.com/accounts/login/?next=%2F&source=mobile_nav");
        new Thread(new Runnable() {
            public void run() {
                while (isOn.booleanValue()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (Build.VERSION.SDK_INT >= 19) {
                                webView.evaluateJavascript("console.log(document.cookie)", (ValueCallback) null);
                            } else {
                                webView.loadUrl("javascript:console.log(document.cookie)");
                            }
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (x == 0 && ((consoleMessage.message().contains("csrftoken") || consoleMessage.message().contains("ig_nrcb") || consoleMessage.message().contains("mid")) && consoleMessage.message().contains("ds_user_id") && !functions.getUserId().equals("null"))) {
                    x = 1;
                    userDetails.login();
                    Toast.makeText(TMMainLoginActivity.this, "you are logged in", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(TMMainLoginActivity.this, TMMainActivity.class));
                    finish();
                }
                return super.onConsoleMessage(consoleMessage);
            }
        });

    }

    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.contains("error_code=PLATFORM__LOGIN_DISABLED_FROM")) {
                return true;
            }
            webView.loadUrl(str);
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        @Override
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            loader.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        startActivity(new Intent(this, TMLoginActivity.class));
        finish();
    }

    public void applyOverrideConfiguration(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 25) {
            configuration.uiMode &= -49;
        }
        super.applyOverrideConfiguration(configuration);
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }
}
