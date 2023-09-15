package com.yalantis.ucrop.util;

import android.opengl.EGL14;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class EglUtils {
    private static final String TAG = "EglUtils";

    private EglUtils() {
    }

    private static int getMaxTextureEgl10() {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[2]);
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr = new int[1];
        egl10.eglChooseConfig(eglGetDisplay, new int[]{12351, 12430, 12329, 0, 12339, 1, 12344}, eGLConfigArr, 1, iArr);
        if (iArr[0] == 0) {
            return 0;
        }
        EGLConfig eGLConfig = eGLConfigArr[0];
        EGLSurface eglCreatePbufferSurface = egl10.eglCreatePbufferSurface(eglGetDisplay, eGLConfig, new int[]{12375, 64, 12374, 64, 12344});
        EGLContext eglCreateContext = egl10.eglCreateContext(eglGetDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 1, 12344});
        egl10.eglMakeCurrent(eglGetDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, eglCreateContext);
        int[] iArr2 = new int[1];
        GLES10.glGetIntegerv(3379, iArr2, 0);
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eglGetDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        egl10.eglDestroySurface(eglGetDisplay, eglCreatePbufferSurface);
        egl10.eglDestroyContext(eglGetDisplay, eglCreateContext);
        egl10.eglTerminate(eglGetDisplay);
        return iArr2[0];
    }

    private static int getMaxTextureEgl14() {
        android.opengl.EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
        int[] iArr = new int[2];
        EGL14.eglInitialize(eglGetDisplay, iArr, 0, iArr, 1);
        android.opengl.EGLConfig[] eGLConfigArr = new android.opengl.EGLConfig[1];
        int[] iArr2 = new int[1];
        EGL14.eglChooseConfig(eglGetDisplay, new int[]{12351, 12430, 12329, 0, 12352, 4, 12339, 1, 12344}, 0, eGLConfigArr, 0, 1, iArr2, 0);
        if (iArr2[0] == 0) {
            return 0;
        }
        android.opengl.EGLConfig eGLConfig = eGLConfigArr[0];
        android.opengl.EGLSurface eglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(eglGetDisplay, eGLConfig, new int[]{12375, 64, 12374, 64, 12344}, 0);
        android.opengl.EGLContext eglCreateContext = EGL14.eglCreateContext(eglGetDisplay, eGLConfig, EGL14.EGL_NO_CONTEXT, new int[]{12440, 2, 12344}, 0);
        EGL14.eglMakeCurrent(eglGetDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, eglCreateContext);
        int[] iArr3 = new int[1];
        GLES20.glGetIntegerv(3379, iArr3, 0);
        android.opengl.EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
        EGL14.eglMakeCurrent(eglGetDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
        EGL14.eglDestroySurface(eglGetDisplay, eglCreatePbufferSurface);
        EGL14.eglDestroyContext(eglGetDisplay, eglCreateContext);
        EGL14.eglTerminate(eglGetDisplay);
        return iArr3[0];
    }

    public static int getMaxTextureSize() {
        try {
            return Build.VERSION.SDK_INT >= 17 ? getMaxTextureEgl14() : getMaxTextureEgl10();
        } catch (Exception e) {
            Log.d(TAG, "getMaxTextureSize: ", e);
            return 0;
        }
    }
}
