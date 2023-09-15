package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.interfaces.HastagAdapterInterface;
import com.templatemela.instagramzy.models.TMHashTagModel;
import java.util.ArrayList;
import java.util.List;

public class TMHashTagAdapter extends RecyclerView.Adapter<TMHashTagAdapter.ViewHolder> {
    Context context;
    Functions functions;
    HastagAdapterInterface hastagAdapterInterface;
    List<TMHashTagModel> list;
    int max = 0;
    List<TMHashTagModel> selectedHashTags;
    int type;

    public TMHashTagAdapter(Context context2, List<TMHashTagModel> list2, int i) {
        context = context2;
        list = list2;
        functions = new Functions(context2);
        selectedHashTags = new ArrayList();
        hastagAdapterInterface = (HastagAdapterInterface) context2;
        type = i;
    }

    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.hashtag_item, viewGroup, false));
    }

    
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final TMHashTagModel hashTagModel = list.get(i);
        try {
            viewHolder.textView.setText("#" + hashTagModel.getName());
            if (type == 0) {
                viewHolder.postCount.setText("("+functions.prettyCount((long) hashTagModel.getPostCount())+")");
            } else if (max == hashTagModel.getPostCount()) {
                viewHolder.postCount.setText("100%");
            } else if (hashTagModel.getPostCount() > 9) {
                viewHolder.postCount.setText("90%");
            } else {
                viewHolder.postCount.setText(hashTagModel.getPostCount() + "0%");
            }
        } catch (Exception unused) {
        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (isInList(selectedHashTags, hashTagModel)) {
                    removeFromSelectedList(hashTagModel);
                    hastagAdapterInterface.onDeSelected(hashTagModel);
                } else if (selectedHashTags.size() < 30) {
                    selectedHashTags.add(hashTagModel);
                    hastagAdapterInterface.onSelected(hashTagModel);
                } else {
                    Toast.makeText(context, "You can not add more than 30 tags", 0).show();
                }
                notifyDataSetChanged();
            }
        });
        if (isInList(selectedHashTags, hashTagModel)) {
            viewHolder.cardView.setBackgroundResource(R.drawable.ic_check_fill);
        } else {
            viewHolder.cardView.setBackgroundResource(R.drawable.ic_check_unfill);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardView;
        TextView postCount;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            postCount = (TextView) view.findViewById(R.id.post_count);
            cardView = (LinearLayout) view.findViewById(R.id.card);
        }
    }

   
    public boolean isInList(List<TMHashTagModel> list2, TMHashTagModel hashTagModel) {
        int i = 0;
        while (i < list2.size() && !list2.get(i).getName().equals(hashTagModel.getName())) {
            i++;
        }
        if (i == list2.size()) {
            return false;
        }
        return true;
    }

   
    public void removeFromSelectedList(TMHashTagModel hashTagModel) {
        for (int i = 0; i < selectedHashTags.size(); i++) {
            if (selectedHashTags.get(i).getName().equals(hashTagModel.getName())) {
                Log.i("HashTag", i + "  " + hashTagModel.getName());
                selectedHashTags.remove(i);
                return;
            }
        }
    }

    public void setList(List<TMHashTagModel> list2) {
        list = list2;
    }

    public void setSelectedHashTags(List<TMHashTagModel> list2) {
        selectedHashTags = list2;
    }

    public void setMax(int i) {
        max = i;
    }
}
