package com.templatemela.instagramzy.activitys;

import android.os.Bundle;
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
import com.templatemela.instagramzy.interfaces.FriendShipChangedInterface;
import com.templatemela.instagramzy.models.TMPersonModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.myadapter.TMFollowerAdapter;
import com.templatemela.instagramzy.handeler.TMFollowerListHadeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMListActivity extends AppCompatActivity implements FriendShipChangedInterface {
    TextView countTv;
    TextView emptyText;
    LinearLayout emptyView;
    ErrorDialog errorDialog;
    int follower = 0;
    TMFollowerAdapter followerAdapter;
    TMFollowerListHadeler followerListHadeler;
    TextView followersTv;
    int following = 0;
    TextView followingTv,nameTv,usernameTv;
    Functions functions;
    LinearLayout loadingView;
    List<TMPersonModel> personList;
    Pref pref;
    RateDialog rateDialog;
    RecyclerView recyclerView;
   
    public SwipeRefreshLayout swipeContainer;
    TextView titleTv;
    int type;
    TMUserDetails userDetails;
    TMUserModel user;
    TemplateView my_template;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_list);
        EventBus.getDefault().register(this);
        type = getIntent().getIntExtra("type", 0);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        followersTv = (TextView) findViewById(R.id.follower_c);
        followingTv = (TextView) findViewById(R.id.following_c);
        nameTv = (TextView) findViewById(R.id.nameTv);
        my_template=(TemplateView) findViewById(R.id.my_template);
        TMAdsUtils.initAd(this);
        TMAdsUtils.loadNativeAd(this,my_template);
        usernameTv = (TextView) findViewById(R.id.usernameTv);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        countTv = (TextView) findViewById(R.id.count);
        titleTv = (TextView) findViewById(R.id.title);
        loadingView = (LinearLayout) findViewById(R.id.loading);
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        emptyText = (TextView) findViewById(R.id.empty_text);
        errorDialog = new ErrorDialog(this);
        rateDialog = new RateDialog(this);
        functions = new Functions(this);
        userDetails = new TMUserDetails(this);
        loadingView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        if (!userDetails.isLoggedIn()) {
            new TMLoginRequiredDialog(this).showDialog();
        }
        followerListHadeler = new TMFollowerListHadeler(this);
        userDetails = new TMUserDetails(this);
        user=userDetails.getUser(false);
        nameTv.setText(user.getName());
        usernameTv.setText(user.getUsername());
        pref = new Pref(this);
        titleTv.setText(getIntent().getStringExtra("title"));
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (follower + following > 2000) {
                    errorDialog.setMessage("Your list is too big it may take a long time.");
                    errorDialog.show();
                    if (pref.getFollowerListUpdateTimeDifferance() > 10) {
                        if (follower + following > 20000) {
                            errorDialog.setMessage("Your list is too big it may take a long time.");
                            errorDialog.show();
                        }
                        pref.setIsChained(false);
                        followerListHadeler.fetchList(true);
                        return;
                    }
                    
                    errorDialog.setMessage("You updated your list recently.\nPlease try after " + (11 - pref.getFollowerListUpdateTimeDifferance()) + " minutes.");
                    errorDialog.show();
                    swipeContainer.setRefreshing(false);
                    return;
                }
                pref.setIsChained(false);
                followerListHadeler.fetchList(true);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        TMUserModel user = userDetails.getUser(false);
        if (user.getFollowing() + user.getFollowers() > 50000) {
            errorDialog.setMessage("Your list is too big. It can't be loaded.");
            errorDialog.show();
            swipeContainer.setRefreshing(false);
        } else {
            if (follower + following > 20000) {
                errorDialog.setMessage("Your list is too big it may take a long time.");
                errorDialog.show();
            }
            followerListHadeler.fetchList(false);
        }

        List arrayList = new ArrayList();
        int i = type;
        if (i > 2) {
            arrayList = pref.getRecentFollowUnfollowList(i - 4);
            countTv.setText(functions.withSuffix((long) arrayList.size()));
        }
        followerAdapter = new TMFollowerAdapter(this, arrayList, (List<TMPersonModel>) null, type);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(followerAdapter);
        if (type == 4) {
            emptyText.setText("After Installing This App, No One Unfollowed You");
        }
        if (type == 5) {
            emptyText.setText("After Installing This App, No One Followed You");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLog(TMStrModel str) {
        if (str.getType() == 0) {
            if (!str.getData().contains("100%") && !str.getData().equals("finish")) {
                Toast.makeText(this, str.getData(), Toast.LENGTH_LONG).show();
            }
            swipeContainer.setRefreshing(true);
            if (str.getData().equals("finish")) {
                swipeContainer.setRefreshing(false);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComplete(List<TMPersonModel> list) {
        List<TMPersonModel> list2;
        try {
            list.get(0).getFollowedByMe();
            personList = new ArrayList();
            personList = list;
            following = 0;
            follower = 0;
            followerAdapter.setMainList(list);
            if (type > 2) {
                list2 = pref.getRecentFollowUnfollowList(type - 4);
                TextView textView = countTv;
                textView.setText(list2.size() + "");
                getList(list);
            } else {
                list2 = getList(list);
            }
            if (list2.size() == 0) {
                loadingView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
            }
            followerAdapter.setList(list2);
            followerAdapter.notifyDataSetChanged();
            followingTv.setText(functions.withSuffix((long) following));
            followersTv.setText(functions.withSuffix((long) follower));


            swipeContainer.setRefreshing(false);
        } catch (Exception unused) {
        }
    }

    
    public List<TMPersonModel> getList(List<TMPersonModel> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            TMPersonModel person = list.get(i);
            if (person.getFollowing().booleanValue()) {
                follower++;
            }
            if (person.getFollowedByMe().booleanValue()) {
                following++;
            }
            int i2 = type;
            if (i2 == 0) {
                if (person.getFollowedByMe().booleanValue() && !person.getFollowing().booleanValue()) {
                    arrayList.add(person);
                }
            } else if (i2 == 1) {
                if (!person.getFollowedByMe().booleanValue() && person.getFollowing().booleanValue()) {
                    arrayList.add(person);
                }
            } else if (i2 == 2 && person.getFollowedByMe().booleanValue() && person.getFollowing().booleanValue()) {
                arrayList.add(person);
            }
        }
        if (type < 3) {
            countTv.setText(arrayList.size() + "");
        }
        return arrayList;
    }

    @Override
    public void onFriendShipSuccessfullyChanged(TMPersonModel person) {
        if (person.getFollowedByMe().booleanValue()) {
            following++;
        } else {
            following--;
        }
        pref.updateCounts(follower + "", following + "");
        followingTv.setText(functions.withSuffix((long) following));
        followersTv.setText(functions.withSuffix((long) follower));
        int i = 0;
        while (true) {
            if (i >= personList.size()) {
                break;
            } else if (personList.get(i).getId().equals(person.getId())) {
                personList.set(i, person);
                pref.saveMainList(personList);
                break;
            } else {
                i++;
            }
        }
        rateDialog.increaseCount();
    }

    @Override
    public void onFriendShipChangedError() {
        errorDialog.setMessage("Error while doing this operation. Kindly wait for sometime and try again.");
        errorDialog.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
