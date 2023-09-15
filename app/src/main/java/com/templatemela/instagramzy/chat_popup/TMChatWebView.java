package com.templatemela.instagramzy.chat_popup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMChatModel;
import com.templatemela.instagramzy.models.TMStrModel;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.File;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMChatWebView {
    public static final int REQUEST_SELECT_FILE = 100;
    TMChatModel chatModel;
    Context context;
    CircleImageView dp;
    Handler handler;
    boolean isKeyboardShowing = false;
    LayoutInflater layoutInflater;
    RelativeLayout.LayoutParams layoutParams;
    LottieAnimationView lottieAnimationView;
    Uri media;
    TextView name;
    int screenHeight = 0;
    String script;
    String tag = "ChatWebView";
    public ValueCallback<Uri[]> uploadMessage;
    WebView webView;
    int webViewH;
    int webViewW;

    public TMChatWebView(LayoutInflater layoutInflater2, Context context2, TMChatModel chatModel2) {
        this.layoutInflater = layoutInflater2;
        this.context = context2;
        this.chatModel = chatModel2;
        this.handler = new Handler(Looper.getMainLooper());
        this.script = "var listSize =0;\nvar myVar;\n\nfunction timer(){\n\tmyVar = setInterval(callMyf, 200);\n}\nfunction callMyf(){\n\tvar a = document.getElementsByClassName('-qQT3');\n\tif(a.length>listSize){\n\t\tclearInterval(myVar);\n\t\tmyf();\n\t}else{\n\t   myf1();\n\t}\n}\nfunction myf1(){\n\tif(a[i].href.includes('" + chatModel2.getThreadId() + "')){\n\t\ta[i].click();\n\t\tconsole.log('chat opened');\n\t\tclearInterval(myVar);\n\t}\n}\n\nfunction myf(){\n\tvar a = document.getElementsByClassName('-qQT3');\n\tlistSize = a.length;\n\tfor(var i = 0; i<listSize;i++){\n\t\t if(a[i].href.includes('" + chatModel2.getThreadId() + "')){\n\t\t\ta[i].click();\n\t\t\tconsole.log('chat opened');\n\t\t }else{\n\t\t\t  if(String(a[i]).includes('" + chatModel2.getThreadId() + "')){\n\t\t\t\ta[i].click();\n\t\t\t\tconsole.log('chat opened');\n\t\t\t }else{\n\t\t\tvar element = document.getElementsByClassName('N9abW')[0];\n\t\t\telement.scrollTop = element.scrollHeight - element.clientHeight;\n\t\t\ttimer();}\n\t\t }\n\t}\n}\nmyf();";
    }

    public View getView() {
        EventBus.getDefault().register(this);
        View inflate = this.layoutInflater.inflate(R.layout.expand_view_bottom_item, (ViewGroup) null);
        this.lottieAnimationView = (LottieAnimationView) inflate.findViewById(R.id.lottie_layer);
        WebView webView2 = (WebView) inflate.findViewById(R.id.web);
        this.webView = webView2;
        this.layoutParams = (RelativeLayout.LayoutParams) webView2.getLayoutParams();
        this.lottieAnimationView.setVisibility(View.VISIBLE);
        this.dp = (CircleImageView) inflate.findViewById(R.id.dp);
        this.name = (TextView) inflate.findViewById(R.id.name);
        Glide.with(this.context).load(this.chatModel.getDp()).into((ImageView) this.dp);
        this.name.setText(this.chatModel.getUsername());
        this.webView.setVisibility(View.GONE);
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        this.webView.setWebViewClient(new MyBrowser());
        this.webView.loadUrl("https://www.instagram.com/direct/inbox/");
        this.webView.setWebChromeClient(new WebChromeClient() {
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                Log.i(TMChatWebView.this.tag, fileChooserParams.getAcceptTypes()[0]);
                if (TMChatWebView.this.uploadMessage != null) {
                    TMChatWebView.this.uploadMessage.onReceiveValue( null);
                    TMChatWebView.this.uploadMessage = null;
                }
                TMChatWebView.this.uploadMessage = valueCallback;
                EventBus.getDefault().post(new TMStrModel("open_file_selection", 100));
                return true;
            }

            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.i(TMChatWebView.this.tag, consoleMessage.message());
                if (consoleMessage.message().contains("chat opened")) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TMChatWebView.this.lottieAnimationView.setVisibility(View.GONE);
                    TMChatWebView.this.webView.setVisibility(View.VISIBLE);
                }
                return super.onConsoleMessage(consoleMessage);
            }
        });
        Rect rect = new Rect();
        this.webView.getWindowVisibleDisplayFrame(rect);
        this.webViewH = rect.height();
        this.webView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                Rect rect = new Rect();
                TMChatWebView.this.webView.getWindowVisibleDisplayFrame(rect);
                if (TMChatWebView.this.screenHeight == 0) {
                    TMChatWebView.this.screenHeight = rect.bottom;
                }
                int i = TMChatWebView.this.screenHeight - rect.bottom;
                String str = TMChatWebView.this.tag;
                Log.d(str, "keypadHeight = " + i);
                if (((double) i) > ((double) TMChatWebView.this.screenHeight) * 0.05d) {
                    if (!TMChatWebView.this.isKeyboardShowing) {
                        TMChatWebView.this.isKeyboardShowing = true;
                        TMChatWebView.this.onKeyboardVisibilityChanged(true, i);
                    }
                } else if (TMChatWebView.this.isKeyboardShowing) {
                    TMChatWebView.this.isKeyboardShowing = false;
                    TMChatWebView.this.onKeyboardVisibilityChanged(false, i);
                }
            }
        });
        return inflate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TMStrModel str) {
        if (str.getType() == 101) {
            this.uploadMessage.onReceiveValue(new Uri[]{Uri.fromFile(new File(str.getData()))});
            this.uploadMessage = null;
        }
        if (str.getType() == 102 && str.getData().equals("cancel")) {
            this.uploadMessage.onReceiveValue(new Uri[0]);
            this.uploadMessage = null;
        }
    }

    
    public void onKeyboardVisibilityChanged(boolean z, int i) {
        if (z) {
            this.layoutParams.height = (this.webViewH - i) - 200;
            this.webView.setLayoutParams(this.layoutParams);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TMChatWebView.this.handler.post(new Runnable() {
                        public void run() {
                            TMChatWebView.this.webView.evaluateJavascript("var element = document.getElementsByClassName('frMpI  ')[0];\nelement.scrollTop = element.scrollHeight - element.clientHeight;", (ValueCallback) null);
                        }
                    });
                }
            }).start();
            return;
        }
        this.layoutParams.height = this.webViewH;
        this.webView.setLayoutParams(this.layoutParams);
    }

    private class MyBrowser extends WebViewClient {
        int i;

        private MyBrowser() {
            this.i = 0;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.contains("https://www.instagram.com/direct/")) {
                TMChatWebView.this.webView.getSettings().setBlockNetworkImage(true);
            }
            return true;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            Log.i("webview load finished", str);
            TMChatWebView.this.webView.evaluateJavascript(TMChatWebView.this.script, (ValueCallback) null);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TMChatWebView.this.handler.post(new Runnable() {
                        public void run() {
                            TMChatWebView.this.webView.evaluateJavascript(TMChatWebView.this.script, (ValueCallback) null);
                        }
                    });
                }
            }).start();
            if (str.contains("https://www.instagram.com/direct/t/")) {
                TMChatWebView.this.webView.evaluateJavascript("document.getElementsByClassName('Z_FEn _9ezyW')[0].style.display= 'none';", (ValueCallback) null);
            } else {
                TMChatWebView.this.webView.goBack();
            }
            TMChatWebView.this.webView.evaluateJavascript("document.getElementsByClassName('Z_FEn _9ezyW')[0].style.display= 'none';", (ValueCallback) null);
        }
    }

    public void onShow() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TMChatWebView.this.handler.post(new Runnable() {
                    public void run() {
                        if (TMChatWebView.this.webView.getUrl().equals("https://www.instagram.com/direct/inbox/")) {
                            TMChatWebView.this.webView.evaluateJavascript(TMChatWebView.this.script, (ValueCallback) null);
                        }
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                TMChatWebView.this.handler.post(new Runnable() {
                    public void run() {
                        if (TMChatWebView.this.webView.getUrl().equals("https://www.instagram.com/direct/inbox/")) {
                            TMChatWebView.this.webView.evaluateJavascript(TMChatWebView.this.script, (ValueCallback) null);
                        }
                    }
                });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
                TMChatWebView.this.handler.post(new Runnable() {
                    public void run() {
                        if (TMChatWebView.this.webView.getUrl().equals("https://www.instagram.com/direct/inbox/")) {
                            TMChatWebView.this.webView.evaluateJavascript(TMChatWebView.this.script, (ValueCallback) null);
                        }
                    }
                });
            }
        }).start();
    }
}
