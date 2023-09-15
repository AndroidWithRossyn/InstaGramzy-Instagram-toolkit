package com.templatemela.instagramzy.activitys;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.templatemela.instagramzy.utils.ErrorDialog;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMPostModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.myadapter.TMPostAdapter;
import com.templatemela.instagramzy.handeler.TMPostHandeler;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMLikeCommentsActivity extends AppCompatActivity {
    TMPostAdapter adapter;
    ErrorDialog errorDialog;
    TMPostHandeler postHandeler;
    List<TMPostModel> postList;
    Pref pref;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_like_comments);
        EventBus.getDefault().register(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        adapter = new TMPostAdapter(this);
        postHandeler = new TMPostHandeler(this);
        pref = new Pref(this);
        errorDialog = new ErrorDialog(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                if (pref.getPostListUpdateTimeDifferance() > 5) {
                    postHandeler.getPosts(true);
                    return;
                }
                
                errorDialog.setMessage("This list is updated recently.\nPlease try after " + (6 - pref.getPostListUpdateTimeDifferance()) + " minutes.");
                errorDialog.show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        postHandeler.getPosts(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "Select a post to like all comments", Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteListGeneration(List<TMPostModel> list) {
        try {
            list.get(0).getViews();
            postList = list;
            adapter.setPostList(list);
            adapter.notifyDataSetChanged();
        } catch (Exception unused) {
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLog(TMStrModel str) {
        if (str.getType() != 1) {
            return;
        }
        if (str.getData().equals("finish")) {
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        swipeRefreshLayout.setRefreshing(true);
        Toast.makeText(this, str.getData(), 0).show();
    }

    
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
