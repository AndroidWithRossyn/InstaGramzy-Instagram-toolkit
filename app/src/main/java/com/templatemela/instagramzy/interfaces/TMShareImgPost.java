package com.templatemela.instagramzy.interfaces;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

public class TMShareImgPost {
    public static void ShareHandler(AppCompatActivity appCompatActivity, Uri uri, String str, int i) {
        if (appCompatActivity != null) {
            try {
                if (!appCompatActivity.isFinishing()) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.SEND");
                    intent.setType("image/*");
                    new ArrayList();
                    for (ResolveInfo next : appCompatActivity.getApplicationContext().getPackageManager().queryIntentActivities(intent, 65536)) {
                        String str2 = next.activityInfo.packageName;
                        String str3 = next.activityInfo.name;
                        if (!str2.equalsIgnoreCase(appCompatActivity.getPackageName()) && str2.contains(str) && str3.contains("ShareHandlerActivity")) {
                            Intent intent2 = new Intent();
                            intent2.setAction("android.intent.action.SEND");
                            intent2.setType("image/*");
                            intent2.setComponent(new ComponentName(str2, str3));
                            intent2.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(appCompatActivity, appCompatActivity.getPackageName() + ".file_provider", new File(uri.getPath())));
                            appCompatActivity.startActivityForResult(intent2, i);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
