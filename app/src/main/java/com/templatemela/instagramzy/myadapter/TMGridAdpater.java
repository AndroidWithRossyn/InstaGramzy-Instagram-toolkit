package com.templatemela.instagramzy.myadapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.Utitlity;
import com.templatemela.instagramzy.models.TMGridItem;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class TMGridAdpater extends RecyclerView.Adapter<TMGridAdpater.ViewHolder> {
    private static final long KiB = 1024;
    private static final long MiB = 1048576;
    private static final DecimalFormat format = new DecimalFormat("#.##");
  
    public static int sequence;
  
    public OnItemClick clickListener;
  
    public Context context;
  
    public ArrayList<TMGridItem> filesList;
  
    public ArrayList<Integer> number_list;

    static  int getSequence() {
        int i = sequence;
        sequence = i + 1;
        return i;
    }

    public TMGridAdpater(Context context2, ArrayList<TMGridItem> arrayList, ArrayList<Integer> arrayList2, int i) {
        context = context2;
        filesList = arrayList;
        number_list = arrayList2;
        sequence = i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_grid_item, viewGroup, false), context);
    }

    
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final TMGridItem gridItem = filesList.get(i);
        viewHolder.grid_image_count.setText("" + number_list.get(i));
        viewHolder.grid_image.setImageURI(Uri.parse(gridItem.getGridImagePath()));
        Log.e("number_list__", "onBindViewHolder: " + number_list.get(i));
        if (filesList.get(i).isChecked()) {
            viewHolder.share_post.setVisibility(View.VISIBLE);
            viewHolder.done.setVisibility(View.VISIBLE);
            viewHolder.grid_image_count.setVisibility(View.GONE);
        } else {
            viewHolder.share_post.setVisibility(View.GONE);
            viewHolder.done.setVisibility(View.GONE);
            viewHolder.grid_image_count.setVisibility(View.VISIBLE);
        }
        viewHolder.main_layout.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    Log.e("onClick", "onClick__gridItem: " + TMGridAdpater.sequence);
                    if (!Utitlity.isAppInstalled(context, "com.instagram.android")) {
                        Toast.makeText(context, "No Instagram application Install", Toast.LENGTH_LONG).show();
                    } else if (((Integer) number_list.get(i)).intValue() == TMGridAdpater.sequence) {
                        TMGridAdpater.getSequence();
                        ((TMGridItem) filesList.get(i)).setChecked(true);
                        clickListener.ShareItemClick(gridItem.getGridImagePath(), ((Integer) number_list.get(i)).intValue(), true);
                    } else {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_validation_share);
                        ((TextView) dialog.findViewById(R.id.txt_one)).setText("You should share post number " + TMGridAdpater.sequence + " to preserve grid order");
                        ((TextView) dialog.findViewById(R.id.txt_two)).setText("Do you really wnat to share post number " + number_list.get(i) + "?");
                        dialog.show();
                        ((TextView) dialog.findViewById(R.id.txt_cancel)).setOnClickListener(new View.OnClickListener() {
                          @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        ((TextView) dialog.findViewById(R.id.txt_continue)).setOnClickListener(new View.OnClickListener() {
                           @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                clickListener.ShareItemClick(gridItem.getGridImagePath(), ((Integer) number_list.get(i)).intValue(), true);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public void setClickListener(OnItemClick onItemClick) {
        clickListener = onItemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView done;
        ImageView grid_image;
        TextView grid_image_count;
        FrameLayout main_layout;
        LinearLayout share_post;

        public ViewHolder(View view, Context context) {
            super(view);
            grid_image = (ImageView) view.findViewById(R.id.grid_image);
            grid_image_count = (TextView) view.findViewById(R.id.grid_image_count);
            main_layout = (FrameLayout) view.findViewById(R.id.main_layout);
            share_post = (LinearLayout) view.findViewById(R.id.share_post);
            done = (ImageView) view.findViewById(R.id.done);
        }
    }
}
