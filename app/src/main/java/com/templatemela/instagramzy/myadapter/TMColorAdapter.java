package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.interfaces.ColorAdapterInterface;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class TMColorAdapter extends RecyclerView.Adapter<TMColorAdapter.ViewHolder> {
    ColorAdapterInterface colorAdapterInterface;
    List<float[]> colorList;
    Context context;

    public TMColorAdapter(Context context2, List<float[]> list) {
        context = context2;
        colorList = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.color_item, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.linearLayout.setBackgroundColor(Color.HSVToColor(colorList.get(i)));
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EventBus.getDefault().post(colorList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear);
        }
    }
}
