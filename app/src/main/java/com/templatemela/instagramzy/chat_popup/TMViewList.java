package com.templatemela.instagramzy.chat_popup;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMChatModel;
import com.templatemela.instagramzy.models.TMStrModel;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class TMViewList extends FrameLayout {
    long a = 0;
    private View backView;
    public List<TMChatModel> chatModelList;
    Context context;
    int currentIndex = 0;
    List<TMExpandViewItem> expandViewItems;
   
    public View frame;
    FrameLayout frameLayout;
    int headSize = 180;
    int horizontalGap = 20;
    LayoutInflater inflater;
    public boolean isKeyboardShowing = false;
    int screenHeight = 0;
    private View selectionTriangle;
    int size = 0;
    private Point szWindow = new Point();
    String tag = "ViewList";
    int triangleHeaght = 30;
    int verticalGap = 20;
    WindowManager windowManager;

    public TMViewList(Context context2, WindowManager windowManager2, LayoutInflater layoutInflater) {
        super(context2);
        this.context = context2;
        Sizes.setContext(context2);
        this.headSize = Sizes.getHeadSize();
        this.horizontalGap = Sizes.getHorizontalGap();
        this.verticalGap = Sizes.getVerticalGap();
        this.triangleHeaght = Sizes.getTriangleSize();
        this.windowManager = windowManager2;
        this.inflater = layoutInflater;
        getWindowManagerDefaultDisplay();
        this.expandViewItems = new ArrayList();
        this.chatModelList = new ArrayList();
        addFrame(layoutInflater);
        addBackGround(layoutInflater);
        addSelectionView();
    }

    private void addFrame(LayoutInflater layoutInflater) {
        WindowManager.LayoutParams layoutParams;
        View inflate = layoutInflater.inflate(R.layout.expad_chat_head, this, true);
        this.frame = inflate;
        this.frameLayout = (FrameLayout) inflate.findViewById(R.id.frame);
        if (Build.VERSION.SDK_INT < 26) {
            layoutParams = new WindowManager.LayoutParams(-2, -2, 2003, 32, -3);
        } else {
            layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 32, -3);
        }
        layoutParams.gravity = 51;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = this.szWindow.x;
        layoutParams.height = this.szWindow.y;
        this.windowManager.addView(this.frame, layoutParams);
        this.frame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                Rect rect = new Rect();
                TMViewList.this.frame.getWindowVisibleDisplayFrame(rect);
                if (TMViewList.this.screenHeight == 0) {
                    TMViewList.this.screenHeight = rect.bottom;
                }
                int i = TMViewList.this.screenHeight - rect.bottom;
                String str = TMViewList.this.tag;
                Log.d(str, "keypadHeight = " + i);
                if (((double) i) > ((double) TMViewList.this.screenHeight) * 0.05d) {
                    if (!TMViewList.this.isKeyboardShowing) {
                        TMViewList.this.isKeyboardShowing = true;
                    }
                    EventBus.getDefault().post(new TMStrModel("keyBoard_open", 100));
                } else if (TMViewList.this.isKeyboardShowing) {
                    TMViewList.this.isKeyboardShowing = false;
                    EventBus.getDefault().post(new TMStrModel("keyBoard_close", 100));
                }
            }
        });
    }

    private void addBackGround(LayoutInflater layoutInflater) {
        View inflate = layoutInflater.inflate(R.layout.back, (ViewGroup) null);
        this.backView = inflate;
        this.frameLayout.addView(inflate);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.szWindow.x, this.szWindow.y - (this.headSize + this.triangleHeaght));
        layoutParams.leftMargin = 0;
        layoutParams.topMargin = this.headSize + this.triangleHeaght;
        this.backView.setLayoutParams(layoutParams);
    }

    private void getWindowManagerDefaultDisplay() {
        if (Build.VERSION.SDK_INT >= 13) {
            this.windowManager.getDefaultDisplay().getSize(this.szWindow);
            return;
        }
        this.szWindow.set(this.windowManager.getDefaultDisplay().getWidth(), this.windowManager.getDefaultDisplay().getHeight());
    }

    private void addSelectionView() {
        View inflate = this.inflater.inflate(R.layout.selection_triangle, (ViewGroup) null);
        this.selectionTriangle = inflate;
        this.frameLayout.addView(inflate);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(Sizes.getTriangleSize(), Sizes.getTriangleSize());
        layoutParams.rightMargin = this.szWindow.x - (((this.headSize / 2) + (this.triangleHeaght / 2)) + (this.horizontalGap / 2));
        layoutParams.topMargin = this.headSize + this.verticalGap;
        this.selectionTriangle.setLayoutParams(layoutParams);
    }

    private void setSelectionTriangle(int i) {
        this.currentIndex = i;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.selectionTriangle.getLayoutParams();
        layoutParams.topMargin = this.headSize + this.verticalGap;
        layoutParams.leftMargin = ((this.szWindow.x - (((this.headSize / 2) + (this.triangleHeaght / 2)) + Sizes.getHorizontalGap())) - (i * (this.headSize + Sizes.getHorizontalGap()))) - 3;
        this.selectionTriangle.setLayoutParams(layoutParams);
        this.selectionTriangle.setVisibility(View.VISIBLE);
        Log.i("triangle height", layoutParams.height + "");
    }

    
    public void addItem() {
        if (this.size < 4) {
            this.expandViewItems.add(new TMExpandViewItem(this.context, this.szWindow, this.frameLayout, this.size, (TMChatModel) null));
            this.size++;
        } else {
            this.expandViewItems.get(1).removeToAddNew();
            this.expandViewItems.remove(1);
            this.expandViewItems.get(1).setIndex(1);
            this.expandViewItems.get(2).setIndex(2);
            this.expandViewItems.add(new TMExpandViewItem(this.context, this.szWindow, this.frameLayout, 3, (TMChatModel) null));
        }
        setCurrentItem(this.expandViewItems.size() - 1);
    }

    
    public void addItem(TMChatModel chatModel) {
        if (this.size < 4) {
            this.expandViewItems.add(new TMExpandViewItem(this.context, this.szWindow, this.frameLayout, this.size, chatModel));
            this.chatModelList.add(chatModel);
            this.size++;
        } else {
            this.expandViewItems.get(1).removeToAddNew();
            this.expandViewItems.remove(1);
            this.expandViewItems.get(1).setIndex(1);
            this.expandViewItems.get(2).setIndex(2);
            this.expandViewItems.add(new TMExpandViewItem(this.context, this.szWindow, this.frameLayout, 3, chatModel));
            this.chatModelList.remove(0);
            this.chatModelList.add(chatModel);
        }
        setCurrentItem(this.expandViewItems.size() - 1);
    }

    public void removeItem(int i) {
        this.expandViewItems.remove(i);
        this.chatModelList.remove(i - 1);
        for (int i2 = 0; i2 < this.expandViewItems.size(); i2++) {
            this.expandViewItems.get(i2).setIndex(i2);
        }
        int i3 = this.currentIndex;
        if (i3 == i) {
            setCurrentItem(0);
        } else if (i3 > i) {
            setCurrentItem(i3 - 1);
        }
        this.size--;
    }

    public void hide() {
        this.frame.setVisibility(View.GONE);
        this.selectionTriangle.setVisibility(View.GONE);
        for (int i = 0; i < this.expandViewItems.size(); i++) {
            this.expandViewItems.get(i).hide();
        }
    }

    public void show() {
        this.frame.setVisibility(View.VISIBLE);
        this.selectionTriangle.setVisibility(View.VISIBLE);
        for (int i = 0; i < this.expandViewItems.size(); i++) {
            this.expandViewItems.get(i).show();
        }
    }

    public void setCurrentItem(int i) {
        if (i <= this.size - 1) {
            this.selectionTriangle.setVisibility(View.VISIBLE);
            for (int i2 = 0; i2 < this.expandViewItems.size(); i2++) {
                this.expandViewItems.get(i2).setCurrentItem(i);
            }
            this.currentIndex = i;
            setSelectionTriangle(i);
            this.backView.setVisibility(View.VISIBLE);
        }
    }

    public void hideBottom(int i) {
        this.backView.setVisibility(View.GONE);
        this.selectionTriangle.setVisibility(View.GONE);
        for (int i2 = 0; i2 < this.expandViewItems.size(); i2++) {
            this.expandViewItems.get(i2).hideBottom(i);
        }
    }

    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if (122 == keyEvent.getKeyCode()) {
            String str = this.tag;
            Log.i(str, keyEvent.getKeyCode() + "home");
        }
        if (4 == keyEvent.getKeyCode() && System.currentTimeMillis() - this.a > 600) {
            this.a = System.currentTimeMillis();
            Log.i(this.tag, "on back");
            EventBus.getDefault().post(new TMStrModel("KEYCODE_BACK", 100));
        }
        return super.dispatchKeyEventPreIme(keyEvent);
    }
}
