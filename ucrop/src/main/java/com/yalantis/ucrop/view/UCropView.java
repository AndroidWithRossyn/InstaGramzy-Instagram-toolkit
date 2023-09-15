package com.yalantis.ucrop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.yalantis.ucrop.R;
import com.yalantis.ucrop.callback.CropBoundsChangeListener;
import com.yalantis.ucrop.callback.OverlayViewChangeListener;

public class UCropView extends FrameLayout {
    public GestureCropImageView mGestureCropImageView;
    public final OverlayView mViewOverlay;

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public UCropView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public UCropView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.ucrop_view, this, true);
        this.mGestureCropImageView = (GestureCropImageView) findViewById(R.id.image_view_crop);
        OverlayView overlayView = (OverlayView) findViewById(R.id.view_overlay);
        this.mViewOverlay = overlayView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ucrop_UCropView);
        overlayView.processStyledAttributes(obtainStyledAttributes);
        this.mGestureCropImageView.processStyledAttributes(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        setListenersToViews();
    }

    private void setListenersToViews() {
        this.mGestureCropImageView.setCropBoundsChangeListener(new CropBoundsChangeListener() {
            public void onCropAspectRatioChanged(float f, int i, int i2) {
                UCropView.this.mViewOverlay.setTargetAspectRatio(f, i, i2);
            }
        });
        this.mViewOverlay.setOverlayViewChangeListener(new OverlayViewChangeListener() {
            public void onCropRectUpdated(RectF rectF) {
                UCropView.this.mGestureCropImageView.setCropRect(rectF);
            }
        });
    }

    public GestureCropImageView getCropImageView() {
        return this.mGestureCropImageView;
    }

    public OverlayView getOverlayView() {
        return this.mViewOverlay;
    }

    public void resetCropImageView() {
        removeView(this.mGestureCropImageView);
        this.mGestureCropImageView = new GestureCropImageView(getContext());
        setListenersToViews();
        this.mGestureCropImageView.setCropRect(getOverlayView().getCropViewRect());
        addView(this.mGestureCropImageView, 0);
    }
}
