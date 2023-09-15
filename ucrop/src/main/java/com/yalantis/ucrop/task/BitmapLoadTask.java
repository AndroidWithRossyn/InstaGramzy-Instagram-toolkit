package com.yalantis.ucrop.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.util.FileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;

public class BitmapLoadTask extends AsyncTask<Void, Void, BitmapLoadTask.BitmapWorkerResult> {
    private static final String TAG = "BitmapWorkerTask";
    private final BitmapLoadCallback mBitmapLoadCallback;
    private final Context mContext;
    private Uri mInputUri;
    private Uri mOutputUri;
    private final int mRequiredHeight;
    private final int mRequiredWidth;
    Throwable th;

    private void downloadFile(Uri uri, Uri uri2) {
    }

    public static class BitmapWorkerResult {
        Bitmap mBitmapResult;
        Exception mBitmapWorkerException;
        ExifInfo mExifInfo;

        public BitmapWorkerResult(Bitmap bitmap, ExifInfo exifInfo) {
            this.mBitmapResult = bitmap;
            this.mExifInfo = exifInfo;
        }

        public BitmapWorkerResult(Exception exc) {
            this.mBitmapWorkerException = exc;
        }
    }

    public BitmapLoadTask(Context context, Uri uri, Uri uri2, int i, int i2, BitmapLoadCallback bitmapLoadCallback) {
        this.mContext = context;
        this.mInputUri = uri;
        this.mOutputUri = uri2;
        this.mRequiredWidth = i;
        this.mRequiredHeight = i2;
        this.mBitmapLoadCallback = bitmapLoadCallback;
    }

    private void copyFile(Uri uri, Uri uri2) throws Throwable {
        FileOutputStream fileOutputStream;
        Log.d(TAG, "copyFile");
        Objects.requireNonNull(uri2, "Output Uri is null - cannot copy image");
        try {
            InputStream openInputStream = this.mContext.getContentResolver().openInputStream(uri);
            try {
                fileOutputStream = new FileOutputStream(new File(uri2.getPath()));
                if (openInputStream != null) {
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = openInputStream.read(bArr);
                            if (read > 0) {
                                fileOutputStream.write(bArr, 0, read);
                            } else {
                                BitmapLoadUtils.close(fileOutputStream);
                                BitmapLoadUtils.close(openInputStream);
                                this.mInputUri = this.mOutputUri;
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            this.th = th;
                            BitmapLoadUtils.close(fileOutputStream);
                            BitmapLoadUtils.close(openInputStream);
                            this.mInputUri = this.mOutputUri;
                            throw this.th;
                        } catch (Throwable th3) {
                            th = th3;
                            this.th = th;
                            BitmapLoadUtils.close(fileOutputStream);
                            BitmapLoadUtils.close((Closeable) null);
                            this.mInputUri = this.mOutputUri;
                            try {
                                throw this.th;
                            } catch (Throwable th4) {
                                th4.printStackTrace();
                            }
                        }
                    }
                } else {
                    throw new NullPointerException("InputStream for given input Uri is null");
                }
            } catch (Throwable th5) {
                th = th5;
                fileOutputStream = null;
                this.th = th;
                BitmapLoadUtils.close(fileOutputStream);
                BitmapLoadUtils.close(openInputStream);
                this.mInputUri = this.mOutputUri;
                throw this.th;
            }
        } catch (Throwable th6) {
            th = th6;
            fileOutputStream = null;
            this.th = th;
            BitmapLoadUtils.close(fileOutputStream);
            BitmapLoadUtils.close((Closeable) null);
            this.mInputUri = this.mOutputUri;
            throw this.th;
        }
    }

    private String getFilePath() {
        if (ContextCompat.checkSelfPermission(this.mContext, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return FileUtils.getPath(this.mContext, this.mInputUri);
        }
        return null;
    }

    private void processInputUri() {
        String scheme = this.mInputUri.getScheme();
        Log.d(TAG, "Uri scheme: " + scheme);
        if (!"http".equals(scheme) && !"https".equals(scheme)) {
            if ("content".equals(scheme)) {
                String filePath = getFilePath();
                if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
                    try {
                        copyFile(this.mInputUri, this.mOutputUri);
                    } catch (NullPointerException e) {
                        Log.e(TAG, "Copying failed", e);
                        throw e;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                } else {
                    this.mInputUri = Uri.fromFile(new File(filePath));
                }
            } else if (!"file".equals(scheme)) {
                Log.e(TAG, "Invalid Uri scheme " + scheme);
                throw new IllegalArgumentException("Invalid Uri scheme" + scheme);
            }
        }
    }

    public BitmapWorkerResult doInBackground(Void... voidArr) {
        if (this.mInputUri == null) {
            return new BitmapWorkerResult(new NullPointerException("Input Uri cannot be null"));
        }
        try {
            processInputUri();
            try {
                ParcelFileDescriptor openFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(this.mInputUri, "r");
                if (openFileDescriptor != null) {
                    FileDescriptor fileDescriptor = openFileDescriptor.getFileDescriptor();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFileDescriptor(fileDescriptor, (Rect) null, options);
                    if (options.outWidth != -1) {
                        if (options.outHeight != -1) {
                            options.inSampleSize = BitmapLoadUtils.calculateInSampleSize(options, this.mRequiredWidth, this.mRequiredHeight);
                            boolean z = false;
                            options.inJustDecodeBounds = false;
                            Bitmap bitmap = null;
                            while (!z) {
                                try {
                                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, (Rect) null, options);
                                    z = true;
                                } catch (OutOfMemoryError e) {
                                    Log.e(TAG, "doInBackground: BitmapFactory.decodeFileDescriptor: ", e);
                                    options.inSampleSize *= 2;
                                }
                            }
                            if (bitmap == null) {
                                return new BitmapWorkerResult(new IllegalArgumentException("Bitmap could not be decoded from the Uri: [" + this.mInputUri + "]"));
                            }
                            if (Build.VERSION.SDK_INT >= 16) {
                                BitmapLoadUtils.close(openFileDescriptor);
                            }
                            int exifOrientation = BitmapLoadUtils.getExifOrientation(this.mContext, this.mInputUri);
                            int exifToDegrees = BitmapLoadUtils.exifToDegrees(exifOrientation);
                            int exifToTranslation = BitmapLoadUtils.exifToTranslation(exifOrientation);
                            ExifInfo exifInfo = new ExifInfo(exifOrientation, exifToDegrees, exifToTranslation);
                            Matrix matrix = new Matrix();
                            if (exifToDegrees != 0) {
                                matrix.preRotate((float) exifToDegrees);
                            }
                            if (exifToTranslation != 1) {
                                matrix.postScale((float) exifToTranslation, 1.0f);
                            }
                            return !matrix.isIdentity() ? new BitmapWorkerResult(BitmapLoadUtils.transformBitmap(bitmap, matrix), exifInfo) : new BitmapWorkerResult(bitmap, exifInfo);
                        }
                    }
                    return new BitmapWorkerResult(new IllegalArgumentException("Bounds for bitmap could not be retrieved from the Uri: [" + this.mInputUri + "]"));
                }
                return new BitmapWorkerResult(new NullPointerException("ParcelFileDescriptor was null for given Uri: [" + this.mInputUri + "]"));
            } catch (FileNotFoundException e2) {
                return new BitmapWorkerResult(e2);
            }
        } catch (NullPointerException e3) {
            return new BitmapWorkerResult(e3);
        }
    }

    public void onPostExecute(BitmapWorkerResult bitmapWorkerResult) {
        String str;
        Exception exc = bitmapWorkerResult.mBitmapWorkerException;
        if (exc == null) {
            BitmapLoadCallback bitmapLoadCallback = this.mBitmapLoadCallback;
            Bitmap bitmap = bitmapWorkerResult.mBitmapResult;
            ExifInfo exifInfo = bitmapWorkerResult.mExifInfo;
            String path = this.mInputUri.getPath();
            Uri uri = this.mOutputUri;
            if (uri == null) {
                str = null;
            } else {
                str = uri.getPath();
            }
            bitmapLoadCallback.onBitmapLoaded(bitmap, exifInfo, path, str);
            return;
        }
        this.mBitmapLoadCallback.onFailure(exc);
    }
}
