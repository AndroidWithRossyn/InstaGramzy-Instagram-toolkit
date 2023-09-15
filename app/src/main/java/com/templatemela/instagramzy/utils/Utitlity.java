package com.templatemela.instagramzy.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import com.templatemela.instagramzy.R;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class Utitlity {

    public static  String SQUARE_IMAGE_PATH=Environment.getExternalStorageDirectory().toString() + File.separator+Environment.DIRECTORY_PICTURES+File.separator + "Instagramzy"+File.separator+ "Square Image";
    public static  String TEMP_PATH=Environment.getExternalStorageDirectory().toString() + File.separator+Environment.DIRECTORY_PICTURES+File.separator + "Instagramzy"+File.separator+ "Panoramas"+ File.separator + ".temp" + File.separator;
    public static  String GRID_TEMP=Environment.getExternalStorageDirectory().toString() + File.separator+Environment.DIRECTORY_PICTURES+File.separator + "Instagramzy"+File.separator+ "Grid"+ File.separator + ".temp" ;
    public static  String GRID_PATH=Environment.getExternalStorageDirectory().toString() + File.separator+Environment.DIRECTORY_PICTURES+File.separator + "Instagramzy"+File.separator+ "Grid"+ File.separator ;
    public static  String DOWNLOAD_PATH=Environment.getExternalStorageDirectory().toString() + File.separator+Environment.DIRECTORY_PICTURES+File.separator + "Instagramzy"+File.separator+ "DP Story Highlights"+ File.separator ;
    public static  String PANORAMAS_PATH=Environment.getExternalStorageDirectory().toString() + File.separator+Environment.DIRECTORY_PICTURES+File.separator + "Instagramzy"+File.separator+ "Panoramas" + File.separator;

    public static final String DOCUMENTS_DIR = "documents";

    public static boolean isAppInstalled(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static int getWidthScreen(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static String getFileNameWithoutExtension(File file) {
        String str = "";
        if (file != null) {
            try {
                if (file.exists()) {
                    str = file.getName().replaceFirst("[.][^.]+$", str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new StringTokenizer(str, "|").nextToken();
    }

    public static String getPath(Context context, Uri uri) {
        int i = 0;
        Uri uri2 = null;
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, (String) null, (String[]) null);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(":");
            if ("primary".equalsIgnoreCase(split[0])) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if (documentId != null && documentId.startsWith("raw:")) {
                return documentId.substring(4);
            }
            String[] strArr = {"content://downloads/public_downloads", "content://downloads/my_downloads"};
            while (i < 2) {
                try {
                    String dataColumn = getDataColumn(context, ContentUris.withAppendedId(Uri.parse(strArr[i]), Long.valueOf(documentId).longValue()), (String) null, (String[]) null);
                    if (dataColumn != null) {
                        return dataColumn;
                    }
                    i++;
                } catch (Exception unused) {
                }
            }
            File generateFileName = generateFileName(getFileName(context, uri), getDocumentCacheDir(context));
            if (generateFileName != null) {
                return generateFileName.getAbsolutePath();
            }
            return null;
        } else if (isMediaDocument(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
            String str = split2[0];
            if ("image".equals(str)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(str)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
        }
        return null;
    }

    public static File generateFileName(String str, File file) {
        String str2;
        if (str == null) {
            return null;
        }
        File file2 = new File(file, str);
        if (file2.exists()) {
            int lastIndexOf = str.lastIndexOf(46);
            int i = 0;
            if (lastIndexOf > 0) {
                String substring = str.substring(0, lastIndexOf);
                str2 = str.substring(lastIndexOf);
                str = substring;
            } else {
                str2 = "";
            }
            while (file2.exists()) {
                i++;
                file2 = new File(file, str + '(' + i + ')' + str2);
            }
        }
        try {
            if (!file2.createNewFile()) {
                return null;
            }
            return file2;
        } catch (IOException unused) {
            return null;
        }
    }

    public static String getFileName(Context context, Uri uri) {
        if (context.getContentResolver().getType(uri) != null || context == null) {
            Cursor query = context.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
            if (query == null) {
                return null;
            }
            int columnIndex = query.getColumnIndex("_display_name");
            query.moveToFirst();
            String string = query.getString(columnIndex);
            query.close();
            return string;
        }
        String path = getPath(context, uri);
        if (path == null) {
            return getName(uri.toString());
        }
        return new File(path).getName();
    }

    public static String getName(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(str.lastIndexOf(47) + 1);
    }

    public static File getDocumentCacheDir(Context context) {
        File file = new File(context.getCacheDir(), "documents");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }



    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;

}

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
