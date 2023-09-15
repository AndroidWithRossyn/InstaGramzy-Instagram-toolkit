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
import com.bumptech.glide.request.RequestOptions;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.activitys.TMCaptionViewActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMCaptionTypeAdapter extends RecyclerView.Adapter<TMCaptionTypeAdapter.ViewHolder> {
    String[] colorArray = {"#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#d2b4de", "#d6eaf8", "#d1f2eb", "#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#fdebd0", "#f9e79f", "#fcf3cf", "#d5f5e3", "#abebc6", "#d6eaf8", "#d5f5e3", "#fdebd0", "#fcf3cf", "#f5eef8", "#d5f5e3", "#fdebd0", "#fcf3cf", "#f9e79f", "#fdf2e9", "#fcf3cf", "#ebf5fb", "#fdedec", "#f9ebea", "#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#d2b4de", "#d6eaf8", "#d1f2eb", "#fadbd8", "#f4ecf7", "#fdedec", "#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#fdebd0", "#f9e79f", "#fcf3cf", "#fdedec", "#f9ebea", "#d2b4de", "#d6eaf8", "#d1f2eb", "#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#fdebd0", "#f9e79", "#fcf3cf", "#d5f5e3", "#abebc6", "#d6eaf8", "#d5f5e3", "#fdebd0", "#fcf3cf", "#f9e79f", "#aeb6bf", "#d6dbdf"};
    Context context;
    JSONArray jsonArray;

    public TMCaptionTypeAdapter(Context context2, JSONArray jSONArray) {
        this.context = context2;
        this.jsonArray = jSONArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.caption_type_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            final JSONObject jSONObject = this.jsonArray.getJSONObject(i);
            TextView textView = viewHolder.textView;
            textView.setText(jSONObject.getString("title") + " ");
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_image);

            Glide.with(context)
                    .load(jSONObject.getString("image"))
                    .apply(requestOptions)
                    .into(viewHolder.image);
            viewHolder.image.setOnClickListener(new View.OnClickListener() {
              @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TMCaptionTypeAdapter.this.context, TMCaptionViewActivity.class);
                    try {
                        intent.putExtra("id", jSONObject.getInt("id"));
                        intent.putExtra("title", jSONObject.getString("title"));
                        intent.putExtra("type", 0);
                        TMCaptionTypeAdapter.this.context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.text);
            this.image = (ImageView) view.findViewById(R.id.image);
        }
    }
}
