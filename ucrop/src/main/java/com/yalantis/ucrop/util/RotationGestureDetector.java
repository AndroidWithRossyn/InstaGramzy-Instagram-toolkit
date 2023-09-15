package com.yalantis.ucrop.util;

import android.view.MotionEvent;

public class RotationGestureDetector {
    private static final int INVALID_POINTER_INDEX = -1;
    private float f14181fX;
    private float f14182fY;
    private float f14183sX;
    private float f14184sY;
    private float mAngle;
    private boolean mIsFirstTouch;
    private OnRotationGestureListener mListener;
    private int mPointerIndex1 = -1;
    private int mPointerIndex2 = -1;

    public interface OnRotationGestureListener {
        boolean onRotation(RotationGestureDetector rotationGestureDetector);
    }

    public static class SimpleOnRotationGestureListener implements OnRotationGestureListener {
        public boolean onRotation(RotationGestureDetector rotationGestureDetector) {
            return false;
        }
    }

    public RotationGestureDetector(OnRotationGestureListener onRotationGestureListener) {
        this.mListener = onRotationGestureListener;
    }

    private float calculateAngleBetweenLines(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        return calculateAngleDelta((float) Math.toDegrees((double) ((float) Math.atan2((double) (f2 - f4), (double) (f - f3)))), (float) Math.toDegrees((double) ((float) Math.atan2((double) (f6 - f8), (double) (f5 - f7)))));
    }

    private float calculateAngleDelta(float f, float f2) {
        float f3 = (f2 % 360.0f) - (f % 360.0f);
        this.mAngle = f3;
        if (f3 < -180.0f) {
            float f4 = f3 + 360.0f;
            this.mAngle = f4;
            return f4;
        }
        int i = (f3 > 180.0f ? 1 : (f3 == 180.0f ? 0 : -1));
        return f3;
    }

    public float getAngle() {
        return this.mAngle;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                this.mPointerIndex1 = -1;
            } else if (actionMasked != 2) {
                if (actionMasked == 5) {
                    this.f14181fX = motionEvent.getX();
                    this.f14182fY = motionEvent.getY();
                    this.mPointerIndex2 = motionEvent2.findPointerIndex(motionEvent2.getPointerId(motionEvent.getActionIndex()));
                } else if (actionMasked == 6) {
                    this.mPointerIndex2 = -1;
                }
            } else if (!(this.mPointerIndex1 == -1 || this.mPointerIndex2 == -1 || motionEvent.getPointerCount() <= this.mPointerIndex2)) {
                float x = motionEvent2.getX(this.mPointerIndex1);
                float y = motionEvent2.getY(this.mPointerIndex1);
                float x2 = motionEvent2.getX(this.mPointerIndex2);
                float y2 = motionEvent2.getY(this.mPointerIndex2);
                if (this.mIsFirstTouch) {
                    this.mAngle = 0.0f;
                    this.mIsFirstTouch = false;
                } else {
                    calculateAngleBetweenLines(this.f14181fX, this.f14182fY, this.f14183sX, this.f14184sY, x2, y2, x, y);
                }
                OnRotationGestureListener onRotationGestureListener = this.mListener;
                if (onRotationGestureListener != null) {
                    onRotationGestureListener.onRotation(this);
                }
                this.f14181fX = x2;
                this.f14182fY = y2;
                this.f14183sX = x;
                this.f14184sY = y;
            }
            return true;
        }
        this.f14183sX = motionEvent.getX();
        this.f14184sY = motionEvent.getY();
        this.mPointerIndex1 = motionEvent2.findPointerIndex(motionEvent2.getPointerId(0));
        this.mAngle = 0.0f;
        this.mIsFirstTouch = true;
        return true;
    }
}
