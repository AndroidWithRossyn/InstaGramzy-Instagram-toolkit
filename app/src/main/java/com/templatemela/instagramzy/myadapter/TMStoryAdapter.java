package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMStoryModel;
import com.templatemela.instagramzy.activitys.TMViewStoriesActivity;

import java.util.List;

public class TMStoryAdapter extends RecyclerView.Adapter<TMStoryAdapter.ViewHolder> {
    Context context;
    List<TMStoryModel> list;
    int type;

    public TMStoryAdapter(Context context2, List<TMStoryModel> list2, int i) {
        context = context2;
        list = list2;
        type = i;
    }

    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (type == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.story_item, viewGroup, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.highlight_item, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final TMStoryModel storyModel = list.get(i);
        Glide.with(context).load(storyModel.getDp()).into((ImageView) viewHolder.dp);
        String username = storyModel.getUsername();
        if (username.length() > 10) {
            username = username.substring(0, 9) + "...";
        }
        if (type == 1) {
            username = storyModel.getUserId();
        }
        viewHolder.username.setText(username);
        viewHolder.dp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, TMViewStoriesActivity.class);
                intent.putExtra("id", storyModel.getStoryId());
                intent.putExtra("dp", storyModel.getDp());
                intent.putExtra("name", storyModel.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      ImageView dp;
        TextView username;

        public ViewHolder(View view) {
            super(view);
            dp = (ImageView) view.findViewById(R.id.dp);
            username = (TextView) view.findViewById(R.id.username);
        }
    }

    public void setList(List<TMStoryModel> list2) {
        list = list2;
        notifyDataSetChanged();
    }
}
