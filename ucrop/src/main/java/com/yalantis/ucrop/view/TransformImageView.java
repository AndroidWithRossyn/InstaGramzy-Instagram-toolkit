package com.yalantis.ucrop.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.util.FastBitmapDrawable;
import com.yalantis.ucrop.util.RectUtils;

public class TransformImageView extends ImageView {
    private static final int MATRIX_VALUES_COUNT = 9;
    private static final int RECT_CENTER_POINT_COORDS = 2;
    private static final int RECT_CORNER_POINTS_COORDS = 8;
    private static final String TAG = "TransformImageView";
    protected boolean mBitmapDecoded;
    protected boolean mBitmapLaidOut;
    protected final float[] mCurrentImageCenter;
    protected final float[] mCurrentImageCorners;
    protected Matrix mCurrentImageMatrix;
    public ExifInfo mExifInfo;
    public String mImageInputPath;
    public String mImageOutputPath;
    private float[] mInitialImageCenter;
    private float[] mInitialImageCorners;
    private final float[] mMatrixValues;
    private int mMaxBitmapSize;
    protected int mThisHeight;
    protected int mThisWidth;
    protected TransformImageListener mTransformImageListener;

    public interface TransformImageListener {
        void onFlip(float f);

        void onLoadComplete();

        void onLoadFailure(Exception exc);

        void onRotate(float f);

        void onScale(float f);
    }

    public TransformImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TransformImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TransformImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mCurrentImageCorners = new float[8];
        mCurrentImageCenter = new float[2];
        mMatrixValues = new float[9];
        mCurrentImageMatrix = new Matrix();
        mBitmapDecoded = false;
        mBitmapLaidOut = false;
        mMaxBitmapSize = 0;
        init();
    }

    private void updateCurrentImagePoints() {
        mCurrentImageMatrix.mapPoints(mCurrentImageCorners, mInitialImageCorners);
        mCurrentImageMatrix.mapPoints(mCurrentImageCenter, mInitialImageCenter);
    }

    public float getCurrentAngle() {
        return getMatrixAngle(mCurrentImageMatrix);
    }

    public float getCurrentScale() {
        Log.e("mCurrentImageMatrix", "" + mCurrentImageMatrix);
        return getMatrixScale(mCurrentImageMatrix);
    }

    public ExifInfo getExifInfo() {
        return mExifInfo;
    }

    public String getImageInputPath() {
        return mImageInputPath;
    }

    public String getImageOutputPath() {
        return mImageOutputPath;
    }

    public float getMatrixAngle(Matrix matrix) {
        return (float) (-(Math.atan2((double) getMatrixValue(matrix, 1), (double) getMatrixValue(matrix, 0)) * 57.29577951308232d));
    }

    public float getMatrixScale(Matrix matrix) {
        return (float) Math.sqrt(Math.pow((double) getMatrixValue(matrix, 0), 2.0d) + Math.pow((double) getMatrixValue(matrix, 3), 2.0d));
    }

    public float getMatrixValue(Matrix matrix, int i) {
        matrix.getValues(mMatrixValues);
        return mMatrixValues[i];
    }

    public int getMaxBitmapSize() {
        if (mMaxBitmapSize <= 0) {
            mMaxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(getContext());
        }
        return mMaxBitmapSize;
    }

    public Bitmap getViewBitmap() {
        if (getDrawable() == null || !(getDrawable() instanceof FastBitmapDrawable)) {
            return null;
        }
        return ((FastBitmapDrawable) getDrawable()).getBitmap();
    }

    public void init() {
        setScaleType(ScaleType.MATRIX);
    }

    public void onImageLaidOut() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            float intrinsicWidth = (float) drawable.getIntrinsicWidth();
            float intrinsicHeight = (float) drawable.getIntrinsicHeight();
            Log.d(TAG, String.format("Image size: [%d:%d]", new Object[]{Integer.valueOf((int) intrinsicWidth), Integer.valueOf((int) intrinsicHeight)}));
            RectF rectF = new RectF(0.0f, 0.0f, intrinsicWidth, intrinsicHeight);
            mInitialImageCorners = RectUtils.getCornersFromRect(rectF);
            mInitialImageCenter = RectUtils.getCenterFromRect(rectF);
            mBitmapLaidOut = true;
            TransformImageListener transformImageListener = mTransformImageListener;
            if (transformImageListener != null) {
                transformImageListener.onLoadComplete();
            }
        }
    }


    @Override
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z || (mBitmapDecoded && !mBitmapLaidOut)) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int height = getHeight() - getPaddingBottom();
            mThisWidth = (getWidth() - getPaddingRight()) - paddingLeft;
            mThisHeight = height - paddingTop;
            onImageLaidOut();
        }
    }

    public void postRotate(float f, float f2, float f3) {
        if (f != 0.0f) {
            mCurrentImageMatrix.postRotate(f, f2, f3);
            setImageMatrix(mCurrentImageMatrix);
            TransformImageListener transformImageListener = mTransformImageListener;
            if (transformImageListener != null) {
                transformImageListener.onRotate(getMatrixAngle(mCurrentImageMatrix));
            }
        }
    }

    public void postFlipHorizontal(float f, float f2, float f3, float f4) {
        mCurrentImageMatrix.postScale(f, f2, f3, f4);
        setImageMatrix(mCurrentImageMatrix);
        Log.e("mCurrentImageMatrix", "in postFlipHorizontal" + mCurrentImageMatrix);
    }

    public void postScale(float f, float f2, float f3) {
        if (f != 0.0f) {
            mCurrentImageMatrix.postScale(f, f, f2, f3);
            setImageMatrix(mCurrentImageMatrix);
            TransformImageListener transformImageListener = mTransformImageListener;
            if (transformImageListener != null) {
                transformImageListener.onScale(getMatrixScale(mCurrentImageMatrix));
            }
        }
    }

    public void postTranslate(float f, float f2) {
        if (f != 0.0f || f2 != 0.0f) {
            mCurrentImageMatrix.postTranslate(f, f2);
            setImageMatrix(mCurrentImageMatrix);
        }
    }

    public void printMatrix(String str, Matrix matrix) {
        float matrixValue = getMatrixValue(matrix, 2);
        float matrixValue2 = getMatrixValue(matrix, 5);
        float matrixScale = getMatrixScale(matrix);
        float matrixAngle = getMatrixAngle(matrix);
        Log.d(TAG, str + ": matrix: { x: " + matrixValue + ", y: " + matrixValue2 + ", scale: " + matrixScale + ", angle: " + matrixAngle + " }");
    }

    public void setImageBitmap(Bitmap bitmap) {
        setImageDrawable(new FastBitmapDrawable(bitmap));
    }

    public void setImageMatrix(Matrix matrix) {
        super.setImageMatrix(matrix);
        mCurrentImageMatrix.set(matrix);
        updateCurrentImagePoints();
    }

    public void setImageUri(Uri uri, Uri uri2) {
        int maxBitmapSize = getMaxBitmapSize();
        BitmapLoadUtils.decodeBitmapInBackground(getContext(), uri, uri2, maxBitmapSize, maxBitmapSize, new BitmapLoadCallback() {
            public void onBitmapLoaded(Bitmap bitmap, ExifInfo exifInfo, String str, String str2) {
                mImageInputPath = str;
                mImageOutputPath = str2;
                mExifInfo = exifInfo;
                TransformImageView transformImageView = TransformImageView.this;
                transformImageView.mBitmapDecoded = true;
                transformImageView.setImageBitmap(bitmap);
            }

            public void onFailure(Exception exc) {
                Log.e(TransformImageView.TAG, "onFailure: setImageUri", exc);
                if (mTransformImageListener != null) {
                    mTransformImageListener.onLoadFailure(exc);
                }
            }
        });
    }

    public void setMaxBitmapSize(int i) {
        mMaxBitmapSize = i;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType == ScaleType.MATRIX) {
            super.setScaleType(scaleType);
        } else {
            Log.w(TAG, "Invalid ScaleType. Only ScaleType.MATRIX can be used");
        }
    }

    public void setTransformImageListener(TransformImageListener transformImageListener) {
        mTransformImageListener = transformImageListener;
    }
}
