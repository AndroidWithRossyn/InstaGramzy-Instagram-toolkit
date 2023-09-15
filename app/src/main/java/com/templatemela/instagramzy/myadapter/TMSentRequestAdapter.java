package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.WebViewDialog;
import java.util.ArrayList;
import java.util.List;

public class TMSentRequestAdapter extends RecyclerView.Adapter<TMSentRequestAdapter.ViewHolder> {
    Context context;
    List<String> list;
    ArrayList<String> slist = new ArrayList<>();
    WebViewDialog webviewDialog;

    public TMSentRequestAdapter(List<String> list2, Context context2) {
        list = list2;
        context = context2;
        webviewDialog = new WebViewDialog(context2);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sent_requests_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText("@" + list.get(i));
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (viewHolder.checkBox.isChecked()) {
                    slist.add(list.get(i));
                } else {
                    slist.remove(list.get(i));
                }
            }
        });
        if (slist.contains(list.get(i))) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }
        viewHolder.itemC.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                
                webviewDialog.showWebView("https://www.instagram.com/" + list.get(i) + "/");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        LinearLayout itemC;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.usernameTv);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            itemC = (LinearLayout) view.findViewById(R.id.itemv);
        }
    }

    public ArrayList<String> getSlist() {
        return slist;
    }

    public void setList(List<String> list2) {
        list = list2;
    }
}
