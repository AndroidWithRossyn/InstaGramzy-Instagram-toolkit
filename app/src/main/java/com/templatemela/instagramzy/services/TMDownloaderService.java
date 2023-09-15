package com.templatemela.instagramzy.services;

import static com.templatemela.instagramzy.utils.Utitlity.DOWNLOAD_PATH;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.templatemela.instagramzy.TMApplication;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.activitys.TMDownloadedViewActivity;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;

import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2core.Func;

import java.util.ArrayList;
import java.util.List;

public class TMDownloaderService extends Service {
    static Fetch fetch;
    static List<Integer> list;
    private static NotificationCompat.Builder mBuilder;
    private static NotificationManager mNotifyManager;
    private Functions functions;
    int i = 0;
    int id = 1;
    private final IBinder myIbinder = new MyLocalinder();
    String parentdir = "";
    String tag = "DownloaderService";

    public class MyLocalinder extends Binder {
        public MyLocalinder() {
        }

        public TMDownloaderService getService() {
            TMDownloaderService.list = new ArrayList();
            return TMDownloaderService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        list = new ArrayList();
        return this.myIbinder;
    }

    public int onStartCommand(Intent intent, int i2, int i3) {
        initFetch();
        Log.i(this.tag, "onStartCommand");
        try {
            addListener();
            return Service.START_NOT_STICKY;
        } catch (Exception unused) {
            return Service.START_NOT_STICKY;
        }
    }

    private void addListener() {
        fetch.addListener(new FetchListener() {
            @Override
            public void onWaitingNetwork(@NonNull Download download) {

            }

            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onQueued(@NonNull Download download, boolean b) {
                if (!TMDownloaderService.list.contains(Integer.valueOf(download.getId()))) {
                    TMDownloaderService.list.add(Integer.valueOf(download.getId()));
                    new Pref(getApplicationContext()).saveDownloadId(download.getId());
                }
            }

            @Override
            public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {

            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onAdded(@NonNull Download download) {

            }

            public void onCancelled(Download download) {
            }

            public void onDeleted(Download download) {
            }

            public void onError(Download download) {
            }

            public void onPaused(Download download) {
            }

            public void onRemoved(Download download) {
            }

            public void onResumed(Download download) {
            }

            public void onProgress(Download download, long j, long j2) {
                download.getProgress();
                updateNotification(download);
            }

            public void onQueued(Download download) {

            }

            public void onCompleted(Download download) {
                updateNotificationOnComplete(download);
                if (i == 0) {
                    Toast.makeText(TMDownloaderService.this, "Download completed",  Toast.LENGTH_SHORT).show();
                    i++;
                }
            }
        });
    }

    public void initFetch() {
        this.functions = new Functions(getApplicationContext());
        if (fetch == null) {
            try {
                final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                        .setNamespace("main")
                        .build();

                fetch = (Fetch)  Fetch.Impl.getInstance(fetchConfiguration).setDownloadConcurrentLimit(4).enableLogging(true);
            } catch (Exception unused) {
            }
        }
    }

    public void download(String str, String str2) {
        Log.i(this.tag, "download function called");
        initFetch();
        try {
            addListener();
        } catch (Exception unused) {
        }
        this.parentdir = DOWNLOAD_PATH;
        Request request = new Request(str, this.parentdir + str2);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        fetch.enqueue(request, (Func<Request>) download -> {
            Toast.makeText(TMDownloaderService.this, "Download started", Toast.LENGTH_SHORT).show();
            createNotification();
        }, (Func<Error>) new Func<Error>() {
            public void call(Error error) {
            }
        });
    }

    public Fetch getFetch() {
        return fetch;
    }

    
    public void createNotification() {
        PendingIntent activity = PendingIntent.getActivity(this, 1, new Intent(this, TMDownloadedViewActivity.class), PendingIntent.FLAG_MUTABLE);
        if (mNotifyManager == null) {
            mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (mBuilder == null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, TMApplication.CHANNEL_ID);
            mBuilder = builder;
            builder.setContentTitle("File Download").setContentText("Download in progress").setContentIntent(activity).setSound((Uri) null).setOngoing(true).setOnlyAlertOnce(true).setPriority(-1).setProgress(100, 0, false).setSmallIcon(R.drawable.ic_baseline_get_app_24);
        }
    }

    public void updateNotification(Download download) {
        createNotification();
        if (download.getProgress() == 100) {
            mBuilder.setContentText("Download completed").setOngoing(false).setProgress(0, 0, false);
            mNotifyManager.notify(download.getId(), mBuilder.build());
            return;
        }
        mBuilder.setProgress(100, download.getProgress(), false).setOngoing(false).setContentTitle(download.getFile().replace(this.parentdir, "")).setContentText("Downloading...");
        mNotifyManager.notify(download.getId(), mBuilder.build());
    }

    public void updateNotificationOnComplete(Download download) {
        mBuilder.setContentText("Download completed").setOngoing(false).setProgress(0, 0, false);
        mNotifyManager.notify(download.getId(), mBuilder.build());

    }
}
