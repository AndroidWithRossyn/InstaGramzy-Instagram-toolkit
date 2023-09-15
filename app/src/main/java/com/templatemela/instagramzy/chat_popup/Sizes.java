package com.templatemela.instagramzy.chat_popup;

import android.content.Context;
import android.util.TypedValue;

public class Sizes {
    static Context context;

    public static void setContext(Context context2) {
        context = context2;
    }

    static int getPixel(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, context.getResources().getDisplayMetrics());
    }

    public static int getHeadSize() {
        return getPixel(60);
    }

    public static int getTriangleSize() {
        return getPixel(10);
    }

    public static int getHorizontalGap() {
        return getPixel(0);
    }

    public static int getVerticalGap() {
        return getPixel(2);
    }
}
