package com.templatemela.instagramzy.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.TMDownloadHelper;
import com.templatemela.instagramzy.myadapter.TMDownloadAdapter;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchListener;

import com.tonyodev.fetch2.Status;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2core.Func;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMDownloadedViewActivity extends AppCompatActivity {
    TMDownloadAdapter adapter;
    BottomSheetDialog dialog;
    TMDownloadHelper downloadHelper;
    LinkedList<Download> downloadList;
    TextView emptyText;
    String fileName = "";
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_download_view);
        downloadHelper = new TMDownloadHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        emptyText = (TextView) findViewById(R.id.emptyText);
        downloadHelper.getFetch();
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteListGeneration(Fetch fetch) {
        LinkedList<Download> linkedList = new LinkedList<>();
        downloadList = linkedList;
        adapter = new TMDownloadAdapter(linkedList, this, fetch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fetch.getDownloads(new Func<List<Download>>() {

            public void call( List<Download>  list) {
                for (Integer intValue : new Pref(getApplicationContext()).getDownloadIds()) {
                    int intValue2 = intValue.intValue();
                    Iterator<? extends Download> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Download download = (Download) it.next();
                        if (download.getId() == intValue2 && download.getStatus() != Status.DELETED) {
                            downloadList.add(download);
                            break;
                        }
                    }
                }
                if (downloadList.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyText.setVisibility(View.GONE);
                }
                adapter.setDownloads(downloadList);
                adapter.notifyDataSetChanged();
            }
        });

        fetch.addListener(new FetchListener() {
            @Override
            public void onWaitingNetwork(@NonNull Download download) {

            }

            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onQueued(@NonNull Download download, boolean b) {

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

            public void onQueued(Download download) {
            }

            public void onProgress(Download download, long j, long j2) {
                int progress = download.getProgress();
                Log.i("fetch_status", progress + "");
                Log.i("fetch_status", download.getFile());
                updateDownloads(download);
            }

            public void onCompleted(Download download) {
                Log.i("fetch_status", "completed");
                updateDownloads(download);
            }

            public void onError(Download download) {
                updateDownloads(download);
            }

            public void onPaused(Download download) {
                updateDownloads(download);
            }

            public void onResumed(Download download) {
                updateDownloads(download);
            }

            public void onCancelled(Download download) {
                updateDownloads(download);
            }

            public void onRemoved(Download download) {
                updateDownloads(download);
            }

            public void onDeleted(Download download) {
                updateDownloads(download);
            }
        });

    }

    public void updateDownloads(Download download) {
        int i = 0;
        while (i < downloadList.size()) {
            if (!(downloadList.get(i).getId() + "").equals(download.getId() + "")) {
                i++;
            } else if (download.getStatus() == Status.DELETED) {
                downloadList.remove(i);
                adapter.setDownloads(downloadList);
                adapter.notifyDataSetChanged();
                return;
            } else {
                downloadList.set(i, download);
                adapter.setDownloads(downloadList);
                adapter.notifyDataSetChanged();
                return;
            }
        }
        downloadList.addFirst(download);
        adapter.setDownloads(downloadList);
        adapter.notifyDataSetChanged();
        if (downloadList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
