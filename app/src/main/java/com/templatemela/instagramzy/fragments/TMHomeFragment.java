package com.templatemela.instagramzy.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.LoginWebviewDialog;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.WebViewDialog;
import com.templatemela.instagramzy.utils.TMFollowerAnalysisListsCounts;
import com.templatemela.instagramzy.utils.TMGridSpacingItemDecoration;
import com.templatemela.instagramzy.utils.TMInteractionAnalysisListGenerator;
import com.templatemela.instagramzy.utils.TMInteractionHelper;
import com.templatemela.instagramzy.utils.TMPostAnalylisListsGenerator;
import com.templatemela.instagramzy.interfaces.LoginInterface;
import com.templatemela.instagramzy.models.TMFollowAnalysisModel;
import com.templatemela.instagramzy.models.TMMainActivityButtonModel;
import com.templatemela.instagramzy.models.TMPersonModel;
import com.templatemela.instagramzy.models.TMStoryModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.activitys.TMListActivity;
import com.templatemela.instagramzy.activitys.TMLoginActivity;
import com.templatemela.instagramzy.activitys.TMPostActivity;
import com.templatemela.instagramzy.myadapter.TMFollowAnalysisAdapter;
import com.templatemela.instagramzy.myadapter.TMMainListAdapter;
import com.templatemela.instagramzy.myadapter.TMStoryAdapter;
import com.templatemela.instagramzy.handeler.TMFollowerListHadeler;
import com.templatemela.instagramzy.handeler.TMInteractionHandeler;
import com.templatemela.instagramzy.handeler.TMPostHandeler;
import com.templatemela.instagramzy.handeler.TMStoryHandeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMHomeFragment extends Fragment implements LoginInterface {
    int a = 0;
    List<TMFollowAnalysisModel> buttonList;
    List<TMMainActivityButtonModel> buttonList2;
    List<TMMainActivityButtonModel> buttonList3;

    ImageView dp;
    TMFollowerAnalysisListsCounts followerAnalysisListsCounts;
    TMFollowerListHadeler followerListHadeler;
    TextView follower_c;
    TextView following_c;
    Functions functions;
    Handler handler;
    CardView hview;
    CardView iNotBack;
    Intent intent;
    TMInteractionAnalysisListGenerator interactionAnalysisListGenerator;
    TMInteractionHandeler interactionHandeler;
    TMInteractionHelper interactionHelper;
    LinearLayout logOut;
    LoginWebviewDialog loginWebviewDialog;
    CardView mutual;
    TextView name;
    CardView notBack;
    TMPostAnalylisListsGenerator postAnalylisListsGenerator;
    TMPostHandeler postHandeler;
    Intent postIntent;
    TextView post_c;
    Pref pref;
    ProgressBar progressBar;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    CardView requests;
    CardView rfollow;
    CardView runfollow;
    Intent seIntent;
    LinearLayout storiesLebel;
    TMStoryHandeler storyHandeler;
    RecyclerView storyRe;
    TextView t;





    TMUserModel user;
    TMUserDetails userDetails;
    TextView username;
    View view;
    WebViewDialog webviewDialog;
    int z = 0;
    private Uri selectedUri;


    public TMHomeFragment(){

    }
    @Override
    public void onLogInComplete() {
    }

    @Override
    public void onLogOutComplete() {
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate(R.layout.main_view, viewGroup, false);
        EventBus.getDefault().register(this);
        handler = new Handler(Looper.getMainLooper());
        dp = (ImageView) view.findViewById(R.id.dp);
        logOut = (LinearLayout) view.findViewById(R.id.logout);
        name = (TextView) view.findViewById(R.id.nameTv);
        username = (TextView) view.findViewById(R.id.usernameTv);
        follower_c = (TextView) view.findViewById(R.id.follower_c);
        following_c = (TextView) view.findViewById(R.id.following_c);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.follower_re);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.post_re);
        recyclerView3 = (RecyclerView) view.findViewById(R.id.interaction_re);
        storyRe = (RecyclerView) view.findViewById(R.id.story_re);
        post_c = (TextView) view.findViewById(R.id.post);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_circular);
        storiesLebel = (LinearLayout) view.findViewById(R.id.storieslabel);
        webviewDialog = new WebViewDialog(view.getContext());
        functions = new Functions(view.getContext());
        pref = new Pref(view.getContext());
        userDetails = new TMUserDetails(view.getContext());
        buttonList = new ArrayList();
        buttonList2 = new ArrayList();
        buttonList3 = new ArrayList();


        t = (TextView) view.findViewById(R.id.text);
        seIntent = new Intent(view.getContext(), TMPostHandeler.class);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle((CharSequence) "Logout");
                builder.setMessage((CharSequence) "Are you sure to log out from this account ?").setCancelable(false).setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userDetails.logout();
                        startActivity(new Intent(view.getContext(), TMLoginActivity.class));
                        ((Activity) getContext()).finish();
                    }
                }).setNeutralButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        user = userDetails.getUser(false);
        try {
            name.setText(user.getName());
            username.setText("@" + user.getUsername());
            follower_c.setText(functions.withSuffix((long) user.getFollowers()));
            following_c.setText(functions.withSuffix((long) user.getFollowing()));
            post_c.setText(functions.withSuffix((long) user.getPostCount()));
            Glide.with(getContext()).load(user.getDp()).into((ImageView) dp);
        } catch (Exception unused) {
            name.setText("Guest");
            username.setText("@guest");
            follower_c.setText(functions.withSuffix(0));
            following_c.setText(functions.withSuffix(0));
            post_c.setText(functions.withSuffix(0));
        }
        storiesLebel.setVisibility(View.GONE);
        intent = new Intent(view.getContext(), TMListActivity.class);
        postIntent = new Intent(view.getContext(), TMPostActivity.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final TMUserModel user = userDetails.getUser(true);
                    handler.post(new Runnable() {
                        public void run() {
                            name.setText(user.getName());
                            username.setText("@" + user.getUsername());
                            follower_c.setText(functions.withSuffix((long) user.getFollowers()));
                            following_c.setText(functions.withSuffix((long) user.getFollowing()));
                            Glide.with(view.getContext()).load(user.getDp()).into((ImageView) dp);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (userDetails.isLoggedIn()) {
            Log.i("tag__", "on story");
            new TMStoryHandeler(getContext()).getAllStories(true, new TMStoryHandeler.StoryInterface() {
                @Override
                public void onStoryGot(final List<TMStoryModel> list) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ((TMStoryModel) list.get(0)).getStoryId();
                            TMStoryAdapter storyAdapter = new TMStoryAdapter(getContext(), list, 0);
                            storyRe.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                            storyRe.setAdapter(storyAdapter);
                            storiesLebel.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
            return;
        }
        Log.i("tag__", "not loggedin");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteFollowerList(List<TMPersonModel> list) {
        try {
            list.get(0).getFollowedByMe();
            if (z == 0 && pref.isChained().booleanValue()) {
                interactionHandeler.getLikeCommentList(true);
            }


            z = 1;
        } catch (Exception unused) {
        }
    }

  //  @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLog(TMStrModel str) {
        if (str.getType() < 30) {
            if (!str.getData().equals("finish")) {

                progressBar.setVisibility(View.VISIBLE);
                setUpdateLayout(true);
            } else if (!pref.isChained().booleanValue()) {


                progressBar.setVisibility(View.GONE);
                setUpdateLayout(false);
                setButtons();
            }
            if (str.getData().equals("All post's data is fetched")) {

                progressBar.setVisibility(View.GONE);
                pref.setIsChained(false);
                setUpdateLayout(false);
                setButtons();
                Toast.makeText(getContext(), "List is updated successfully", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void setUpdateLayout(boolean z2) {
        if (z2) {
            return;
        }
        pref = new Pref(getContext());
        int likeCommentListUpdateTimeDiffInMints = pref.getLikeCommentListUpdateTimeDiffInMints();
        int followerListUpdateTimeDifferance = pref.getFollowerListUpdateTimeDifferance();
        if (likeCommentListUpdateTimeDiffInMints >= 360 || followerListUpdateTimeDifferance >= 360) {
            if (!userDetails.isLoggedIn()) {



            } else if (likeCommentListUpdateTimeDiffInMints > 2592000) {



            } else {

                           }


            return;
        }


    }


    public void setButtons() {
        followerAnalysisListsCounts = new TMFollowerAnalysisListsCounts(getContext(), pref.getMainList());
        followerListHadeler = new TMFollowerListHadeler(getContext());
        interactionHandeler = new TMInteractionHandeler(getContext());
        postAnalylisListsGenerator = new TMPostAnalylisListsGenerator(getContext());
        TMInteractionHelper interactionHelper2 = new TMInteractionHelper(getContext());
        interactionHelper = interactionHelper2;
        interactionAnalysisListGenerator = new TMInteractionAnalysisListGenerator(interactionHelper2.getLikeCommentList(pref.getSavedLikeCommentList()), pref.getMainList());
        buttonList.clear();
        buttonList2.clear();
        buttonList3.clear();
        buttonList.add(new TMFollowAnalysisModel("Don't Follow Me Back", R.drawable.ic_don_t_follow_me_back, 0, followerAnalysisListsCounts.getListCount(0),R.color.function1,true));
        buttonList.add(new TMFollowAnalysisModel("I Don't Follow Back", R.drawable.ic_i_don_t__follow_back, 1, followerAnalysisListsCounts.getListCount(1),R.color.function2,true));
        buttonList.add(new TMFollowAnalysisModel("My Mutual \nFriends", R.drawable.ic_my_mutual_friends, 2, followerAnalysisListsCounts.getListCount(2),R.color.function3,true));
        buttonList.add(new TMFollowAnalysisModel("Who Remove Me", R.drawable.ic_who_is__unfollowed_me, 4, followerAnalysisListsCounts.getListCount(4),R.color.function4,true));
        buttonList.add(new TMFollowAnalysisModel("Who Added Me", R.drawable.ic_who_recently_followed_me, 5, followerAnalysisListsCounts.getListCount(5),R.color.function5,true));
        recyclerView1.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        if (recyclerView1.getItemDecorationCount()==0){
            recyclerView1.addItemDecoration(new TMGridSpacingItemDecoration(2,30,false));
        }
        recyclerView1.setAdapter(new TMFollowAnalysisAdapter(buttonList, view.getContext(), (Activity) null));
        buttonList2.add(new TMMainActivityButtonModel("Most View Videos", R.drawable.ic_my_most_viewed_videos, 6, (postAnalylisListsGenerator.getPostList(buttonList2.size(), 1).size() + postAnalylisListsGenerator.getPostList(buttonList2.size(), 2).size()) + ""));
        buttonList2.add(new TMMainActivityButtonModel("Less Viewed Videos", R.drawable.ic_my_less_viewed_videos, 7, postAnalylisListsGenerator.getPostList(1, 2).size() + ""));
        buttonList2.add(new TMMainActivityButtonModel("Most Liked Posts", R.drawable.ic_my_most_liked_posts, 8, (postAnalylisListsGenerator.getPostList(buttonList2.size(), 1).size() + postAnalylisListsGenerator.getPostList(buttonList2.size(), 2).size()) + ""));
        buttonList2.add(new TMMainActivityButtonModel("Less Liked Posts", R.drawable.ic_my_less_liked_posts, 9, (postAnalylisListsGenerator.getPostList(buttonList2.size(), 1).size() + postAnalylisListsGenerator.getPostList(buttonList2.size(), 2).size()) + ""));
        buttonList2.add(new TMMainActivityButtonModel("Most Commented Posts", R.drawable.ic_most_comment_on_my_posts, 12, (postAnalylisListsGenerator.getPostList(6, 1).size() + postAnalylisListsGenerator.getPostList(6, 2).size()) + ""));
        buttonList2.add(new TMMainActivityButtonModel("Less Commented Posts", R.drawable.ic_less_comment_on_my_posts, 13, (postAnalylisListsGenerator.getPostList(7, 1).size() + postAnalylisListsGenerator.getPostList(7, 2).size()) + ""));
        buttonList2.add(new TMMainActivityButtonModel("Most Popular Posts", R.drawable.ic_my_most_popular_post, 10, (postAnalylisListsGenerator.getPostList(4, 1).size() + postAnalylisListsGenerator.getPostList(4, 2).size()) + ""));
        buttonList2.add(new TMMainActivityButtonModel("Less Popular Posts", R.drawable.ic_my_less_popular_post, 11, (postAnalylisListsGenerator.getPostList(5, 1).size() + postAnalylisListsGenerator.getPostList(5, 2).size()) + ""));
        recyclerView2.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        if (recyclerView2.getItemDecorationCount()==0){
            recyclerView2.addItemDecoration(new TMGridSpacingItemDecoration(2,30,false));
        }

        recyclerView2.setAdapter(new TMMainListAdapter(buttonList2, view.getContext(), (Activity) null));
        buttonList3.add(new TMMainActivityButtonModel("Who Liked My Posts The Most", R.drawable.ic_who_liked_my_posts, 14, interactionAnalysisListGenerator.getInteractionList(buttonList3.size()).size() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Who Commented The Most", R.drawable.ic_who_comment_my_posts, 15, interactionAnalysisListGenerator.getInteractionList(this.buttonList3.size()).size() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Who Interacted The Most", R.drawable.ic_who_interact_my_posts, 16, interactionAnalysisListGenerator.getInteractionList(this.buttonList3.size()).size() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Followers With Least Likes", R.drawable.ic_follower_with_low_likes, 17, interactionAnalysisListGenerator.getInteractionList(this.buttonList3.size()).size() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Followers With Least Comments", R.drawable.ic_follower_with_low_comment, 18, interactionAnalysisListGenerator.getInteractionList(this.buttonList3.size()).size() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Followers With Least Interaction", R.drawable.ic_follower_with_low_interaction, 19, interactionAnalysisListGenerator.getInteractionList(this.buttonList3.size()).size() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Mutual Followers With Least Interaction", R.drawable.ic_mutual_follower_low_interaction, 20, interactionAnalysisListGenerator.getInteractionList(this.buttonList3.size()).size() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Who Don't Follow But Interacted Most", R.drawable.ic_who_interacted_most_me, 21, interactionAnalysisListGenerator.getInteractionList(this.buttonList3.size()).size() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Who Removed Their Likes", R.drawable.ic_who_remove_their_like, 22, pref.getWhoDeletedLikesOrComments(0).length() + ""));
        buttonList3.add(new TMMainActivityButtonModel("Who Removed Their Comments", R.drawable.ic_who_remove_their_comment, 23, pref.getWhoDeletedLikesOrComments(1).length() + ""));
        recyclerView3.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        if (recyclerView3.getItemDecorationCount()==0){
            recyclerView3.addItemDecoration(new TMGridSpacingItemDecoration(2,30,false));
        }

        recyclerView3.setAdapter(new TMMainListAdapter(this.buttonList3, view.getContext(), (Activity) null));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpdateLayout(false);
        setButtons();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
