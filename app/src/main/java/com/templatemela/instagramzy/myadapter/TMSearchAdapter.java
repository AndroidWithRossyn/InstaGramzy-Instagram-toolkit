package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.activitys.TMDpViewActivity;
import com.templatemela.instagramzy.activitys.TMOtherDowloaderActivity;
import com.templatemela.instagramzy.activitys.TMViewStoriesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMSearchAdapter extends RecyclerView.Adapter<TMSearchAdapter.ViewHolder> {
    Context context;
    JSONArray jsonArray;
    int type;

    public TMSearchAdapter(Context context2, JSONArray jSONArray, int i) {
        context = context2;
        jsonArray = jSONArray;
        type = i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false));
    }

    
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            final JSONObject jSONObject = jsonArray.getJSONObject(i).getJSONObject("user");
            viewHolder.name.setText(jSONObject.getString("full_name"));
            viewHolder.username.setText(jSONObject.getString("username"));
            Glide.with(context).load(jSONObject.getString("profile_pic_url")).into(viewHolder.dp);
            viewHolder.item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (type == 0) {
                        Intent intent = new Intent(context, TMDpViewActivity.class);
                        try {
                            intent.putExtra("id", jSONObject.getString("pk"));
                            intent.putExtra("name", jSONObject.getString("full_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        context.startActivity(intent);
                    } else if (type == 1) {
                        Intent intent2 = new Intent(context, TMOtherDowloaderActivity.class);
                        try {
                            intent2.putExtra("id", jSONObject.getString("pk"));
                            intent2.putExtra("name", jSONObject.getString("full_name"));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        context.startActivity(intent2);
                    } else {
                        Intent intent3 = new Intent(context, TMViewStoriesActivity.class);
                        try {
                            intent3.putExtra("name", jSONObject.getString("username"));
                            intent3.putExtra("dp", jSONObject.getString("profile_pic_url"));
                            intent3.putExtra("id", jSONObject.getString("pk"));
                            intent3.putExtra("type", 1);
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                        context.startActivity(intent3);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewHolder.button.setVisibility(View.GONE);
    }

    
    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView button;
        ImageView dp;
        LinearLayout item;
        TextView name;
        TextView username;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nameTv);
            username = (TextView) view.findViewById(R.id.usernameTv);
            dp = (ImageView) view.findViewById(R.id.dp);
            button = (TextView) view.findViewById(R.id.button);
            item = (LinearLayout) view.findViewById(R.id.itemv);
        }
    }

    public void setJsonArray(JSONArray jSONArray) {
        jsonArray = jSONArray;
        notifyDataSetChanged();
    }
}
