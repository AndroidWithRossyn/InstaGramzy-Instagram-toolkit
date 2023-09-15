package com.templatemela.instagramzy.chat_popup;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMChatModel;
import com.templatemela.instagramzy.models.TMChatNotificationModel;
import com.templatemela.instagramzy.models.TMStrModel;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMFloatingWidgetService extends Service implements TMExpendViewEvents {

    public View background;
    CircleImageView circleImageView;
    RelativeLayout collapsedView;
    TextView count;
    LinearLayout countHolder;
    int currentItem = 0;
    TMFileSelectView fileSelectView;
    Handler handler;
    int headSize;
    LayoutInflater inflater;
    boolean isKeyBoardOpen = false;
    boolean isLeft;
    LottieAnimationView lottieAnimationView;
    public View mFloatingWidgetView;
    public WindowManager mWindowManager;
    int movecount = 0;
   
    public Point position = new Point();
    private View removeFloatingWidgetView;
    private ImageView remove_image_view;
   
    public Point szWindow = new Point();
    String tag = "FloatingWidgetService";
    TMViewList viewList;
   
    public int x_init_cord;
   
    public int x_init_margin;
   
    public int y_init_cord;
   
    public int y_init_margin;

    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        this.handler = new Handler(getMainLooper());
        Sizes.setContext(getApplicationContext());
        this.headSize = Sizes.getHeadSize();
        this.mWindowManager = (WindowManager) getSystemService("window");
        getWindowManagerDefaultDisplay();
        this.inflater = (LayoutInflater) getSystemService("layout_inflater");
        addBackground();
        addFloatingWidgetView();
        TMViewList viewList2 = new TMViewList(this, this.mWindowManager, this.inflater);
        this.viewList = viewList2;
        viewList2.addItem();
        this.viewList.hide();
        implementTouchListenerToFloatingWidgetView();
        setCount();
        this.fileSelectView = new TMFileSelectView(getApplicationContext(), this.mWindowManager);
    }

    private void addBackground() {
        WindowManager.LayoutParams layoutParams;
        View inflate = this.inflater.inflate(R.layout.chat_head_back, (ViewGroup) null);
        this.background = inflate;
        this.lottieAnimationView = (LottieAnimationView) inflate.findViewById(R.id.lottie_layer);
        if (Build.VERSION.SDK_INT < 26) {
            layoutParams = new WindowManager.LayoutParams(-2, -2, 2003, 32, -3);
        } else {
            layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 32, -3);
        }
        layoutParams.height = this.szWindow.y;
        layoutParams.width = this.szWindow.x;
        layoutParams.gravity = 51;
        this.mWindowManager.addView(this.background, layoutParams);
        this.background.setVisibility(View.GONE);
    }

    private void addFloatingWidgetView() {
        WindowManager.LayoutParams layoutParams;
        View inflate = this.inflater.inflate(R.layout.floating_widget_layout, (ViewGroup) null);
        this.mFloatingWidgetView = inflate;
        this.circleImageView = (CircleImageView) inflate.findViewById(R.id.dp);
        if (Build.VERSION.SDK_INT < 26) {
            layoutParams = new WindowManager.LayoutParams(-2, -2, 2003, 8, -3);
        } else {
            layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
        }
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.height = this.headSize;
        layoutParams.width = this.headSize;
        layoutParams.gravity = 51;
        this.mWindowManager.addView(this.mFloatingWidgetView, layoutParams);
        this.collapsedView = (RelativeLayout) this.mFloatingWidgetView.findViewById(R.id.collapse_view);
        this.countHolder = (LinearLayout) this.mFloatingWidgetView.findViewById(R.id.countHolder);
        this.count = (TextView) this.mFloatingWidgetView.findViewById(R.id.unseencount);
        setWidgetMargin(-1);
    }

    private void getWindowManagerDefaultDisplay() {
        if (Build.VERSION.SDK_INT >= 13) {
            this.mWindowManager.getDefaultDisplay().getSize(this.szWindow);
            return;
        }
        this.szWindow.set(this.mWindowManager.getDefaultDisplay().getWidth(), this.mWindowManager.getDefaultDisplay().getHeight());
    }

    private void implementTouchListenerToFloatingWidgetView() {
        this.mFloatingWidgetView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            boolean inBounded = false;
            boolean isLongClick = false;
            int remove_img_height = 0;
            int remove_img_width = 0;
            long time_end = 0;
            long time_start = 0;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (TMFloatingWidgetService.this.isViewCollapsed()) {
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) TMFloatingWidgetService.this.mFloatingWidgetView.getLayoutParams();
                    int rawX = (int) motionEvent.getRawX();
                    int rawY = (int) motionEvent.getRawY();
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        Log.i(TMFloatingWidgetService.this.tag, "MotionEvent.ACTION_DOWN");
                        this.time_start = System.currentTimeMillis();
                        int unused = TMFloatingWidgetService.this.x_init_cord = rawX;
                        int unused2 = TMFloatingWidgetService.this.y_init_cord = rawY;
                        int unused3 = TMFloatingWidgetService.this.x_init_margin = layoutParams.x;
                        int unused4 = TMFloatingWidgetService.this.y_init_margin = layoutParams.y;
                        TMFloatingWidgetService.this.movecount = 0;
                        TMFloatingWidgetService.this.setWidgetMargin(0);
                        return true;
                    } else if (action == 1) {
                        Log.i(TMFloatingWidgetService.this.tag, "MotionEvent.ACTION_Up");
                        this.isLongClick = false;
                        TMFloatingWidgetService.this.background.setVisibility(View.GONE);
                        if (this.inBounded) {
                            TMFloatingWidgetService.this.stopSelf();
                            this.inBounded = false;
                        } else {
                            int access$300 = rawY - TMFloatingWidgetService.this.y_init_cord;
                            if (Math.abs(rawX - TMFloatingWidgetService.this.x_init_cord) < 5 && Math.abs(access$300) < 5) {
                                long currentTimeMillis = System.currentTimeMillis();
                                this.time_end = currentTimeMillis;
                                if (currentTimeMillis - this.time_start < 300) {
                                    TMFloatingWidgetService.this.onFloatingWidgetClick();
                                }
                            }
                            int access$500 = TMFloatingWidgetService.this.y_init_margin + access$300;
                            int access$800 = TMFloatingWidgetService.this.getStatusBarHeight();
                            if (access$500 < 0) {
                                access$500 = 0;
                            } else if (TMFloatingWidgetService.this.mFloatingWidgetView.getHeight() + access$800 + access$500 > TMFloatingWidgetService.this.szWindow.y) {
                                access$500 = TMFloatingWidgetService.this.szWindow.y - (TMFloatingWidgetService.this.mFloatingWidgetView.getHeight() + access$800);
                            }
                            layoutParams.y = access$500;
                            this.inBounded = false;
                            if (TMFloatingWidgetService.this.movecount > 5 && TMFloatingWidgetService.this.isViewCollapsed()) {
                                TMFloatingWidgetService.this.resetPosition(rawX, rawY);
                            }
                            TMFloatingWidgetService.this.movecount = 0;
                            return true;
                        }
                    } else if (action == 2) {
                        Log.i(TMFloatingWidgetService.this.tag, "MotionEvent.ACTION_move");
                        int access$200 = rawX - TMFloatingWidgetService.this.x_init_cord;
                        int access$3002 = rawY - TMFloatingWidgetService.this.y_init_cord;
                        layoutParams.x = TMFloatingWidgetService.this.x_init_margin + access$200;
                        layoutParams.y = TMFloatingWidgetService.this.y_init_margin + access$3002;
                        TMFloatingWidgetService.this.mWindowManager.updateViewLayout(TMFloatingWidgetService.this.mFloatingWidgetView, layoutParams);
                        TMFloatingWidgetService.this.movecount++;
                        if (TMFloatingWidgetService.this.movecount > 10) {
                            TMFloatingWidgetService.this.onFloatingWidgetLongClick();
                        }
                        Log.i(TMFloatingWidgetService.this.tag, "move_count : " + TMFloatingWidgetService.this.movecount);
                        return true;
                    }
                }
                return false;
            }
        });
    }

   
    public void onFloatingWidgetLongClick() {
        this.background.setVisibility(View.VISIBLE);
    }

    
    public void setWidgetMargin(int i) {
        int i2 = this.headSize;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i2, i2);
        if (i == -1) {
            layoutParams.setMargins((-this.headSize) / 2, 0, 0, 0);
        } else if (i == 1) {
            int i3 = this.headSize;
            layoutParams.setMargins(i3 / 2, 0, (-i3) / 2, 0);
        } else {
            layoutParams.setMargins(0, 0, 0, 0);
        }
        this.collapsedView.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.countHolder.getLayoutParams();
        if (i == -1) {
            layoutParams2.setMargins(this.headSize / 5, 20, 0, 0);
        } else if (i == 1) {
            layoutParams2.setMargins(this.headSize / 2, 20, 0, 0);
        } else {
            layoutParams2.setMargins(0, 20, 0, 0);
        }
        this.countHolder.setLayoutParams(layoutParams2);
    }

   
    public void resetPosition(int i, int i2) {
        int i3 = this.szWindow.x / 2;
        if (i < i3 + 150 && i > i3 - 100 && i2 > this.szWindow.y - 500) {
            stopSelf();
        } else if (i <= i3) {
            this.isLeft = true;
            moveToLeft(i);
        } else {
            this.isLeft = false;
            moveToRight(i);
        }
    }

    private void moveToLeft(int i) {
        Log.i(this.tag, "moveToLeft");
        int i2 = this.szWindow.x;
        final int i3 = i;
        new CountDownTimer(500, 5) {
            WindowManager.LayoutParams mParams = ((WindowManager.LayoutParams) TMFloatingWidgetService.this.mFloatingWidgetView.getLayoutParams());

            public void onTick(long j) {
                WindowManager.LayoutParams layoutParams = this.mParams;
                int i = i3;
                layoutParams.x = 0 - ((int) (((long) (i * i)) * ((500 - j) / 5)));
                TMFloatingWidgetService.this.mWindowManager.updateViewLayout(TMFloatingWidgetService.this.mFloatingWidgetView, this.mParams);
            }

            public void onFinish() {
                this.mParams.x = 0;
                if (!TMFloatingWidgetService.this.isViewCollapsed()) {
                    this.mParams.y = 0;
                }
                TMFloatingWidgetService.this.setWidgetMargin(-1);
            }
        }.start();
    }

    private void moveToRight(int i) {
        Log.i(this.tag, "moveToRight");
        final WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mFloatingWidgetView.getLayoutParams();
        final int i2 = i;
        new CountDownTimer(500, 5) {
            public void onTick(long j) {

                long j2 = (long) TMFloatingWidgetService.this.szWindow.x;
                int i = i2;
                layoutParams.x = (int) ((j2 + (((long) (i * i)) * ((500 - j) / 5))) - ((long) TMFloatingWidgetService.this.mFloatingWidgetView.getWidth()));
                TMFloatingWidgetService.this.mWindowManager.updateViewLayout(TMFloatingWidgetService.this.mFloatingWidgetView, layoutParams);
            }

            public void onFinish() {
                layoutParams.x = TMFloatingWidgetService.this.szWindow.x - (TMFloatingWidgetService.this.headSize / 2);
                TMFloatingWidgetService.this.mWindowManager.updateViewLayout(TMFloatingWidgetService.this.mFloatingWidgetView, layoutParams);
                TMFloatingWidgetService.this.setWidgetMargin(1);
            }
        }.start();
    }

   
    public boolean isViewCollapsed() {
        View view = this.mFloatingWidgetView;
        return view == null || view.findViewById(R.id.collapse_view).getVisibility() == 0;
    }

   
    public int getStatusBarHeight() {
        return (int) Math.ceil((double) (getApplicationContext().getResources().getDisplayMetrics().density * 25.0f));
    }

   
    public void onFloatingWidgetClick() {
        if (isViewCollapsed()) {
            expand();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComplete(TMChatModel chatModel) {
        List<TMChatModel> list = this.viewList.chatModelList;
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                break;
            } else if (chatModel.getThreadId().equals(list.get(i).getThreadId())) {
                int i2 = i + 1;
                this.currentItem = i2;
                this.viewList.setCurrentItem(i2);
                break;
            } else {
                i++;
            }
        }
        if (i == list.size()) {
            this.viewList.addItem(chatModel);
            this.currentItem = this.viewList.size - 1;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TMStrModel str) {
        Log.i(this.tag, str.getData());
        if (str.getType() == 100) {
            if (str.getData().equals("KEYCODE_BACK") && !this.isKeyBoardOpen) {
                if (this.currentItem == 0) {
                    collapse();
                } else {
                    this.viewList.setCurrentItem(0);
                    this.currentItem = 0;
                }
            }
            if (str.getData().equals("keyBoard_open")) {
                this.isKeyBoardOpen = true;
            } else if (str.getData().equals("keyBoard_close")) {
                this.isKeyBoardOpen = false;
            }
            if (str.getData().equals("home") && !isViewCollapsed()) {
                collapse();
            }
            if (str.getData().equals("open_file_selection")) {
                this.fileSelectView.show();
            }
        }
    }

    
    public void expand() {
        this.countHolder.setVisibility(View.GONE);
        final WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mFloatingWidgetView.getLayoutParams();
        if (layoutParams.x >= 0) {
            this.position.x = layoutParams.x;
        } else {
            this.position.x = 0;
        }
        this.position.y = layoutParams.y;
        final int i = this.position.x;
        final int i2 = this.position.y;
        float f = (float) ((this.szWindow.x - 170) - i);
        float f2 = (float) (0 - i2);
        final int i3 = (int) ((f - f2) / 10.0f);
        String str = this.tag;
        Log.i(str, "total time : " + i3);
        float f3 = (float) i3;
        final float f4 = f / f3;
        final float f5 = f2 / f3;
        new CountDownTimer((long) i3, 3) {
            public void onTick(long j) {
                int i = i3 - ((int) j);
                float f = (float) i;
                layoutParams.x = (int) (((float) i) + (f4 * f));
                layoutParams.y = (int) (((float) i2) + (f5 * f));
                TMFloatingWidgetService.this.mWindowManager.updateViewLayout(TMFloatingWidgetService.this.mFloatingWidgetView, layoutParams);
            }

            public void onFinish() {
                TMFloatingWidgetService.this.collapsedView.setVisibility(View.GONE);
                TMFloatingWidgetService.this.viewList.show();
                TMFloatingWidgetService.this.viewList.setCurrentItem(TMFloatingWidgetService.this.currentItem);
                TMFloatingWidgetService.this.setWidgetMargin(0);
                TMFloatingWidgetService.this.background.setVisibility(View.VISIBLE);
                TMFloatingWidgetService.this.lottieAnimationView.setVisibility(View.GONE);
            }
        }.start();
    }

    
    public void collapse() {
        setCount();
        this.countHolder.setVisibility(View.VISIBLE);
        this.background.setVisibility(View.GONE);
        final WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mFloatingWidgetView.getLayoutParams();
        this.collapsedView.setVisibility(View.VISIBLE);
        this.viewList.hide();
        int i = this.position.x;
        int i2 = this.position.y;
        final int i3 = this.szWindow.x - 170;
        float f = (float) (i - i3);
        float f2 = (float) (i2 - 0);
        final int i4 = ((f < 0.0f ? (int) (f * -1.0f) : (int) f) + (f2 < 0.0f ? (int) (-1.0f * f2) : (int) f2)) / 10;
        float f3 = (float) i4;
        final float f4 = f / f3;
        final float f5 = f2 / f3;
        new CountDownTimer((long) i4, 1) {
            public void onTick(long j) {
                int i = i4 - ((int) j);
                float f = (float) i;
                layoutParams.x = (int) (((float) i3) + (f4 * f));
                layoutParams.y = (int) (((float) 0) + (f5 * f));
                TMFloatingWidgetService.this.mWindowManager.updateViewLayout(TMFloatingWidgetService.this.mFloatingWidgetView, layoutParams);
            }

            public void onFinish() {
                layoutParams.x = TMFloatingWidgetService.this.position.x;
                layoutParams.y = TMFloatingWidgetService.this.position.y;
                TMFloatingWidgetService.this.mWindowManager.updateViewLayout(TMFloatingWidgetService.this.mFloatingWidgetView, layoutParams);
                if (TMFloatingWidgetService.this.position.x < 10) {
                    TMFloatingWidgetService.this.setWidgetMargin(-1);
                } else {
                    TMFloatingWidgetService.this.setWidgetMargin(1);
                }
                TMFloatingWidgetService.this.lottieAnimationView.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsg(TMChatNotificationModel chatNotificationModel) {
        setCount();
    }

    
    public void setCount() {
        try {
            HashMap<String, Integer> unseenCount = new Pref(getApplicationContext()).getUnseenCount();
            int i = 0;
            for (String str : new ArrayList<>(unseenCount.keySet())) {
                i += (int) Double.parseDouble(unseenCount.get(str) + "");
            }
            String str2 = this.tag;
            Log.i(str2, "on set Count" + i);
            TextView textView = this.count;
            textView.setText(i + "");
            if (i == 0) {
                this.countHolder.setAlpha(0.0f);
            } else {
                this.countHolder.setAlpha(1.0f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        View view = this.mFloatingWidgetView;
        if (view != null) {
            this.mWindowManager.removeView(view);
        }
        View view2 = this.removeFloatingWidgetView;
        if (view2 != null) {
            this.mWindowManager.removeView(view2);
        }
        View view3 = this.background;
        if (view3 != null) {
            this.mWindowManager.removeView(view3);
        }
        EventBus.getDefault().unregister(this);
    }

    public void onRemoveItem(int i) {
        this.viewList.removeItem(i);
        if (this.currentItem == i) {
            this.currentItem = 0;
        }
    }

    public void onToach(int i) {
        if (this.currentItem != i) {
            this.currentItem = i;
            this.viewList.setCurrentItem(i);
            return;
        }
        collapse();
    }

    public void onMove() {
        this.viewList.hideBottom(this.currentItem);
        this.lottieAnimationView.setVisibility(View.VISIBLE);
    }

    public void onRelease() {
        this.viewList.setCurrentItem(this.currentItem);
        this.lottieAnimationView.setVisibility(View.GONE);
    }

    public void collapseFloatWidget() {
        collapse();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getWindowManagerDefaultDisplay();
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mFloatingWidgetView.getLayoutParams();
        if (configuration.orientation == 2) {
            stopSelf();
        } else if (configuration.orientation == 1 && layoutParams.x > this.szWindow.x) {
            resetPosition(this.szWindow.x, this.szWindow.y);
            this.mWindowManager.removeViewImmediate(this.background);
            addBackground();
        }
    }
}
