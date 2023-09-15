package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMCaptionAdapter extends RecyclerView.Adapter<TMCaptionAdapter.ViewHolder> {
    Context context;
    JSONArray fJsonArray;
    Functions functions;
    JSONArray jsonArray;
    Pref pref;
    String tag = "CaptionAdapter";
    int type;

    public TMCaptionAdapter(Context context2, JSONArray jSONArray, int i) {
        context = context2;
        jsonArray = jSONArray;
        functions = new Functions(context2);
        type = i;
        pref = new Pref(context2);
        fJsonArray = new JSONArray();
        String replaceAll = pref.getBookMarks().toString().replaceAll("\\\\", "");
        Log.i(tag, replaceAll);
        Log.i(tag, jSONArray.toString());
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("content", jSONArray.getString(i2));
                if (replaceAll.contains(jSONArray.getString(i2).replaceAll("\\\\", ""))) {
                    jSONObject.put("fav", true);
                } else {
                    jSONObject.put("fav", false);
                }
                fJsonArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(tag, fJsonArray.toString());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.caption_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            final String string = fJsonArray.getJSONObject(i).getString("content");
            final Boolean valueOf = Boolean.valueOf(fJsonArray.getJSONObject(i).getBoolean("fav"));
            final JSONObject jSONObject = fJsonArray.getJSONObject(i);
            viewHolder.textView.setText(functions.getDecodedName(string));
            if (valueOf.booleanValue()) {
                Glide.with(context).load(Integer.valueOf(R.drawable.ic_baseline_favorite_24)).into(viewHolder.favBtn);

            } else {
                Glide.with(context).load(Integer.valueOf(R.drawable.ic_baseline_favorite_border_24)).into(viewHolder.favBtn);
            }
            final int i2 = i;
            final String str = string;
            viewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View view) {
                    if (!valueOf.booleanValue()) {
                        try {
                            jSONObject.put("fav", true);
                            fJsonArray.put(i2, jSONObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pref.addToBookMark(str);
                    } else {
                        try {
                            jSONObject.put("fav", false);
                            fJsonArray.put(i2, jSONObject);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        pref.removeFromBookMark(str);
                    }
                    Log.i(tag, pref.getBookMarks().toString());
                    notifyDataSetChanged();
                }
            });
            viewHolder.copy.setOnClickListener(new View.OnClickListener() {
              @Override
                public void onClick(View view) {
                    functions.copy(functions.getDecodedName(string));
                }
            });
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
              @Override
                public void onClick(View view) {
                    functions.copy(functions.getDecodedName(string));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout bookmark;
        CardView cardView;
        LinearLayout copy;
        TextView textView;
        ImageView favBtn;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.caption);
            favBtn = (ImageView) view.findViewById(R.id.favBtn);
            bookmark = (LinearLayout) view.findViewById(R.id.bookmark);
            copy = (LinearLayout) view.findViewById(R.id.copy);
            cardView = (CardView) view.findViewById(R.id.card);
        }
    }
}
