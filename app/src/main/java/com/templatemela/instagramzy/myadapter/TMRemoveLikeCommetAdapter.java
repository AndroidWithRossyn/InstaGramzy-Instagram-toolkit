package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;


import de.hdodenhof.circleimageview.CircleImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMRemoveLikeCommetAdapter extends RecyclerView.Adapter<TMRemoveLikeCommetAdapter.ViewHolder> {
    Context context;
    JSONArray jsonArray;
    Pref pref;
    int type;

    public TMRemoveLikeCommetAdapter(Context context2, int i) {
        context = context2;
        type = i;
        Pref pref2 = new Pref(context2);
        pref = pref2;
        jsonArray = pref2.getWhoDeletedLikesOrComments(i - 8);
    }

    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.removed_like_comment_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            final JSONObject jSONObject = jsonArray.getJSONObject(i);
            viewHolder.username.setText(jSONObject.getString("username"));
            viewHolder.name.setText(jSONObject.getString("name"));
            TextView textView = viewHolder.text;
            textView.setText(jSONObject.getJSONArray("posts").length() + "");
            Glide.with(context).load(jSONObject.getString("dp")).into((ImageView) viewHolder.dpCircleImageView);
            if (type == 8) {
                Glide.with(context).load(Integer.valueOf(R.raw.heart)).into(viewHolder.logo);
            } else {
                Glide.with(context).load(Integer.valueOf(R.raw.chat)).into(viewHolder.logo);
            }
           /* viewHolder.logo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(context, TMLikedOrCommentedPostsActivity.class);
                    try {
                        intent.putExtra("id", jSONObject.getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("type", 2);
                    try {
                        intent.putExtra("title", " Removed by " + jSONObject.getString("name"));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    context.startActivity(intent);
                }
            });*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        CircleImageView dpCircleImageView;
        LinearLayout itemC;
        ImageView logo;
        TextView name;
        TextView text;
        TextView username;

        public ViewHolder(View view) {
            super(view);
            dpCircleImageView = (CircleImageView) view.findViewById(R.id.dp);
            username = (TextView) view.findViewById(R.id.usernameTv);
            name = (TextView) view.findViewById(R.id.nameTv);
            button = (Button) view.findViewById(R.id.button);
            itemC = (LinearLayout) view.findViewById(R.id.itemv);
            text = (TextView) view.findViewById(R.id.like_text);
            logo = (ImageView) view.findViewById(R.id.logo);
        }
    }

    public void setJsonArray() {
        jsonArray = pref.getWhoDeletedLikesOrComments(type - 8);
        notifyDataSetChanged();
    }
}
