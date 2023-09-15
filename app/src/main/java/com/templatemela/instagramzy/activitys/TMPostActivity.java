package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.templatemela.instagramzy.utils.ErrorDialog;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.RateDialog;
import com.templatemela.instagramzy.common.TMLoginRequiredDialog;
import com.templatemela.instagramzy.utils.TMPostAnalylisListsGenerator;
import com.templatemela.instagramzy.models.TMPostModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.myadapter.TMPostAdapter;
import com.templatemela.instagramzy.handeler.TMPostHandeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import java.util.ArrayList;
import java.util.List;
import nl.joery.animatedbottombar.AnimatedBottomBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMPostActivity extends AppCompatActivity {
    int a = 0;
    TMPostAdapter adapter;
    AnimatedBottomBar animatedBottomBar;
    TextView avgCommentsTv;
    TextView avgLikesTv;
    TextView countTv;
    TextView emptyText;
    LinearLayout emptyView;
    ErrorDialog errorDialog;
    TextView nameTv;
    TextView usernameTv;
    int follower = 0;
    int following = 0;
    Functions functions;
    List<TMPostModel> l;
    LinearLayout loadingView;
    TextView logTv;
    TMPostAnalylisListsGenerator postAnalylisListsGenerator;
    TMPostHandeler postHandeler;
    List<TMPostModel> postList;
    Pref pref;
    RateDialog rateDialog;
    RecyclerView recyclerView;
   
    public SwipeRefreshLayout swipeContainer;
    TextView titleTv;
    int type;
    TMUserModel user;
    TMUserDetails userDetails;

    
    public List<TMPostModel> getHalf(List<TMPostModel> list) {
        return list;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_post);
        EventBus.getDefault().register(this);
        type = getIntent().getIntExtra("type", 0);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        avgLikesTv = (TextView) findViewById(R.id.avg_likes_c);
        avgCommentsTv = (TextView) findViewById(R.id.avg_comments);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        countTv = (TextView) findViewById(R.id.count);
        titleTv = (TextView) findViewById(R.id.title);
        nameTv = (TextView) findViewById(R.id.nameTv);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        usernameTv = (TextView) findViewById(R.id.usernameTv);
        loadingView = (LinearLayout) findViewById(R.id.loading);
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        emptyText = (TextView) findViewById(R.id.empty_text);
        animatedBottomBar = (AnimatedBottomBar) findViewById(R.id.bottom_bar);
        errorDialog = new ErrorDialog(this);
        rateDialog = new RateDialog(this);
        functions = new Functions(this);
        userDetails = new TMUserDetails(this);
        pref = new Pref(this);
        user = userDetails.getUser(false);
        if (!userDetails.isLoggedIn()) {
            new TMLoginRequiredDialog(this).showDialog();
        }
        loadingView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        titleTv.setText(getIntent().getStringExtra("title"));
        countTv.setText(user.getPostCount() + "");
        usernameTv.setText("@" + user.getUsername());
        nameTv.setText(user.getName());
        int i = type;
        if (i == 6 || i == 7) {
            animatedBottomBar.setVisibility(View.GONE);
        }
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabReselected(int i, AnimatedBottomBar.Tab tab) {
            }

            @Override
            public void onTabSelected(int i, AnimatedBottomBar.Tab tab, int i2, AnimatedBottomBar.Tab tab2) {
                List<TMPostModel> list;
                new ArrayList();
                if (animatedBottomBar.getSelectedIndex() == 0) {
                    postAnalylisListsGenerator = new TMPostAnalylisListsGenerator(postList);
                    list = postAnalylisListsGenerator.getPostList(type - 6, 1);
                } else {
                    postAnalylisListsGenerator = new TMPostAnalylisListsGenerator(postList);
                    list = postAnalylisListsGenerator.getPostList(type - 6, 2);
                }
                adapter.setPostList(list);
                if (list.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
            }
        });
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (pref.getPostListUpdateTimeDifferance() <= 5) {
                    errorDialog.setMessage("This list is updated recently.\nPlease try after " + (6 - pref.getPostListUpdateTimeDifferance()) + " minutes.");
                    errorDialog.show();
                    swipeContainer.setRefreshing(false);
                } else if (functions.internetIsConnected()) {
                    postHandeler.getPosts(true);
                    pref.setIsChained(false);
                } else {
                    startActivity(new Intent(TMPostActivity.this, TMErrorActivity.class));
                    swipeContainer.setRefreshing(false);
                }
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        postHandeler = new TMPostHandeler(this);
        adapter = new TMPostAdapter(this);
        postHandeler.getPosts(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteListGeneration(List<TMPostModel> list) {
        List<TMPostModel> list2;
        avgLikesTv.setText(getAvgLikes(list));
        avgCommentsTv.setText(getAvgComments(list));
        new ArrayList();
        postList = list;
        try {
            list.get(0).getViews();
            loadingView.setVisibility(View.GONE);
            int i = type;
            if (type == 6 || type == 7) {
                postAnalylisListsGenerator = new TMPostAnalylisListsGenerator(list);
                list2 = postAnalylisListsGenerator.getPostList(type - 6, 2);
                adapter.setPostList(list2);
            } else if (animatedBottomBar.getSelectedIndex() == 0) {
                postAnalylisListsGenerator = new TMPostAnalylisListsGenerator(postList);
                list2 = postAnalylisListsGenerator.getPostList(type - 6, 1);
                adapter.setPostList(list2);
            } else {
                postAnalylisListsGenerator = new TMPostAnalylisListsGenerator(postList);
                list2 = postAnalylisListsGenerator.getPostList(type - 6, 2);
                adapter.setPostList(list2);
            }
            if (list2.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLog(TMStrModel str) {
        if (str.getType() != 1) {
            return;
        }
        if (str.getData().equals("finish")) {
            swipeContainer.setRefreshing(false);
            return;
        }
        swipeContainer.setRefreshing(true);
        Toast.makeText(this, str.getData(), Toast.LENGTH_LONG).show();
    }

    
    public String getAvgLikes(List<TMPostModel> list) {
        int i = 0;
        for (TMPostModel likeCount : list) {
            i += likeCount.getLikeCount();
        }
        return functions.withSuffix((long) (i / list.size()));
    }

    
    public String getAvgComments(List<TMPostModel> list) {
        int i = 0;
        for (TMPostModel commentCount : list) {
            i += commentCount.getCommentCount();
        }
        return functions.withSuffix((long) (i / list.size()));
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
