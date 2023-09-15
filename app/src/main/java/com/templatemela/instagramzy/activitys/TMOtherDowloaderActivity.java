package com.templatemela.instagramzy.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMStoryModel;
import com.templatemela.instagramzy.myadapter.TMStoryAdapter;
import com.templatemela.instagramzy.handeler.TMStoryHandeler;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMOtherDowloaderActivity extends AppCompatActivity {
    TextView emptyText;
    LinearLayout emptyView;
    String id = "";
    List<TMStoryModel> list;
    LinearLayout loadingView;
    RecyclerView recyclerView;
    TMStoryAdapter storyAdapter;
    TMStoryHandeler storyHandeler;
    String tag = "OtherDowloaderView";
    TextView title;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_other_dowloader_view);
        id = getIntent().getStringExtra("id");
        storyHandeler = new TMStoryHandeler(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        list = new ArrayList();
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title = (TextView) findViewById(R.id.title);
        loadingView = (LinearLayout) findViewById(R.id.loading);
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        emptyText = (TextView) findViewById(R.id.empty_text);
        title.setText("Highlights of " + getIntent().getStringExtra("name"));
        list=storyHandeler.getHighLight(id,1);
        storyAdapter = new TMStoryAdapter(this, list, 1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(storyAdapter);
        if (list.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteListGeneration(List<TMStoryModel> list2) {
        try {

          /*  if (list2.get(0).getStoryId().contains("high")) {
                storyAdapter.setList(list2);
            }*/
        } catch (Exception unused) {
        }
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
}
