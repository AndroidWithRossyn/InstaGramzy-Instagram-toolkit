package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.RateDialog;
import com.templatemela.instagramzy.interfaces.SentRequestInterface;
import com.templatemela.instagramzy.models.TMStringParcebleModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.myadapter.TMSentRequestAdapter;
import com.templatemela.instagramzy.handeler.TMSentRequestHandeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import java.util.ArrayList;
import java.util.List;

public class TMSentRequestActivity extends AppCompatActivity implements SentRequestInterface {
    TMSentRequestAdapter adapter;
    Button cancel;
    TextView countTv;
    LinearLayout emptyView;
    TextView followersTv;
    TextView followingTv;
    Functions functions;
    LinearLayout loadingView;
    RateDialog rateDialog;
    RecyclerView recyclerView;
    TMSentRequestHandeler sentRequestHandeler;
    private SwipeRefreshLayout swipeContainer;
    TextView titleTv;
    int type;
    TMUserDetails userDetails;

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_sent_request);
        type = getIntent().getIntExtra("type", 0);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        followersTv = (TextView) findViewById(R.id.follower_c);
        followingTv = (TextView) findViewById(R.id.following_c);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        countTv = (TextView) findViewById(R.id.count);
        titleTv = (TextView) findViewById(R.id.title);
        cancel = (Button) findViewById(R.id.cancel);
        functions = new Functions(this);
        loadingView = (LinearLayout) findViewById(R.id.loading);
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        loadingView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        sentRequestHandeler = new TMSentRequestHandeler(this);
        userDetails = new TMUserDetails(this);
        adapter = new TMSentRequestAdapter((List<String>) null, this);
        rateDialog = new RateDialog(this);
        titleTv.setText(" Sent Requests");
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                   @Override
                    public void run() {
                        sentRequestHandeler.getList(true);
                    }
                }).start();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        new Thread(new Runnable() {
             @Override
            public void run() {
                sentRequestHandeler.getList(false);
            }
        }).start();
        try {
            TMUserModel user = userDetails.getUser(false);
            followersTv.setText(functions.withSuffix((long) user.getFollowers()));
            followingTv.setText(functions.withSuffix((long) user.getFollowing()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cancel.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                if (rateDialog.canShow().booleanValue()) {
                    rateDialog.showDialog();
                    return;
                }
                ArrayList<String> slist = adapter.getSlist();
                if (slist.size() != 0) {
                    ArrayList arrayList = new ArrayList();
                    for (String stringParceble : slist) {
                        arrayList.add(new TMStringParcebleModel(stringParceble));
                    }
                    Intent intent = new Intent(TMSentRequestActivity.this, TMCancelSentRequestsActivity.class);
                    intent.putExtra("sList", arrayList);
                    startActivity(intent);
                    finish();
                    return;
                }
                Toast.makeText(TMSentRequestActivity.this, "No one is selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onComplete(List<String> list) {
        swipeContainer.setRefreshing(false);
        if (list.size() == 0) {
            loadingView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            loadingView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
        }
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        countTv.setText(list.size() + "");
    }
}
