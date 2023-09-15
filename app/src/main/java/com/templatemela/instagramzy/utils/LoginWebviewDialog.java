package com.templatemela.instagramzy.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.interfaces.LoginInterface;

public class LoginWebviewDialog {
    Context context;
    Functions functions;
    Handler handler;
    Boolean isOn = true;
    LoginInterface loginInterface;
    Dialog logindialog;
    WebView loginwebView;
    int type;
    int x = 0;

    public LoginWebviewDialog(Context context2, int i) {
        context = context2;
        loginInterface = (LoginInterface) context2;
        type = i;
        functions = new Functions(context2);
    }

    public void showDialog() {
        handler = new Handler(Looper.getMainLooper());
        logindialog = new Dialog(context);
        logindialog.setCancelable(true);
        logindialog.setContentView(R.layout.login_browser);
        loginwebView = (WebView) logindialog.findViewById(R.id.webview);
        loginwebView.getSettings().setJavaScriptEnabled(true);
        loginwebView.setWebViewClient(new MyBrowser());
        loginwebView.loadUrl("https://www.instagram.com");
        new Thread(new Runnable() {

           @Override
           public void run() {
                while (isOn.booleanValue()) {
                    handler.post(new Runnable() {
                        public void run() {
                            if (Build.VERSION.SDK_INT >= 19) {
                                loginwebView.evaluateJavascript("console.log(document.cookie)", (ValueCallback) null);
                            } else {
                                loginwebView.loadUrl("javascript:console.log(document.cookie)");
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
        loginwebView.setWebChromeClient(new WebChromeClient() {
          @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (x == 0 && (consoleMessage.message().contains("csrftoken") || consoleMessage.message().contains("ig_nrcb") || consoleMessage.message().contains("mid"))) {
                    if (type == 0 && consoleMessage.message().contains("ds_user_id") && !functions.getUserId().equals("null")) {
                        x = 1;
                        Toast.makeText(context, "you are logged in", Toast.LENGTH_SHORT).show();
                        loginInterface.onLogInComplete();
                        logindialog.dismiss();
                        isOn = false;
                    }
                    if (type == 1 && !consoleMessage.message().contains("ds_user_id")) {
                        x = 1;
                        Toast.makeText(context, "you are logged out", Toast.LENGTH_SHORT).show();
                        loginInterface.onLogOutComplete();
                        logindialog.dismiss();
                        isOn = false;
                    }
                }
                return super.onConsoleMessage(consoleMessage);
            }
        });
        logindialog.show();
    }

    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }
    }
}
