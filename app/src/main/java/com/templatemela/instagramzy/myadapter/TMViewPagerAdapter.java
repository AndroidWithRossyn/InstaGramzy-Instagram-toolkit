package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.templatemela.instagramzy.R;


import java.util.ArrayList;

public class TMViewPagerAdapter extends PagerAdapter {
    private Context context;
    ArrayList<String> images;
    private LayoutInflater layoutInflater;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public TMViewPagerAdapter(Context context2, ArrayList<String> arrayList) {
        this.context = context2;
        this.images = arrayList;
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public int getItemPosition(Object obj) {
        return super.getItemPosition(obj);
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = layoutInflater.inflate(R.layout.custom_layout, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.count);
        ((ImageView) inflate.findViewById(R.id.imageView)).setImageURI(Uri.parse(this.images.get(i)));
        ((ViewPager) viewGroup).addView(inflate, 0);
        return inflate;
    }


    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ((ViewPager) viewGroup).removeView((View) obj);
    }
}
