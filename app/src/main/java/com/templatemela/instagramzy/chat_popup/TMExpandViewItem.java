package com.templatemela.instagramzy.chat_popup;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMChatModel;
import com.templatemela.instagramzy.models.TMChatNotificationModel;
import com.templatemela.instagramzy.models.TMStrModel;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMExpandViewItem extends FrameLayout {
    TMChatModel chatModel;
    TMChatWebView chatWebView;
    View childView;
    RelativeLayout content;
    private View contentView;
    Context context;
    TextView count;
    LinearLayout countHolder;
    int currentPostion = 0;
    CircleImageView dp;
    TMExpendViewEvents expendViewEvents;
    FrameLayout frameLayout;
    int index;
    boolean isKeyboardShowing = false;
   
    public View mFloatingWidgetView;
    int moveCount = 0;
    private Point position = new Point();
    Pref pref;
    private ImageView remove_image_view;
    int screenHeight = 0;
    private Point szWindow = new Point();
    String tag = "ExpandViewItem";
    WebView webView;
   
    public int x_init_cord;
   
    public int x_init_margin;
   
    public int y_init_cord;
   
    public int y_init_margin;

    public TMExpandViewItem(Context context2, Point point, FrameLayout frameLayout2, int i, TMChatModel chatModel2) {
        super(context2);
        this.context = context2;
        this.frameLayout = frameLayout2;
        this.index = i;
        this.szWindow = point;
        this.chatModel = chatModel2;
        Sizes.setContext(context2);
        this.pref = new Pref(context2);
        EventBus.getDefault().register(this);
        LayoutInflater layoutInflater = (LayoutInflater) context2.getSystemService("layout_inflater");
        this.expendViewEvents = (TMExpendViewEvents) context2;
        View inflate = layoutInflater.inflate(R.layout.expand_view_head_item, (ViewGroup) null);
        this.mFloatingWidgetView = inflate;
        this.dp = (CircleImageView) inflate.findViewById(R.id.dp);
        if (chatModel2 != null) {
            Glide.with(context2).load(Integer.valueOf(R.raw.place_holder)).into((ImageView) this.dp);
        }
        this.countHolder = (LinearLayout) this.mFloatingWidgetView.findViewById(R.id.countHolder);
        this.count = (TextView) this.mFloatingWidgetView.findViewById(R.id.unseencount);
        if (chatModel2 != null) {
            Glide.with(this.mFloatingWidgetView).load(chatModel2.getDp()).into((ImageView) this.dp);
        }
        if (i > 0) {
            TMChatWebView chatWebView2 = new TMChatWebView(layoutInflater, context2, chatModel2);
            this.chatWebView = chatWebView2;
            this.contentView = chatWebView2.getView();
            this.childView = this.chatWebView.webView;
        } else {
            TMMainExpendView mainExpendView = new TMMainExpendView(layoutInflater, context2);
            this.contentView = mainExpendView.getView();
            this.childView = mainExpendView.swipeRefreshLayout;
        }
        frameLayout2.addView(this.contentView);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(point.x, point.y - ((Sizes.getHeadSize() + Sizes.getTriangleSize()) + Sizes.getVerticalGap()));
        layoutParams.leftMargin = 0;
        layoutParams.topMargin = Sizes.getHeadSize() + Sizes.getTriangleSize();
        this.contentView.setLayoutParams(layoutParams);
        frameLayout2.addView(this.mFloatingWidgetView);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(Sizes.getHeadSize(), Sizes.getHeadSize());
        int i2 = i + 1;
        layoutParams2.leftMargin = point.x - ((Sizes.getHeadSize() + Sizes.getHorizontalGap()) * i2);
        layoutParams2.topMargin = 0;
        this.mFloatingWidgetView.setLayoutParams(layoutParams2);
        this.position.x = point.x - (i2 * (Sizes.getHeadSize() + Sizes.getHorizontalGap()));
        this.position.y = 0;
        implementTouchListenerToFloatingWidgetView();
        setCount();
    }

    private void implementTouchListenerToFloatingWidgetView() {
        this.mFloatingWidgetView.findViewById(R.id.collapse_view).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i(TMExpandViewItem.this.tag, "on click");
            }
        });
        this.mFloatingWidgetView.findViewById(R.id.collapse_view).setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) TMExpandViewItem.this.mFloatingWidgetView.getLayoutParams();
                int action = motionEvent.getAction();
                if (action == 0) {
                    TMExpandViewItem.this.moveCount = 0;
                    TMExpandViewItem.this.mFloatingWidgetView.setScaleX(0.9f);
                    TMExpandViewItem.this.mFloatingWidgetView.setScaleY(0.9f);
                    return true;
                } else if (action == 1) {
                    TMExpandViewItem.this.mFloatingWidgetView.setScaleX(1.0f);
                    TMExpandViewItem.this.mFloatingWidgetView.setScaleY(1.0f);
                    TMExpandViewItem.this.resetPosition(rawX, rawY);
                    if (TMExpandViewItem.this.moveCount < 5) {
                        TMExpandViewItem.this.expendViewEvents.onToach(TMExpandViewItem.this.index);
                    } else {
                        TMExpandViewItem.this.expendViewEvents.onRelease();
                    }
                    TMExpandViewItem.this.moveCount = 0;
                    return true;
                } else if (action != 2) {
                    return false;
                } else {
                    Log.i(TMExpandViewItem.this.tag, "MotionEvent.ACTION_move");
                    TMExpandViewItem.this.moveCount++;
                    if (TMExpandViewItem.this.index != 0) {
                        TMExpandViewItem.this.mFloatingWidgetView.setScaleX(0.9f);
                        TMExpandViewItem.this.mFloatingWidgetView.setScaleY(0.9f);
                        if (TMExpandViewItem.this.moveCount > 5) {
                            int access$200 = rawX - TMExpandViewItem.this.x_init_cord;
                            int access$300 = rawY - TMExpandViewItem.this.y_init_cord;
                            layoutParams.leftMargin = (TMExpandViewItem.this.x_init_margin + access$200) - 85;
                            layoutParams.topMargin = (TMExpandViewItem.this.y_init_margin + access$300) - 170;
                            TMExpandViewItem.this.mFloatingWidgetView.setLayoutParams(layoutParams);
                            TMExpandViewItem.this.expendViewEvents.onMove();
                        }
                    }
                    return true;
                }
            }
        });
    }

   
    public void resetPosition(int i, int i2) {
        Log.i(this.tag, "on reset position");
        int i3 = this.szWindow.x / 2;
        if (i >= i3 + 150 || i <= i3 - 100 || i2 <= this.szWindow.y - 500) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mFloatingWidgetView.getLayoutParams();
            layoutParams.leftMargin = this.position.x;
            layoutParams.topMargin = this.position.y;
            this.mFloatingWidgetView.setLayoutParams(layoutParams);
            return;
        }
        remove();
    }

    
    public void setIndex(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mFloatingWidgetView.getLayoutParams();
        int i2 = i + 1;
        layoutParams.leftMargin = this.szWindow.x - ((Sizes.getHeadSize() + Sizes.getHorizontalGap()) * i2);
        layoutParams.topMargin = 0;
        this.position.x = this.szWindow.x - (i2 * (Sizes.getHeadSize() + Sizes.getHorizontalGap()));
        this.position.y = 0;
        this.index = i;
        this.mFloatingWidgetView.setLayoutParams(layoutParams);
    }

    public void hide() {
        this.mFloatingWidgetView.setVisibility(View.GONE);
        this.contentView.setVisibility(View.GONE);
    }

    public void show() {
        this.mFloatingWidgetView.setVisibility(View.VISIBLE);
        this.contentView.setVisibility(View.VISIBLE);
        TMChatWebView chatWebView2 = this.chatWebView;
        if (chatWebView2 != null) {
            chatWebView2.onShow();
        }
    }

    public void hideBottom(int i) {
        if (i == this.index) {
            this.contentView.setVisibility(View.GONE);
        }
    }

    public void remove() {
        this.expendViewEvents.onRemoveItem(this.index);
        this.frameLayout.removeView(this.mFloatingWidgetView);
        this.frameLayout.removeView(this.contentView);
        EventBus.getDefault().unregister(this);
    }

    public void setCurrentItem(int i) {
        this.currentPostion = i;
        if (i == this.index) {
            this.contentView.setVisibility(View.VISIBLE);
            try {
                HashMap<String, Integer> unseenCount = this.pref.getUnseenCount();
                unseenCount.put(this.chatModel.getUsername(), 0);
                new Pref(this.context).saveUnseenCount(unseenCount);
            } catch (Exception unused) {
            }
            setCount();
            EventBus.getDefault().post(new TMStrModel("update", 99));
            return;
        }
        this.contentView.setVisibility(View.GONE);
    }

    public void removeToAddNew() {
        this.frameLayout.removeView(this.mFloatingWidgetView);
        this.frameLayout.removeView(this.contentView);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsg(TMChatNotificationModel chatNotificationModel) {
        if (this.currentPostion == this.index && chatNotificationModel.getUsername().equals(this.chatModel.getUsername())) {
            try {
                HashMap<String, Integer> unseenCount = this.pref.getUnseenCount();
                unseenCount.put(this.chatModel.getUsername(), 0);
                new Pref(this.context).saveUnseenCount(unseenCount);
            } catch (Exception unused) {
            }
            EventBus.getDefault().post(new TMStrModel("update", 99));
        }
        setCount();
    }

    
    public void setCount() {
        try {
            HashMap<String, Integer> unseenCount = new Pref(this.context).getUnseenCount();
            int parseDouble = (int) Double.parseDouble(unseenCount.get(this.chatModel.getUsername()) + "");
            String str = this.tag;
            Log.i(str, "on set Count" + parseDouble);
            TextView textView = this.count;
            textView.setText(parseDouble + "");
            if (parseDouble == 0) {
                this.countHolder.setAlpha(0.0f);
            } else {
                this.countHolder.setAlpha(1.0f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.countHolder.setAlpha(0.0f);
        }
    }
}
