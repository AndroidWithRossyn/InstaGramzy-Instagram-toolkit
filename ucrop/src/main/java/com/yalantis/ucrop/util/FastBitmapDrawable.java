package com.yalantis.ucrop.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class FastBitmapDrawable extends Drawable {
    private int mAlpha = 255;
    private Bitmap mBitmap;
    private int mHeight;
    private final Paint mPaint = new Paint(2);
    private int mWidth;

    public int getOpacity() {
        return -3;
    }

    public FastBitmapDrawable(Bitmap bitmap) {
        setBitmap(bitmap);
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(this.mBitmap, (Rect) null, getBounds(), this.mPaint);
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    public int getMinimumHeight() {
        return this.mHeight;
    }

    public int getMinimumWidth() {
        return this.mWidth;
    }

    public void setAlpha(int i) {
        this.mAlpha = i;
        this.mPaint.setAlpha(i);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        if (bitmap != null) {
            this.mWidth = bitmap.getWidth();
            this.mHeight = this.mBitmap.getHeight();
            return;
        }
        this.mHeight = 0;
        this.mWidth = 0;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
    }
}
