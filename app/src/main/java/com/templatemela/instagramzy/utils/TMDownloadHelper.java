package com.templatemela.instagramzy.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.templatemela.instagramzy.services.TMDownloaderService;
import org.greenrobot.eventbus.EventBus;

public class TMDownloadHelper {
    static boolean isServiceConnected = false;
    Context context;
    public TMDownloaderService downloaderService;
    Handler handler;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(tag, "on onServiceConnected entered");
            downloaderService = ((TMDownloaderService.MyLocalinder) iBinder).getService();
            TMDownloadHelper.isServiceConnected = true;
            Log.i(tag, "on onServiceConnected finished");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(tag, "on onServiceDisconnected finished");
            TMDownloadHelper.isServiceConnected = false;
        }
    };
    String tag = "DownloadHandeler";

    public TMDownloadHelper(Context context2) {
        this.context = context2;
        this.handler = new Handler(Looper.getMainLooper());
        startServiceAndBind();
    }

    public void startDownload(final String str, final String str2) {
        startServiceAndBind();
        new Thread(new Runnable() {
            public void run() {
                while (!TMDownloadHelper.isServiceConnected) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    public void run() {
                        Log.i(tag, "downloaderService download called");
                        downloaderService.download(str, str2);
                    }
                });
            }
        }).start();
    }

    public void getFetch() {
        startServiceAndBind();
        new Thread(new Runnable() {
            public void run() {
                while (downloaderService == null) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    public void run() {
                        Log.i(tag, "downloaderService download called");
                        EventBus.getDefault().post(downloaderService.getFetch());
                    }
                });
            }
        }).start();
    }

    private void startServiceAndBind() {
        Intent intent = new Intent(this.context, TMDownloaderService.class);
        this.context.bindService(intent, this.serviceConnection, 1);
        this.context.startService(intent);
        Log.i("data__", "start");
    }
}
