package com.templatemela.instagramzy.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMMessageItemModel;
import com.templatemela.instagramzy.models.TMMessageModelList;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.myadapter.TMMessageAdapter;
import com.templatemela.instagramzy.handeler.TMMessageListHadeler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TVMessagesViewActivity extends AppCompatActivity {
    String dp;
    List<TMMessageItemModel> list;
    LinearLayout loading;
    TMMessageAdapter messageAdapter;
    TMMessageListHadeler messageListHadeler;
    String name;
    Pref pref;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    String threadId = "";
    TextView title;
    HashMap<String, Integer> unseenHashmap;
    String username;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);
        setContentView((int) R.layout.activity_messages_view);
        threadId = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        dp = getIntent().getStringExtra("dp");
        username = getIntent().getStringExtra("username");
        messageListHadeler = new TMMessageListHadeler(this);
        messageAdapter = new TMMessageAdapter(this);
        list = new ArrayList();
        Pref pref2 = new Pref(this);
        pref = pref2;
        unseenHashmap = pref2.getUnseenCount();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        loading = (LinearLayout) findViewById(R.id.loading);
        title = (TextView) findViewById(R.id.title);
        loading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        recyclerView.setAdapter(messageAdapter);
        messageListHadeler.fetchMessage(threadId, "");
        TextView textView = title;
        textView.setText("Conversation with " + name);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDp(HashMap<String, String> hashMap) {
        messageAdapter.setDpMap(hashMap);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComplete(final TMMessageModelList messageModelList) {
        progressBar.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        unseenHashmap.put(username, 0);
        pref.saveUnseenCount(unseenHashmap);
        EventBus.getDefault().post(new TMStrModel("update", 99));
        if (!messageModelList.isShouldAdd()) {
            list.clear();
        }
        for (TMMessageItemModel add : messageModelList.getList()) {
            list.add(add);
        }
        messageAdapter.setList(list);
        if (messageModelList.isHasOlder()) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (!recyclerView.canScrollVertically(-1) && i == 0) {
                        Log.d("-----......", "end");
                        recyclerView.removeOnScrollListener(this);
                        progressBar.setVisibility(View.VISIBLE);
                        messageListHadeler.fetchMessage(threadId, messageModelList.getList().get(messageModelList.getList().size() - 1).getId());
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
