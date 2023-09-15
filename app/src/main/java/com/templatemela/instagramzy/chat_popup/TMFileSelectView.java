package com.templatemela.instagramzy.chat_popup;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.utils.GalleryBucketUtils;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMStrModel;
import com.yalantis.ucrop.view.CropImageView;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMFileSelectView {
    Context context;
    LayoutInflater inflater;
    TMSlectionViewAdapter slectionViewAdapter;
    View view;
    WindowManager windowManager;

    public TMFileSelectView(Context context2, WindowManager windowManager2) {
        this.context = context2;
        this.windowManager = windowManager2;
        this.inflater = (LayoutInflater) context2.getSystemService("layout_inflater");
        Sizes.setContext(context2);
    }

    public void show() {
        WindowManager.LayoutParams layoutParams;
        EventBus.getDefault().register(this);
        this.view = this.inflater.inflate(R.layout.file_selection_view, (ViewGroup) null, false);
        if (Build.VERSION.SDK_INT < 26) {
            layoutParams = new WindowManager.LayoutParams(-2, -2, 2003, 32, -3);
        } else {
            layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 32, -3);
        }
        layoutParams.gravity = 17;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = Sizes.getPixel(300);
        layoutParams.height = Sizes.getPixel(CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION);
        this.windowManager.addView(this.view, layoutParams);
        final List<GalleryBucketUtils.Bucket> imgBuckets = GalleryBucketUtils.getImgBuckets(this.context);
        String[] strArr = new String[imgBuckets.size()];
        for (int i = 0; i < imgBuckets.size(); i++) {
            strArr[i] = imgBuckets.get(i).name;
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.context, 17367048, strArr);
        RecyclerView recyclerView = (RecyclerView) this.view.findViewById(R.id.recycler_view);
        this.slectionViewAdapter = new TMSlectionViewAdapter(this.context, imgBuckets.get(0).paths);
        recyclerView.setLayoutManager(new GridLayoutManager(this.context, 3));
        recyclerView.setAdapter(this.slectionViewAdapter);
        arrayAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.folders);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                TMFileSelectView.this.slectionViewAdapter.setImages(((GalleryBucketUtils.Bucket) imgBuckets.get(i)).paths);
            }
        });
        ((ImageView) this.view.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TMFileSelectView.this.remove();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TMStrModel str) {
        if (str.getType() == 101) {
            remove();
        }
        if (str.getType() == 100) {
            if (str.getData().equals("KEYCODE_BACK")) {
                remove();
            }
            if (str.getData().equals("home")) {
                remove();
            }
        }
    }

    public void remove() {
        try {
            EventBus.getDefault().post(new TMStrModel("cancel", 102));
            EventBus.getDefault().unregister(this);
            this.windowManager.removeViewImmediate(this.view);
        } catch (Exception unused) {
        }
    }
}
