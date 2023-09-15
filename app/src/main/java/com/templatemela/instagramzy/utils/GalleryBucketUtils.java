package com.templatemela.instagramzy.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class GalleryBucketUtils {
    private static final String DEBUG_TAG = "GalleryBucketUtils";
    public static final String IMAGES = "IMAGES";

    public static class Bucket {
        public String id;
        public String name;
        public List<String> paths = new ArrayList();

        public Bucket(String str, String str2) {
            this.id = str;
            this.name = str2;
        }
    }

    public static List<Bucket> getImgBuckets(Context context) {
        if (Build.VERSION.SDK_INT < 29) {
            return getImageBucketsBelowApi29(context);
        }
        return getImageBuckets(context);
    }

    private static List<Bucket> getImageBucketsBelowApi29(Context context) {
        Cursor query = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"bucket_id", "bucket_display_name", "_data", "_id"}, "1) GROUP BY 1,(2", (String[]) null, "bucket_display_name ASC");
        ArrayList arrayList = new ArrayList();
        if (query == null) {
            return arrayList;
        }
        while (query.moveToNext()) {
            int columnIndex = query.getColumnIndex("bucket_id");
            int columnIndex2 = query.getColumnIndex("bucket_display_name");
            int columnIndex3 = query.getColumnIndex("_data");
            query.getColumnIndex("_id");
            String string = query.getString(columnIndex);
            String string2 = query.getString(columnIndex2);
            if (string2 == null) {
                string2 = "0";
            }
            int isBucketPresent = isBucketPresent(arrayList, string2);
            if (isBucketPresent == -1) {
                Bucket bucket = new Bucket(string, string2);
                bucket.paths.add(query.getString(columnIndex3));
                arrayList.add(bucket);
            } else {
                ((Bucket) arrayList.get(isBucketPresent)).paths.add(query.getString(columnIndex3));
            }
        }
        query.close();
        return arrayList;
    }

    private static int isBucketPresent(List<Bucket> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i).name)) {
                return i;
            }
        }
        return -1;
    }

    private static List<Bucket> getImageBuckets(Context context) {
        Cursor query = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"bucket_id", "bucket_display_name", "_data", "_id"}, (String) null, (String[]) null, (String) null);
        ArrayList arrayList = new ArrayList();
        if (query == null) {
            return arrayList;
        }
        while (query.moveToNext()) {
            int columnIndex = query.getColumnIndex("bucket_id");
            int columnIndex2 = query.getColumnIndex("bucket_display_name");
            int columnIndex3 = query.getColumnIndex("_data");
            query.getColumnIndex("_id");
            String string = query.getString(columnIndex);
            String string2 = query.getString(columnIndex2);
            if (string2 == null) {
                string2 = "0";
            }
            int isBucketPresent = isBucketPresent(arrayList, string2);
            if (isBucketPresent == -1) {
                Bucket bucket = new Bucket(string, string2);
                bucket.paths.add(query.getString(columnIndex3));
                arrayList.add(bucket);
            } else {
                ((Bucket) arrayList.get(isBucketPresent)).paths.add(query.getString(columnIndex3));
            }
        }
        query.close();
        return arrayList;
    }
}
