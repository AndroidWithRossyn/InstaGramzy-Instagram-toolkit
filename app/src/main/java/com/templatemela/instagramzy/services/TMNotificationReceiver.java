/*
package com.templatemela.instagramzy.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.exifinterface.media.ExifInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.templatemela.instagramzy.ActionReceiver;
import com.templatemela.instagramzy.App;
import com.templatemela.instagramzy.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.ChatNotificationModel;
import com.templatemela.instagramzy.myactivities.Login;
import com.templatemela.instagramzy.myactivities.MainActivity;
import com.templatemela.instagramzy.myautoreply.ArFunction;
import com.templatemela.instagramzy.networkLogics.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationReceiver extends NotificationListenerService {
    Gson gson;
    HashMap<String, Integer> hashMap;
    long lastMillis;
    NotificationManager mNotificationManager;
    Notification notification;
    Notification notification1;
    List<String> packages;
    Pref pref;
    UserDetails userDetails;
    String username;
    String tag = "NotificationReceiver";
    String lastSender = "";
    Context context = this;
    Handler handler = new Handler(Looper.getMainLooper());
  //  ArFunction arFunction = new ArFunction(this);

    public NotificationReceiver() {
        ArrayList arrayList = new ArrayList();
        this.packages = arrayList;
        arrayList.add("com.whatsapp");
        this.packages.add("com.facebook.orca");
        this.packages.add("org.telegram.messenger");
        this.packages.add("com.instagram.android");
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerConnected() {
        super.onListenerConnected();
        this.lastMillis = 0L;

        PendingIntent activity = PendingIntent.getActivity(this, 2, new Intent(this, Login.class), 33554432);
        Intent intent = new Intent(this.context, ActionReceiver.class);
        intent.putExtra("action", 0);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.context, 2, intent, 33554432);
        Intent intent2 = new Intent(this.context, MainActivity.class);
        intent2.putExtra("action", 1);
        this.notification1 = new NotificationCompat.Builder(this, App.CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).setContentText("Choose one action").setContentTitle("New message received").setContentIntent(activity).addAction(R.drawable.ic_launcher_foreground, "Chat head", broadcast).addAction(R.drawable.ic_launcher_foreground, "Watch without seen mark", PendingIntent.getActivity(this.context, 2, intent2, 33554432)).build();
        this.gson = new GsonBuilder().create();
        UserDetails userDetails = new UserDetails(this);
        this.userDetails = userDetails;
        this.username = userDetails.getUser(false).getUsername();
        try {
            if (!this.arFunction.target().getBoolean("insta")) {
                return;
            }
            ((NotificationManager) getSystemService("notification")).notify(1, this.notification);
            startForeground(1, this.notification);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        stopForeground(true);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        if (!statusBarNotification.isOngoing()) {
            try {
                if (!statusBarNotification.getPackageName().equals("com.instagram.android") || System.currentTimeMillis() - this.lastMillis < 500) {
                    return;
                }
                this.lastMillis = System.currentTimeMillis();
                String string = statusBarNotification.getNotification().extras.getString(NotificationCompat.EXTRA_TITLE);
                String string2 = statusBarNotification.getNotification().extras.getString(NotificationCompat.EXTRA_TEXT);
                Log.i(this.tag, string2);
                int indexOf = string2.indexOf(")");
                if (string2.contains(")")){
                    if (!this.username.equals(string2.substring(1, indexOf).trim())) {
                        return;
                    }
                }
                NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
                this.mNotificationManager = notificationManager;
                notificationManager.notify(2, this.notification1);
                int indexOf2 = string2.indexOf(":", indexOf) + 1;
                int indexOf3 = string2.indexOf(":", indexOf2);
                String trim = string2.substring(indexOf2, indexOf3).trim();
                String trim2 = string2.substring(indexOf3 + 1).trim();
                String str = "";
                if (!string.equals("Instagram")) {
                    increaseUnseenCount(string);
                } else {
                    JSONArray jSONArray = new JSONArray(this.gson.toJson(new Pref(getApplicationContext()).getChatList()));
                    int i = 0;
                    while (true) {
                        if (i >= jSONArray.length()) {
                            break;
                        } else if (trim.trim().equals(jSONArray.getJSONObject(i).getString("name").trim())) {
                            str = jSONArray.getJSONObject(i).getString("username");
                            increaseUnseenCount(jSONArray.getJSONObject(i).getString("username"));
                            break;
                        } else {
                            i++;
                        }
                    }
                    string = str;
                }
                EventBus.getDefault().post(new ChatNotificationModel(trim, string, trim2));
                if (trim.equals(this.username)) {
                    return;
                }
                processNotification(statusBarNotification, trim, trim2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void increaseUnseenCount(String str) {
        int i;
        Pref pref = new Pref(getApplicationContext());
        this.pref = pref;
        HashMap<String, Integer> unseenCount = pref.getUnseenCount();
        this.hashMap = unseenCount;
        Log.i(this.tag, this.gson.toJson(unseenCount));
        try {
            i = (int) Double.parseDouble(this.hashMap.get(str) + "");
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        this.hashMap.put(str, Integer.valueOf(i + 1));
        this.pref.saveUnseenCount(this.hashMap);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals(getPackageName() + ".DISABLE_ACTION")) {
                stopForeground(true);
                NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.cancel(1);
                }
            }
        }
        if (intent != null && intent.getAction() != null) {
            String action2 = intent.getAction();
            if (action2.equals(getPackageName() + ".ENABLE_ACTION")) {
                startForeground(1, this.notification);
                NotificationManager notificationManager2 = (NotificationManager) getSystemService("notification");
                if (notificationManager2 != null) {
                    notificationManager2.notify(1, this.notification);
                }
            }
        }
        return 1;
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        stopForeground(true);
    }

    private void processNotification(StatusBarNotification statusBarNotification, String str, String str2) {
        String packageName = statusBarNotification.getPackageName();
        if (this.arFunction.isOn(this.packages.indexOf(packageName))) {
            Log.i(this.tag, "function is on");
            try {
                JSONArray list = this.arFunction.getList(this.packages.indexOf(packageName));
                for (int i = 0; i < list.length(); i++) {
                    JSONObject jSONObject = list.getJSONObject(i);
                    if (!this.arFunction.isOnIgnoredContacts(jSONObject, str).booleanValue()) {
                        try {
                            if (!this.arFunction.isBetweenTime(jSONObject.getString("spTime")).booleanValue()) {
                            }
                        } catch (Exception unused) {
                        }
                        try {
                            if (!jSONObject.getBoolean("enable")) {
                            }
                        } catch (Exception unused2) {
                        }
                        if (this.arFunction.isOnContacts(jSONObject, str).booleanValue() && this.arFunction.isOnMsg(jSONObject, str2).booleanValue()) {
                            String reply = this.arFunction.getReply(jSONObject);
                            replyToNotification(statusBarNotification, reply, jSONObject.getInt("maxDelay"), jSONObject.getInt("minDelay"), str2);
                            Log.i("reply", reply);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void replyToNotification(final StatusBarNotification statusBarNotification, final String str, final int i, final int i2, final String str2) {
        Log.i(this.tag, "replaying...");
        new Thread(new Runnable() { // from class: com.templatemela.instagramzy.services.NotificationReceiver.1
            @Override // java.lang.Runnable
            public void run() {
                Random random = new Random();
                int i3 = i;
                if (i3 != 0) {
                    if (i3 == i2) {
                        i3++;
                    }
                    int nextInt = random.nextInt(i3 - i2) + i2;
                    Log.i("delay", nextInt + "");
                    try {
                        Thread.sleep(nextInt * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.i("delay", e.getMessage() + "");
                    }
                }
                statusBarNotification.getNotification().extras.getString(NotificationCompat.EXTRA_TEXT);
                NotificationReceiver.this.sendNoti(str2, str);
                NotificationReceiver.this.replyToNoti(statusBarNotification, str);
            }
        }).start();
    }

    public void replyToNoti(StatusBarNotification statusBarNotification, String str) {
        RemoteInput[] remoteInputs;
        Log.i(this.tag, "replyToNoti");
        Bundle bundle = statusBarNotification.getNotification().extras;
        NotificationCompat.Action action = getAction(statusBarNotification);
        if (action == null || action.getRemoteInputs() == null) {
            return;
        }
        Log.d("TAG", "NotificationUtils replyToNotification act.getTitle: " + ((Object) action.getTitle()));
        for (RemoteInput remoteInput : action.getRemoteInputs()) {
            Log.d("TAG", "NotificationUtils replyToNotification remoteInput.getLabel: " + ((Object) remoteInput.getLabel()));
            bundle.putCharSequence(remoteInput.getResultKey(), str);
        }
        Intent intent = new Intent();
        intent.addFlags(268435456);
        RemoteInput.addResultsToIntent(action.getRemoteInputs(), intent, bundle);
        try {
            action.actionIntent.send(this, 0, intent);
        } catch (PendingIntent.CanceledException e) {
            Log.e("TAG", "NotificationUtils replyToNotification error: " + e.getLocalizedMessage());
        }
    }

    void sendNoti(String str, String str2) {
        int nextInt = new Random().nextInt();
        */
/*PendingIntent activity = PendingIntent.getActivity(this, 1, new Intent(this, ArRuleList.class), 33554432);*//*

        NotificationCompat.Builder smallIcon = new NotificationCompat.Builder(this, App.CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher);
        NotificationCompat.Builder contentText = smallIcon.setContentText(str + " > " + str2);
        StringBuilder sb = new StringBuilder();
        sb.append("Reply sent on ");
        sb.append("Instagram");
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(nextInt, contentText.setContentTitle(sb.toString()).setContentIntent(activity).setAutoCancel(true).build());
    }

    private NotificationCompat.Action getAction(StatusBarNotification statusBarNotification) {
        for (NotificationCompat.Action action : new NotificationCompat.WearableExtender(statusBarNotification.getNotification()).getActions()) {
            Log.i("test", ExifInterface.GPS_MEASUREMENT_2D);
            Log.i("test", action.title.toString());
            if (action != null && action.getRemoteInputs() != null && isOk(action).booleanValue()) {
                return action;
            }
        }
        for (int i = 0; i < NotificationCompat.getActionCount(statusBarNotification.getNotification()); i++) {
            NotificationCompat.Action action2 = NotificationCompat.getAction(statusBarNotification.getNotification(), i);
            if (action2 != null && action2.getRemoteInputs() != null && isOk(action2).booleanValue()) {
                return action2;
            }
        }
        return null;
    }

    private Boolean isOk(NotificationCompat.Action action) {
        if (action.getRemoteInputs() == null) {
            return false;
        }
        for (RemoteInput remoteInput : action.getRemoteInputs()) {
            if (remoteInput.getAllowFreeFormInput()) {
                return true;
            }
        }
        return false;
    }
}*/
