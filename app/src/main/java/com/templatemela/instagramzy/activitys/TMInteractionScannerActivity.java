package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.nativetemplates.TemplateView;
import com.templatemela.instagramzy.utils.ErrorDialog;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.RateDialog;
import com.templatemela.instagramzy.common.TMLoginRequiredDialog;
import com.templatemela.instagramzy.utils.TMAdsUtils;
import com.templatemela.instagramzy.utils.TMInteractionAnalysisListGenerator;
import com.templatemela.instagramzy.utils.TMInteractionHelper;
import com.templatemela.instagramzy.models.TMLikerCommenterModel;
import com.templatemela.instagramzy.models.TMPersonModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.myadapter.TMInteractionAdapter;
import com.templatemela.instagramzy.myadapter.TMRemoveLikeCommetAdapter;
import com.templatemela.instagramzy.handeler.TMFollowerListHadeler;
import com.templatemela.instagramzy.handeler.TMInteractionHandeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMInteractionScannerActivity extends AppCompatActivity {
    TextView countLabel;
    TextView nameTv,usernameTv;
    TextView countTv;
    TextView emptyText;
    LinearLayout emptyView;
    ErrorDialog errorDialog;
    List<TMPersonModel> fList;
    TMFollowerListHadeler followerListHadeler;
    TextView followersTv;
    TextView followingTv;
    Functions functions;
    TMInteractionAdapter interactionAdapter;
    TMInteractionAnalysisListGenerator interactionAnalysisListGenerator;
    TMInteractionHandeler interactionHandeler;
    TMInteractionHelper interactionHelper;
    boolean isFinished = true;
    LinearLayout loadingView;
    List<TMLikerCommenterModel> mainList;
    Pref pref;
    RateDialog rateDialog;
    RecyclerView recyclerView;
    TMRemoveLikeCommetAdapter removeLikeCommetAdapter;
   
    public SwipeRefreshLayout swipeContainer;
    String tag = "InteractionScanner";
    TextView titleTv;
    int type;
    TMUserDetails userDetails;
    TemplateView my_template;

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_list);
        EventBus.getDefault().register(this);
        int intExtra = getIntent().getIntExtra("type", 0);
        type = intExtra;
        type = intExtra - 14;
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        followersTv = (TextView) findViewById(R.id.follower_c);
        nameTv = (TextView) findViewById(R.id.nameTv);
        usernameTv = (TextView) findViewById(R.id.usernameTv);
        followingTv = (TextView) findViewById(R.id.following_c);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        countTv = (TextView) findViewById(R.id.count);
        titleTv = (TextView) findViewById(R.id.title);
        my_template=(TemplateView)findViewById(R.id.my_template);
        loadingView = (LinearLayout) findViewById(R.id.loading);
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        emptyText = (TextView) findViewById(R.id.empty_text);
        countLabel = (TextView) findViewById(R.id.countLabel);
        errorDialog = new ErrorDialog(this);
        rateDialog = new RateDialog(this);
        functions = new Functions(this);
        interactionHelper = new TMInteractionHelper(this);
        followerListHadeler = new TMFollowerListHadeler(this);
        interactionHandeler = new TMInteractionHandeler(this);
        userDetails = new TMUserDetails(this);
        TMUserModel userModel=userDetails.getUser(false);
        pref = new Pref(this);
        mainList = new ArrayList();
        removeLikeCommetAdapter = new TMRemoveLikeCommetAdapter(this, type);
        nameTv.setText(userModel.getName());
        usernameTv.setText(userModel.getUsername());
        if (!userDetails.isLoggedIn()) {
            new TMLoginRequiredDialog(this).showDialog();
            return;
        }
        TMAdsUtils.initAd(TMInteractionScannerActivity.this);
        TMAdsUtils.loadNativeAd(TMInteractionScannerActivity.this,my_template);
        followerListHadeler.fetchList(false);
        loadingView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        titleTv.setText(getIntent().getStringExtra("title"));
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (pref.getLikeCommentListUpdateTimeDiffInMints() <= 0) {
                    errorDialog.setMessage("This list is updated recently.\nPlease try after " + (6 - pref.getLikeCommentListUpdateTimeDiffInMints()) + " minutes.");
                    errorDialog.show();
                    swipeContainer.setRefreshing(false);
                } else if (functions.internetIsConnected()) {
                    interactionHandeler.getLikeCommentList(true);
                    pref.setIsChained(false);
                } else {
                    startActivity(new Intent(TMInteractionScannerActivity.this, TMErrorActivity.class));
                    swipeContainer.setRefreshing(false);
                }
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        TMUserModel user = userDetails.getUser(false);
        followingTv.setText(functions.withSuffix((long) user.getFollowing()));
        followersTv.setText(functions.withSuffix((long) user.getFollowers()));
        int i = type;
        if (i == 8 || i == 9) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(removeLikeCommetAdapter);
            loadingView.setVisibility(View.GONE);
            if (pref.getWhoDeletedLikesOrComments(type - 8).length() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                if (type == 8) {
                    emptyText.setText("No one removed their likes after installing this app.");
                } else {
                    emptyText.setText("No one removed their comments after installing this app.");
                }
            }
            TextView textView = countTv;
            textView.setText(pref.getWhoDeletedLikesOrComments(type - 8).length() + "");
        } else {
            interactionAdapter = new TMInteractionAdapter(this, mainList, (List<TMLikerCommenterModel>) null, i);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(interactionAdapter);
        }
        int i2 = type;
        if (i2 != 8 && i2 != 9) {
            interactionHandeler.getLikeCommentList(false);
        }

        Log.e("onCreate","Called");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLog(TMStrModel str) {
        if (!str.getData().equals("finish")) {
            swipeContainer.setRefreshing(true);
        }
        isFinished = false;
        if (str.getType() == 1 || str.getType() != 2) {
            return;
        }
        if (str.getData().equals("All post's data is fetched")) {
            swipeContainer.setRefreshing(false);
            isFinished = true;
            Toast.makeText(this, str.getData(), Toast.LENGTH_LONG).show();
            int i = type;
            if (i == 8 || i == 9) {
                TextView textView = countTv;
                textView.setText(pref.getWhoDeletedLikesOrComments(type - 8).length() + "");
            } else {
                countTv.setText(functions.withSuffix((long) mainList.size()));
            }
            countLabel.setText("List count :");
            return;
        }
        countLabel.setText("Post fetched :");
        countTv.setText(str.getData().substring(str.getData().indexOf(": ") + 2));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComplete(List<TMLikerCommenterModel> list) {

        Log.e("finished","on Completed");
        try {
            list.get(0).getLikeCount();
            mainList = list;
            if (type < 3) {
                interactionAnalysisListGenerator = new TMInteractionAnalysisListGenerator(list, (List<TMPersonModel>) null);
                mainList = interactionAnalysisListGenerator.getInteractionList(type);
                interactionAdapter.setList(mainList);
                if (isFinished) {
                    countTv.setText(functions.withSuffix((long) mainList.size()));
                }
                if (mainList.size() == 0) {
                    loadingView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    loadingView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                }
            } else if (type <= 7) {
                after3();
            }
        } catch (Exception unused) {
        }
        int i = type;
        if (i == 8 || i == 9) {

            removeLikeCommetAdapter.setJsonArray();
            if (pref.getWhoDeletedLikesOrComments(type - 8).length() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                if (type == 8) {
                    emptyText.setText("No one removed their likes after installing this app.");
                } else {
                    emptyText.setText("No one removed their comments after installing this app.");
                }
            } else {
                emptyView.setVisibility(View.GONE);
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteF(List<TMPersonModel> list) {
        try {
            list.get(0).getTime();
            fList = list;
            Log.i(tag, "flist fetched");
        } catch (Exception unused) {
        }
    }

    
    public void after3() {
        new Thread(new Runnable() {
            public void run() {
                Log.i(tag, "after 3");
                while (fList == null) {
                    try {
                        Thread.sleep(200);
                        Log.i(tag, "sleeping");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        interactionAnalysisListGenerator = new TMInteractionAnalysisListGenerator(mainList, fList);
                        mainList = interactionAnalysisListGenerator.getInteractionList(type);
                        interactionAdapter.setList(mainList);
                        if (mainList.size() == 0) {
                            loadingView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            loadingView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.GONE);
                        }
                        if (isFinished) {
                            countTv.setText(functions.withSuffix((long) mainList.size()));
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
