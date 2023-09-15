package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMPostModel;
import java.util.ArrayList;
import java.util.List;

public class TMPostAdapter extends RecyclerView.Adapter<TMPostAdapter.ViewHolder> {
    Context context;
    Functions functions;
    List<TMPostModel> postList = new ArrayList();

    public TMPostAdapter(Context context2) {
        context = context2;
        functions = new Functions(context2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.post_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TMPostModel postModel = postList.get(i);
        Glide.with(context).load(postModel.getPictureUrl()).into(viewHolder.imageView);
        viewHolder.view.setText(functions.withSuffix((long) postModel.getViews()));
        viewHolder.like.setText(functions.withSuffix((long) postModel.getLikeCount()));
        viewHolder.comment.setText(functions.withSuffix((long) postModel.getCommentCount()));
        if (postModel.getType() != 2) {
            viewHolder.views_layout.setVisibility(View.GONE);
        } else {
            viewHolder.views_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView comment;
        ImageView imageView;
        TextView like;
        TextView view;
        RelativeLayout views_layout;

        public ViewHolder(View view2) {
            super(view2);
            imageView = (ImageView) view2.findViewById(R.id.image);
            view = (TextView) view2.findViewById(R.id.view_text);
            like = (TextView) view2.findViewById(R.id.like_text);
            comment = (TextView) view2.findViewById(R.id.comment_text);
            views_layout = (RelativeLayout) view2.findViewById(R.id.views_layout);
        }
    }

    public void setPostList(List<TMPostModel> list) {
        postList = list;
        notifyDataSetChanged();
    }
}
