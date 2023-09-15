package com.yalantis.ucrop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yalantis.ucrop.R;
import com.yalantis.ucrop.callback.OverlayViewChangeListener;
import com.yalantis.ucrop.util.RectUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class OverlayView extends View {
    public static final boolean DEFAULT_CIRCLE_DIMMED_LAYER = false;
    public static final int DEFAULT_CROP_GRID_COLUMN_COUNT = 3;
    public static final int DEFAULT_CROP_GRID_ROW_COUNT = 1;
    public static final int DEFAULT_FREESTYLE_CROP_MODE = 0;
    public static final boolean DEFAULT_SHOW_CROP_FRAME = true;
    public static final boolean DEFAULT_SHOW_CROP_GRID = true;
    public static final int FREESTYLE_CROP_MODE_DISABLE = 0;
    public static final int FREESTYLE_CROP_MODE_ENABLE = 1;
    public static final int FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH = 2;
    private OverlayViewChangeListener mCallback;
    private boolean mCircleDimmedLayer;
    private Path mCircularPath;
    private Paint mCropFrameCornersPaint;
    private Paint mCropFramePaint;
    protected float[] mCropGridCenter;
    public int mCropGridColumnCount;
    protected float[] mCropGridCorners;
    private Paint mCropGridPaint;
    public int mCropGridRowCount;
    private int mCropRectCornerTouchAreaLineLength;
    private int mCropRectMinSize;
    private final RectF mCropViewRect;
    private int mCurrentTouchCornerIndex;
    private int mDimmedColor;
    private Paint mDimmedStrokePaint;
    private int mFreestyleCropMode;
    private float[] mGridPoints;
    private float mPreviousTouchX;
    private float mPreviousTouchY;
    private boolean mShouldSetupCropBounds;
    private boolean mShowCropFrame;
    private boolean mShowCropGrid;
    private float mTargetAspectRatio;
    private final RectF mTempRect;
    protected int mThisHeight;
    protected int mThisWidth;
    private int mTouchPointThreshold;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FreestyleMode {
    }

    public OverlayView(Context context) {
        this(context, (AttributeSet) null);
    }

    public OverlayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OverlayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCropViewRect = new RectF();
        this.mTempRect = new RectF();
        this.mCropGridRowCount = 1;
        this.mCropGridColumnCount = 3;
        this.mGridPoints = null;
        this.mCircularPath = new Path();
        this.mDimmedStrokePaint = new Paint(1);
        this.mCropGridPaint = new Paint(1);
        this.mCropFramePaint = new Paint(1);
        this.mCropFrameCornersPaint = new Paint(1);
        this.mFreestyleCropMode = 0;
        this.mPreviousTouchX = -1.0f;
        this.mPreviousTouchY = -1.0f;
        this.mCurrentTouchCornerIndex = -1;
        this.mTouchPointThreshold = getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_rect_corner_touch_threshold);
        this.mCropRectMinSize = getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_rect_min_size);
        this.mCropRectCornerTouchAreaLineLength = getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_rect_corner_touch_area_line_length);
        init();
    }

    private int getCurrentTouchIndex(float f, float f2) {
        double d = (double) this.mTouchPointThreshold;
        int i = -1;
        for (int i2 = 0; i2 < 8; i2 += 2) {
            double sqrt = Math.sqrt(Math.pow((double) (f - this.mCropGridCorners[i2]), 2.0d) + Math.pow((double) (f2 - this.mCropGridCorners[i2 + 1]), 2.0d));
            if (sqrt < d) {
                i = i2 / 2;
                d = sqrt;
            }
        }
        if (this.mFreestyleCropMode != 1 || i >= 0 || !this.mCropViewRect.contains(f, f2)) {
            return i;
        }
        return 4;
    }

    private void initCropFrameStyle(TypedArray typedArray) {
        int dimensionPixelSize = typedArray.getDimensionPixelSize(R.styleable.ucrop_UCropView_ucrop_frame_stroke_size, getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_frame_stoke_width));
        int color = typedArray.getColor(R.styleable.ucrop_UCropView_ucrop_frame_color, getResources().getColor(R.color.ucrop_color_default_crop_frame));
        this.mCropFramePaint.setStrokeWidth((float) dimensionPixelSize);
        this.mCropFramePaint.setColor(color);
        this.mCropFramePaint.setStyle(Paint.Style.STROKE);
        this.mCropFrameCornersPaint.setStrokeWidth((float) (dimensionPixelSize * 3));
        this.mCropFrameCornersPaint.setColor(color);
        this.mCropFrameCornersPaint.setStyle(Paint.Style.STROKE);
    }

    private void initCropGridStyle(TypedArray typedArray) {
        int dimensionPixelSize = typedArray.getDimensionPixelSize(R.styleable.ucrop_UCropView_ucrop_grid_stroke_size, getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_grid_stoke_width));
        int color = typedArray.getColor(R.styleable.ucrop_UCropView_ucrop_grid_color, getResources().getColor(R.color.ucrop_color_default_crop_grid));
        this.mCropGridPaint.setStrokeWidth((float) dimensionPixelSize);
        this.mCropGridPaint.setColor(color);
        this.mCropGridRowCount = typedArray.getInt(R.styleable.ucrop_UCropView_ucrop_grid_row_count, 1);
        this.mCropGridColumnCount = typedArray.getInt(R.styleable.ucrop_UCropView_ucrop_grid_column_count, 3);
    }

    private void updateCropViewRect(float f, float f2) {
        this.mTempRect.set(this.mCropViewRect);
        int i = this.mCurrentTouchCornerIndex;
        boolean z = true;
        if (i == 0) {
            RectF rectF = this.mTempRect;
            RectF rectF2 = this.mCropViewRect;
            rectF.set(f, f2, rectF2.right, rectF2.bottom);
        } else if (i == 1) {
            RectF rectF3 = this.mTempRect;
            RectF rectF4 = this.mCropViewRect;
            rectF3.set(rectF4.left, f2, f, rectF4.bottom);
        } else if (i == 2) {
            RectF rectF5 = this.mTempRect;
            RectF rectF6 = this.mCropViewRect;
            rectF5.set(rectF6.left, rectF6.top, f, f2);
        } else if (i == 3) {
            RectF rectF7 = this.mTempRect;
            RectF rectF8 = this.mCropViewRect;
            rectF7.set(f, rectF8.top, rectF8.right, f2);
        } else if (i == 4) {
            this.mTempRect.offset(f - this.mPreviousTouchX, f2 - this.mPreviousTouchY);
            if (this.mTempRect.left > ((float) getLeft()) && this.mTempRect.top > ((float) getTop()) && this.mTempRect.right < ((float) getRight()) && this.mTempRect.bottom < ((float) getBottom())) {
                this.mCropViewRect.set(this.mTempRect);
                updateGridPoints();
                postInvalidate();
                return;
            }
            return;
        }
        boolean z2 = this.mTempRect.height() >= ((float) this.mCropRectMinSize);
        if (this.mTempRect.width() < ((float) this.mCropRectMinSize)) {
            z = false;
        }
        RectF rectF9 = this.mCropViewRect;
        rectF9.set(z ? this.mTempRect.left : rectF9.left, (z2 ? this.mTempRect : this.mCropViewRect).top, (z ? this.mTempRect : this.mCropViewRect).right, (z2 ? this.mTempRect : this.mCropViewRect).bottom);
        if (z2 || z) {
            updateGridPoints();
            postInvalidate();
        }
    }

    private void updateGridPoints() {
        this.mCropGridCorners = RectUtils.getCornersFromRect(this.mCropViewRect);
        this.mCropGridCenter = RectUtils.getCenterFromRect(this.mCropViewRect);
        this.mGridPoints = null;
        this.mCircularPath.reset();
        this.mCircularPath.addCircle(this.mCropViewRect.centerX(), this.mCropViewRect.centerY(), Math.min(this.mCropViewRect.width(), this.mCropViewRect.height()) / 2.0f, Path.Direction.CW);
    }

    public void drawCropGrid(Canvas canvas) {
        if (this.mShowCropGrid) {
            if (this.mGridPoints == null && !this.mCropViewRect.isEmpty()) {
                if (this.mCropGridRowCount < 0) {
                    this.mCropGridRowCount = 0;
                }
                if (this.mCropGridColumnCount == 0) {
                    this.mCropGridColumnCount = 3;
                }
                this.mGridPoints = new float[((this.mCropGridRowCount * 4) + (this.mCropGridColumnCount * 4))];
                int i = 0;
                for (int i2 = 0; i2 < this.mCropGridRowCount; i2++) {
                    float[] fArr = this.mGridPoints;
                    int i3 = i + 1;
                    RectF rectF = this.mCropViewRect;
                    fArr[i] = rectF.left;
                    int i4 = i3 + 1;
                    float f = ((float) i2) + 1.0f;
                    float height = rectF.height() * (f / ((float) (this.mCropGridRowCount + 1)));
                    RectF rectF2 = this.mCropViewRect;
                    fArr[i3] = height + rectF2.top;
                    float[] fArr2 = this.mGridPoints;
                    int i5 = i4 + 1;
                    fArr2[i4] = rectF2.right;
                    i = i5 + 1;
                    fArr2[i5] = (rectF2.height() * (f / ((float) (this.mCropGridRowCount + 1)))) + this.mCropViewRect.top;
                }
                for (int i6 = 0; i6 < this.mCropGridColumnCount; i6++) {
                    float[] fArr3 = this.mGridPoints;
                    int i7 = i + 1;
                    float f2 = ((float) i6) + 1.0f;
                    float width = this.mCropViewRect.width() * (f2 / ((float) (this.mCropGridColumnCount + 1)));
                    RectF rectF3 = this.mCropViewRect;
                    fArr3[i] = width + rectF3.left;
                    float[] fArr4 = this.mGridPoints;
                    int i8 = i7 + 1;
                    fArr4[i7] = rectF3.top;
                    int i9 = i8 + 1;
                    float width2 = rectF3.width() * (f2 / ((float) (this.mCropGridColumnCount + 1)));
                    RectF rectF4 = this.mCropViewRect;
                    fArr4[i8] = width2 + rectF4.left;
                    i = i9 + 1;
                    this.mGridPoints[i9] = rectF4.bottom;
                }
            }
            float[] fArr5 = this.mGridPoints;
            if (fArr5 != null) {
                canvas.drawLines(fArr5, this.mCropGridPaint);
            }
        }
        if (this.mShowCropFrame) {
            canvas.drawRect(this.mCropViewRect, this.mCropFramePaint);
        }
        if (this.mFreestyleCropMode != 0) {
            canvas.save();
            this.mTempRect.set(this.mCropViewRect);
            RectF rectF5 = this.mTempRect;
            int i10 = this.mCropRectCornerTouchAreaLineLength;
            rectF5.inset((float) i10, (float) (-i10));
            canvas.clipRect(this.mTempRect, Region.Op.DIFFERENCE);
            this.mTempRect.set(this.mCropViewRect);
            RectF rectF6 = this.mTempRect;
            int i11 = this.mCropRectCornerTouchAreaLineLength;
            rectF6.inset((float) (-i11), (float) i11);
            canvas.clipRect(this.mTempRect, Region.Op.DIFFERENCE);
            canvas.drawRect(this.mCropViewRect, this.mCropFrameCornersPaint);
            canvas.restore();
        }
    }

    public void drawDimmedLayer(Canvas canvas) {
        canvas.save();
        if (this.mCircleDimmedLayer) {
            canvas.clipPath(this.mCircularPath, Region.Op.DIFFERENCE);
        } else {
            canvas.clipRect(this.mCropViewRect, Region.Op.DIFFERENCE);
        }
        canvas.drawColor(this.mDimmedColor);
        canvas.restore();
        if (this.mCircleDimmedLayer) {
            canvas.drawCircle(this.mCropViewRect.centerX(), this.mCropViewRect.centerY(), Math.min(this.mCropViewRect.width(), this.mCropViewRect.height()) / 2.0f, this.mDimmedStrokePaint);
        }
    }

    public RectF getCropViewRect() {
        return this.mCropViewRect;
    }

    public int getFreestyleCropMode() {
        return this.mFreestyleCropMode;
    }

    public OverlayViewChangeListener getOverlayViewChangeListener() {
        return this.mCallback;
    }

    public void init() {
        int i = Build.VERSION.SDK_INT;
        if (i < 18 && i >= 11) {
            setLayerType(1, (Paint) null);
        }
    }

    @Deprecated
    public boolean isFreestyleCropEnabled() {
        return this.mFreestyleCropMode == 1;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDimmedLayer(canvas);
        drawCropGrid(canvas);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int height = getHeight() - getPaddingBottom();
            this.mThisWidth = (getWidth() - getPaddingRight()) - paddingLeft;
            this.mThisHeight = height - paddingTop;
            if (this.mShouldSetupCropBounds) {
                this.mShouldSetupCropBounds = false;
                setTargetAspectRatio(this.mTargetAspectRatio, this.mCropGridColumnCount, this.mCropGridRowCount);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (!this.mCropViewRect.isEmpty() && this.mFreestyleCropMode != 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if ((motionEvent.getAction() & 255) == 0) {
                int currentTouchIndex = getCurrentTouchIndex(x, y);
                this.mCurrentTouchCornerIndex = currentTouchIndex;
                if (currentTouchIndex != -1) {
                    z = true;
                }
                if (!z) {
                    this.mPreviousTouchX = -1.0f;
                    this.mPreviousTouchY = -1.0f;
                } else if (this.mPreviousTouchX < 0.0f) {
                    this.mPreviousTouchX = x;
                    this.mPreviousTouchY = y;
                }
                return z;
            } else if ((motionEvent.getAction() & 255) == 2 && motionEvent.getPointerCount() == 1 && this.mCurrentTouchCornerIndex != -1) {
                float min = Math.min(Math.max(x, (float) getPaddingLeft()), (float) (getWidth() - getPaddingRight()));
                float min2 = Math.min(Math.max(y, (float) getPaddingTop()), (float) (getHeight() - getPaddingBottom()));
                updateCropViewRect(min, min2);
                this.mPreviousTouchX = min;
                this.mPreviousTouchY = min2;
                return true;
            } else if ((motionEvent.getAction() & 255) == 1) {
                this.mPreviousTouchX = -1.0f;
                this.mPreviousTouchY = -1.0f;
                this.mCurrentTouchCornerIndex = -1;
                OverlayViewChangeListener overlayViewChangeListener = this.mCallback;
                if (overlayViewChangeListener != null) {
                    overlayViewChangeListener.onCropRectUpdated(this.mCropViewRect);
                }
            }
        }
        return false;
    }

    public void processStyledAttributes(TypedArray typedArray) {
        this.mCircleDimmedLayer = typedArray.getBoolean(R.styleable.ucrop_UCropView_ucrop_circle_dimmed_layer, false);
        int color = typedArray.getColor(R.styleable.ucrop_UCropView_ucrop_dimmed_color, getResources().getColor(R.color.ucrop_color_default_dimmed));
        this.mDimmedColor = color;
        this.mDimmedStrokePaint.setColor(color);
        this.mDimmedStrokePaint.setStyle(Paint.Style.STROKE);
        this.mDimmedStrokePaint.setStrokeWidth(1.0f);
        initCropFrameStyle(typedArray);
        this.mShowCropFrame = typedArray.getBoolean(R.styleable.ucrop_UCropView_ucrop_show_frame, true);
        initCropGridStyle(typedArray);
        this.mShowCropGrid = typedArray.getBoolean(R.styleable.ucrop_UCropView_ucrop_show_grid, true);
    }

    public void setCircleDimmedLayer(boolean z) {
        this.mCircleDimmedLayer = z;
    }

    public void setCropFrameColor(int i) {
        this.mCropFramePaint.setColor(i);
    }

    public void setCropFrameStrokeWidth(int i) {
        this.mCropFramePaint.setStrokeWidth((float) i);
    }

    public void setCropGridColor(int i) {
        this.mCropGridPaint.setColor(i);
    }

    public void setCropGridColumnCount(int i) {
        this.mCropGridColumnCount = i;
        this.mGridPoints = null;
    }

    public void setCropGridRowCount(int i) {
        this.mCropGridRowCount = i;
        this.mGridPoints = null;
    }

    public void setCropGridStrokeWidth(int i) {
        this.mCropGridPaint.setStrokeWidth((float) i);
    }

    public void setDimmedColor(int i) {
        this.mDimmedColor = i;
    }

    @Deprecated
    public void setFreestyleCropEnabled(boolean z) {
        this.mFreestyleCropMode = z ? 1 : 0;
    }

    public void setFreestyleCropMode(int i) {
        this.mFreestyleCropMode = i;
        postInvalidate();
    }

    public void setOverlayViewChangeListener(OverlayViewChangeListener overlayViewChangeListener) {
        this.mCallback = overlayViewChangeListener;
    }

    public void setShowCropFrame(boolean z) {
        this.mShowCropFrame = z;
    }

    public void setShowCropGrid(boolean z) {
        this.mShowCropGrid = z;
    }

    public void setTargetAspectRatio(float f, int i, int i2) {
        this.mTargetAspectRatio = f;
        this.mCropGridRowCount = i2 - 1;
        this.mCropGridColumnCount = i - 1;
        if (this.mThisWidth > 0) {
            setupCropBounds();
            postInvalidate();
            return;
        }
        this.mShouldSetupCropBounds = true;
    }

    public void setupCropBounds() {
        int i = this.mThisWidth;
        float f = this.mTargetAspectRatio;
        int i2 = (int) (((float) i) / f);
        int i3 = this.mThisHeight;
        if (i2 > i3) {
            int i4 = (int) (((float) i3) * f);
            int i5 = (i - i4) / 2;
            this.mCropViewRect.set((float) (getPaddingLeft() + i5), (float) getPaddingTop(), (float) (getPaddingLeft() + i4 + i5), (float) (getPaddingTop() + this.mThisHeight));
        } else {
            int i6 = (i3 - i2) / 2;
            this.mCropViewRect.set((float) getPaddingLeft(), (float) (getPaddingTop() + i6), (float) (getPaddingLeft() + this.mThisWidth), (float) (getPaddingTop() + i2 + i6));
        }
        OverlayViewChangeListener overlayViewChangeListener = this.mCallback;
        if (overlayViewChangeListener != null) {
            overlayViewChangeListener.onCropRectUpdated(this.mCropViewRect);
            Log.e("MYRATIO", "X : " + this.mCropViewRect.width() + "Y :" + this.mCropViewRect.height());
        }
        updateGridPoints();
    }
}
