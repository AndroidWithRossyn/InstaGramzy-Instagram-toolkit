package com.templatemela.instagramzy.chat_popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMStrModel;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class TMSlectionViewAdapter extends RecyclerView.Adapter<TMSlectionViewAdapter.ViewHolder> {
    Context context;
    List<String> images;

    public TMSlectionViewAdapter(Context context2, List<String> list) {
        this.context = context2;
        this.images = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.image_selection_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Glide.with(this.context).load(this.images.get(i)).into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EventBus.getDefault().post(new TMStrModel(TMSlectionViewAdapter.this.images.get(i), 101));
            }
        });
    }

    public int getItemCount() {
        return this.images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image);
        }
    }

    public void setImages(List<String> list) {
        this.images = list;
        notifyDataSetChanged();
    }
}
