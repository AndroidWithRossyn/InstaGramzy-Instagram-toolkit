package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMChatModel;
import com.templatemela.instagramzy.models.TMMainActivityButtonModel;
import com.templatemela.instagramzy.activitys.TVMessagesViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class TMChatAdapter extends RecyclerView.Adapter<TMChatAdapter.ViewHolder> {
    Context context;
    Functions functions;
    HashMap<String, Integer> lastseenHashmap;
    List<TMChatModel> list = new ArrayList();

    public TMChatAdapter(Context context2) {
        context = context2;
        functions = new Functions(context2);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chats_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final TMChatModel chatModel = list.get(i);
        viewHolder.name.setText(chatModel.getUsername());
        String lastMessage = chatModel.getLastMessage();
        if (lastMessage.length() > 18) {
            lastMessage = lastMessage.substring(0, 17).replaceAll("\n", " ") + "...";
        }
        viewHolder.msg.setText(lastMessage);
        if (chatModel.isSeen()) {
            viewHolder.activePoint.setBackgroundResource(R.drawable.seen_msg);
            viewHolder.msg.setTextColor(context.getResources().getColor(R.color.dark));
        } else {
            viewHolder.msg.setTextColor(context.getResources().getColor(R.color.heart));
            viewHolder.activePoint.setBackgroundResource(R.drawable.active_msg);
        }
        String substring = (chatModel.getTime() + "").substring(0, 13);
        long parseLong = Long.parseLong(substring);
        Log.i("ChatAdapter", substring);
        viewHolder.time.setText(functions.toTAgo(parseLong));
        try {
            String str = lastseenHashmap.get(chatModel.getUsername()) + "";
            int parseDouble = !str.equals("null") ? (int) Double.parseDouble(str) : 0;
            if (parseDouble > 0) {
                viewHolder.unseencount.setText(parseDouble + "");
                viewHolder.countHolder.setVisibility(View.VISIBLE);
            } else {
                viewHolder.countHolder.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewHolder.countHolder.setVisibility(View.GONE);
        }
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
        
        @Override
        public void onClick(View view) {
                Log.i("fser", context.getClass().getName());
                if (context.getClass().getName().equals("com.templatemela.followers.mychathead.FloatingWidgetService")) {
                    EventBus.getDefault().post(chatModel);
                    return;
                }
                Intent intent = new Intent(context, TVMessagesViewActivity.class);
                intent.putExtra("id", chatModel.getThreadId());
                intent.putExtra("dp", chatModel.getDp());
                intent.putExtra("name", chatModel.getName());
                intent.putExtra("username", chatModel.getUsername());
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(chatModel.getDp()).into((ImageView) viewHolder.dp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout countHolder;
        LinearLayout activePoint;
       ImageView dp;
        RelativeLayout item;
        TextView msg;
        TextView name;
        public TMMainActivityButtonModel textView;
        TextView time;
        TextView unseencount;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nameTv);
            msg = (TextView) view.findViewById(R.id.messageTv);
            activePoint = (LinearLayout) view.findViewById(R.id.activePoint);
            dp = (ImageView) view.findViewById(R.id.dp);
            time = (TextView) view.findViewById(R.id.time);
            unseencount = (TextView) view.findViewById(R.id.unseencount);
            countHolder = (LinearLayout) view.findViewById(R.id.countHolder);
            item = (RelativeLayout) view.findViewById(R.id.item);
        }
    }

    public void setList(List<TMChatModel> list2) {
        list = list2;
        lastseenHashmap = new Pref(context).getUnseenCount();
        notifyDataSetChanged();
    }

    public void notifyChange() {
        lastseenHashmap = new Pref(context).getUnseenCount();
        notifyDataSetChanged();
    }
}
