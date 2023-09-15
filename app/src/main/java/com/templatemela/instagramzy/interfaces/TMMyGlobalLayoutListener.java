package com.templatemela.instagramzy.interfaces;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

public class TMMyGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    int Column_Count;
    int Row_Count;
    FrameLayout frameLayout;
    RecyclerView recyclerView;

    public TMMyGlobalLayoutListener(RecyclerView recyclerView2, FrameLayout frameLayout2, int i, int i2) {
        recyclerView = recyclerView2;
        frameLayout = frameLayout2;
        Row_Count = i2;
        Column_Count = i;
    }


    @Override
    public void onGlobalLayout() {
        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        int width = recyclerView.getWidth();
        int height = recyclerView.getHeight();
        float f = ((float) Column_Count) / ((float) Row_Count);
        int i = (int) (((float) width) / f);
        if (i > height) {
            layoutParams.width = width - (width - ((int) (((float) height) * f)));
        } else {
            layoutParams.height = height - (height - i);
        }
        recyclerView.requestLayout();
        frameLayout.setVisibility(View.GONE);
    }
}
