package com.templatemela.instagramzy;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import com.templatemela.instagramzy.services.HomeKeyBroadCastReceiver;
import com.templatemela.instagramzy.utils.ErrorHandeler;

public class TMApplication extends Application {
    public static final String CHANNEL_ID = "Operation";
    static TMApplication app;
    private static Thread thread;

    public static TMApplication the() {
        return app;
    }


    @Override
    public void onCreate() {
        String processName;
        super.onCreate();
        app = this;
        Log.i("App_", "on create");
        new ErrorHandeler.Builder(this).build();
        if (Build.VERSION.SDK_INT >= 28 && getPackageName() != (processName = getProcessName())) {
            WebView.setDataDirectorySuffix(processName);
        }
        createNotificationChannel();
        HomeKeyBroadCastReceiver homeKeyBroadCastReceiver = new HomeKeyBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        registerReceiver(homeKeyBroadCastReceiver, intentFilter);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "id", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound((Uri) null, (AudioAttributes) null);
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i("App_", "on destroy");
    }
}
