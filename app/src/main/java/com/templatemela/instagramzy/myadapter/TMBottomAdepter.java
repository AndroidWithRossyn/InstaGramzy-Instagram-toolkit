package com.templatemela.instagramzy.myadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMBottomItemModel;

import java.util.List;

public class TMBottomAdepter extends RecyclerView.Adapter<TMBottomAdepter.MyView> {

    // List with String type
    private List<TMBottomItemModel> bottomItemModels;
    int row_index=0;
    BottomItemClick bottomItemClick;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        // Text View
        ImageView textView;

        CardView cardview;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view) {
            super(view);

            // initialise TextView with id
            textView = (ImageView) view.findViewById(R.id.image);
            cardview = (CardView) view.findViewById(R.id.cardview);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public TMBottomAdepter(List<TMBottomItemModel> horizontalList, BottomItemClick bottomItemClick) {
        this.bottomItemClick=bottomItemClick;
        this.bottomItemModels = horizontalList;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType) {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_item, parent, false);

        // return itemView
        return new MyView(itemView);
    }


    public void refreshBottomBar(int pos){
        row_index =pos;
        notifyDataSetChanged();
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final MyView holder,
                                 final int position) {

        // Set the text of each item of
        // Recycler view with the list items
        holder.textView.setImageResource(bottomItemModels.get(position).getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                bottomItemClick.onBottomItemClick(position);

            }
        });

        if (row_index==position){
            holder.cardview.setBackgroundResource(R.drawable.gradiant_btn);
        }
        else {
            holder.cardview.setBackgroundResource(android.R.color.transparent);
        }
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return bottomItemModels.size();
    }


    public interface BottomItemClick{

        public  void onBottomItemClick(int pos);
    }
}