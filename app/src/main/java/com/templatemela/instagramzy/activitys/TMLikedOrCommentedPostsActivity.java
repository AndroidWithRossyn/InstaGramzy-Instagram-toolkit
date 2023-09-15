package com.templatemela.instagramzy.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.TMLikedOrCommentedPostsHelper;
import com.templatemela.instagramzy.models.TMPostModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.myadapter.TMPostAdapter;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import java.util.List;

public class TMLikedOrCommentedPostsActivity extends AppCompatActivity {
    TextView count;
    LinearLayout emptyList;
    TMLikedOrCommentedPostsHelper likedOrCommentedPostsHelper;
    List<TMPostModel> list;
    TMPostAdapter postAdapter;
    TextView posts;
    RecyclerView recyclerView;
    TextView title;
    TMUserModel user;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_liked_or_commented_posts);
        String stringExtra = getIntent().getStringExtra("id");
        int intExtra = getIntent().getIntExtra("type", 0);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        posts = (TextView) findViewById(R.id.post);
        count = (TextView) findViewById(R.id.count);
        title = (TextView) findViewById(R.id.title);
        emptyList = (LinearLayout) findViewById(R.id.empty_list);
        title.setText(getIntent().getStringExtra("title"));
        likedOrCommentedPostsHelper = new TMLikedOrCommentedPostsHelper(this);
        postAdapter = new TMPostAdapter(this);
        user = new TMUserDetails(this).getUser(false);
        List<TMPostModel> posts2 = likedOrCommentedPostsHelper.getPosts(stringExtra, intExtra);
        list = posts2;
        if (posts2.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            posts.setVisibility(View.GONE);
        }
        count.setText(list.size() + "");
        posts.setText(user.getPostCount() + "");
        postAdapter.setPostList(list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(postAdapter);
    }
}
