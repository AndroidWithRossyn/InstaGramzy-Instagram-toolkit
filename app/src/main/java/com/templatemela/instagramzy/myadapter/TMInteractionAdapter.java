package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.RateDialog;
import com.templatemela.instagramzy.utils.WebViewDialog;
import com.templatemela.instagramzy.models.TMLikerCommenterModel;
import com.templatemela.instagramzy.handeler.TMFollowUnfollowHandler;

import java.util.List;

public class TMInteractionAdapter extends RecyclerView.Adapter<TMInteractionAdapter.ViewHolder> {
    Context context;
    TMFollowUnfollowHandler followUnfollow;
    Handler handler;
    List<TMLikerCommenterModel> list;
    List<TMLikerCommenterModel> mainList;
    Pref pref;
    RateDialog rateDialog;
    int screen;
    WebViewDialog webviewDialog;

    public TMInteractionAdapter(Context context2, List<TMLikerCommenterModel> list2, List<TMLikerCommenterModel> list3, int i) {
        context = context2;
        list = list2;
        mainList = list3;
        followUnfollow = new TMFollowUnfollowHandler(context2);
        handler = new Handler(context2.getMainLooper());
        pref = new Pref(context2);
        screen = i;
        webviewDialog = new WebViewDialog(context2);
        rateDialog = new RateDialog(context2);
    }

    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.interaction_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final TMLikerCommenterModel likerCommenterModel = list.get(i);
        Glide.with(context).load(likerCommenterModel.getDp()).into((ImageView) viewHolder.dpCircleImageView);
        viewHolder.name.setText(likerCommenterModel.getName());
        viewHolder.username.setText(likerCommenterModel.getUsername());
        viewHolder.likTextView.setText(likerCommenterModel.getLikeCount() + "");
        viewHolder.comTextView.setText(likerCommenterModel.getCommentCount() + "");
       /* viewHolder.like_layout.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TMLikedOrCommentedPostsActivity.class);
                intent.putExtra("id", likerCommenterModel.getId());
                intent.putExtra("type", 0);
                intent.putExtra("title", " Liked by " + likerCommenterModel.getName());
                context.startActivity(intent);
            }
        });*/
       /* viewHolder.comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TMLikedOrCommentedPostsActivity.class);
                intent.putExtra("id", likerCommenterModel.getId());
                intent.putExtra("type", 1);
                intent.putExtra("title", " Commented by " + likerCommenterModel.getName());
                context.startActivity(intent);
            }
        });*/
        viewHolder.itemC.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {

                webviewDialog.showWebView("https://www.instagram.com/" + likerCommenterModel.getUsername() + "/");
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return list.size();
        } catch (Exception unused) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        TextView comTextView;
        RelativeLayout comment_layout;
        ImageView dpCircleImageView;
        LinearLayout itemC;
        TextView likTextView;
        RelativeLayout like_layout;
        TextView name;
        TextView username;

        public ViewHolder(View view) {
            super(view);
            dpCircleImageView = (ImageView) view.findViewById(R.id.dp);
            username = (TextView) view.findViewById(R.id.usernameTv);
            name = (TextView) view.findViewById(R.id.nameTv);
            button = (Button) view.findViewById(R.id.button);
            itemC = (LinearLayout) view.findViewById(R.id.itemv);
            likTextView = (TextView) view.findViewById(R.id.like_text);
            comTextView = (TextView) view.findViewById(R.id.comment_text);
            like_layout = (RelativeLayout) view.findViewById(R.id.like);
            comment_layout = (RelativeLayout) view.findViewById(R.id.comment);
        }
    }

    public void setList(List<TMLikerCommenterModel> list2) {
        list = list2;
        notifyDataSetChanged();
    }
}
