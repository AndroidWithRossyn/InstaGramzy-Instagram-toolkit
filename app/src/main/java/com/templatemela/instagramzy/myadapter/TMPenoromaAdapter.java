package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.PagerAdapter;
import com.templatemela.instagramzy.R;
import java.util.ArrayList;
import java.util.List;

public class TMPenoromaAdapter extends PagerAdapter {
    Context context;
    List<Bitmap> list;
    LayoutInflater mLayoutInflater;
    private List<View> pageList = new ArrayList();

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
    }

    
    public TMPenoromaAdapter(Context context2, List<Bitmap> list2) {
        context = context2;
        list = list2;
        mLayoutInflater = (LayoutInflater) context2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((LinearLayout) obj);
    }

    
    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = mLayoutInflater.inflate(R.layout.priview_item, viewGroup, false);
        ((ImageView) inflate.findViewById(R.id.image)).setImageBitmap(list.get(i));
        viewGroup.addView(inflate);
        pageList.add(inflate);
        return pageList.get(i);
    }
}
