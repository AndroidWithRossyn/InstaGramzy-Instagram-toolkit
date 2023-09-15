package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import org.json.JSONArray;
import org.json.JSONException;

public class TMCollectionAdapter extends RecyclerView.Adapter<TMCollectionAdapter.ViewHolder> {
    Context context;
    Functions functions;
    JSONArray jsonArray;
    Pref pref;
    String tag = "CaptionAdapter";
    int type;

    public TMCollectionAdapter(Context context2, JSONArray jSONArray, int i) {
        context = context2;
        jsonArray = jSONArray;
        functions = new Functions(context2);
        type = i;
        pref = new Pref(context2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.colletion_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            final String string = jsonArray.getString(i);
            viewHolder.textView.setText(functions.getDecodedName(string));
            viewHolder.copy.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View view) {
                    functions.copy(string);
                }
            });
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
             @Override
                public void onClick(View view) {
                    functions.copy(string);
                }
            });
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
              @Override
                public void onClick(View view) {
                    pref.removeFromHashtagCollection(string);

                    jsonArray = pref.getHashTagCollection();
                    notifyDataSetChanged();
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
        CardView cardView;
        ImageView copy;
        ImageView delete;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.caption);
            delete = (ImageView) view.findViewById(R.id.delete);
            copy = (ImageView) view.findViewById(R.id.copy);
            cardView = (CardView) view.findViewById(R.id.card);
        }
    }
}
