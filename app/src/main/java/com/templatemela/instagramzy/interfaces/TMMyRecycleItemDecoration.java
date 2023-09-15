package com.templatemela.instagramzy.interfaces;

import android.graphics.Rect;
import android.net.sip.SipSession;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class TMMyRecycleItemDecoration extends RecyclerView.ItemDecoration {
    private int i;
    private int i2;
    private boolean z;

    public TMMyRecycleItemDecoration(int i3, int i4, boolean z2) {
        i = i3;
        i2 = i4;
        z = z2;
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, SipSession.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        int i3 = i;
        int i4 = childAdapterPosition % i3;
        if (z) {
            int i5 = i2;
            rect.left = i5 - ((i4 * i5) / i3);
            rect.right = ((i4 + 1) * i5) / i3;
            if (childAdapterPosition < i3) {
                rect.top = i5;
            }
            rect.bottom = i2;
            return;
        }
        int i6 = i2;
        rect.left = (i4 * i6) / i3;
        rect.right = i6 - (((i4 + 1) * i6) / i3);
        if (childAdapterPosition >= i3) {
            rect.top = i6;
        }
    }
}
