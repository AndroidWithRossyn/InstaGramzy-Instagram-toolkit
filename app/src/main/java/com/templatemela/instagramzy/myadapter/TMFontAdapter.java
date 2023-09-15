package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.R;
import java.util.List;

public class TMFontAdapter extends RecyclerView.Adapter<TMFontAdapter.ViewHolder> {
    Context context;
    Functions functions;
    List<String> list;

    public TMFontAdapter(Context context2, List<String> list2) {
        context = context2;
        list = list2;
        functions = new Functions(context2);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.font_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(list.get(i));
        viewHolder.copy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                functions.copy(list.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView copy;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            copy = (ImageView) view.findViewById(R.id.copy);
        }
    }

    public void setList(List<String> list2) {
        list = list2;
        notifyDataSetChanged();
    }
}
