package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMCropSizeModels;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class TMCropSizeItemAdapter extends RecyclerView.Adapter<TMCropSizeItemAdapter.ViewHolder> {
    Context context;
    List<TMCropSizeModels> list;
    int selection = 0;

    public TMCropSizeItemAdapter(List<TMCropSizeModels> list2, Context context2) {
        list = list2;
        context = context2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.crop_size_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (list.get(i).getTitle().equals("")) {

            viewHolder.textView.setText(list.get(i).getX() + "x" + list.get(i).getY());
        } else {

            viewHolder.textView.setText(list.get(i).getTitle() + " ");
        }
        if (i == selection) {
            viewHolder.card.setBackgroundColor(context.getResources().getColor(R.color.lite_dark));
        } else {
            viewHolder.card.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                selection = i;
                notifyDataSetChanged();
                EventBus.getDefault().post(list.get(i));
            }
        });
    }

    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout card;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            card = (LinearLayout) view.findViewById(R.id.linear);
        }
    }
}
