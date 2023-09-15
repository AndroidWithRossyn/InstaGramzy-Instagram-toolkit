package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.templatemela.instagramzy.R;


import java.util.List;

public class TMColorPickerAdapter extends RecyclerView.Adapter<TMColorPickerAdapter.ViewHolder> {
  
    public List<Integer> colorPickerColors;
    private Context context;
    private LayoutInflater inflater;
  
    public OnColorPickerClickListener onColorPickerClickListener;

    public interface OnColorPickerClickListener {
        void onColorPickerClickListener(int i);
    }

    public TMColorPickerAdapter(Context context2, List<Integer> list) {
        context = context2;
        inflater = LayoutInflater.from(context2);
        colorPickerColors = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.color_picker_item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        buildColorPickerView(viewHolder.colorPickerView, colorPickerColors.get(i).intValue());
    }

    
    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }

    private void buildColorPickerView(View view, int i) {
        view.setVisibility(View.VISIBLE);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(i);
        gradientDrawable.setStroke(3, i);
        gradientDrawable.setCornerRadius(50.0f);
        view.setBackgroundDrawable(gradientDrawable);
    }

    public void setOnColorPickerClickListener(OnColorPickerClickListener onColorPickerClickListener2) {
        onColorPickerClickListener = onColorPickerClickListener2;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView colorPickerView;

        public ViewHolder(View view) {
            super(view);
            colorPickerView = (ImageView) view.findViewById(R.id.color_picker_view);
            view.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View view) {
                    if (onColorPickerClickListener != null) {
                        onColorPickerClickListener.onColorPickerClickListener(((Integer) colorPickerColors.get(getAdapterPosition())).intValue());
                    }
                }
            });
        }
    }
}
