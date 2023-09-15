package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.R;
import java.util.List;

public class TMGridPreviewAdapter extends RecyclerView.Adapter<TMGridPreviewAdapter.ViewHolder> {
    Context context;
    List<Bitmap> list;

    public TMGridPreviewAdapter(Context context2, List<Bitmap> list2) {
        context = context2;
        list = list2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_preview_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.imageView.setImageBitmap(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            linearLayout = (LinearLayout) view.findViewById(R.id.sp);
        }
    }
}
