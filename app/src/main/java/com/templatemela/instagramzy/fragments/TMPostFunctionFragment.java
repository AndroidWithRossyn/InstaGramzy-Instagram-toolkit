package com.templatemela.instagramzy.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.nativetemplates.TemplateView;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.TMAdsUtils;
import com.templatemela.instagramzy.utils.TMGridSpacingItemDecoration;
import com.templatemela.instagramzy.models.TMMainActivityButtonModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.myadapter.TMMainListAdapter;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TMPostFunctionFragment extends Fragment {
    Activity activity;
    List<TMMainActivityButtonModel> buttonList;
    CardView cardView;
    TextView followersC;
    TextView nameTv;
    TextView usernameTv;
    TextView followingC;
    Functions functions;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";
    TextView postC;
    Pref pref;
    RecyclerView recyclerView;
    TextView title;
    TMUserModel user;
    TMUserDetails userDetails;
    TemplateView my_template;
    private UCrop uCrop;

    public TMPostFunctionFragment(Activity activity2) {
        activity = activity2;
    }

    public TMPostFunctionFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.post_adapter, viewGroup, false);
         buttonList = new ArrayList();
         userDetails = new TMUserDetails(getContext());
         user = userDetails.getUser(false);
         functions = new Functions(getContext());
         pref = new Pref(getContext());
         followersC = (TextView) inflate.findViewById(R.id.follower_c);
        my_template=(TemplateView) inflate.findViewById(R.id.my_template);
        TMAdsUtils.initAd(getContext());
        TMAdsUtils.loadNativeAd(getContext(),my_template);
        nameTv = (TextView) inflate.findViewById(R.id.nameTv);
        usernameTv = (TextView) inflate.findViewById(R.id.usernameTv);
         followingC = (TextView) inflate.findViewById(R.id.following_c);
        postC = (TextView) inflate.findViewById(R.id.post_c);
        try {

            followingC.setText(user.getFollowing() + "");
            followersC.setText(user.getFollowers() + "");
            usernameTv.setText("@" + user.getUsername());
            nameTv.setText(user.getName());
            postC.setText(user.getPostCount() + "");
        } catch (Exception unused) {
        }
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyler_view);
        cardView = (CardView) inflate.findViewById(R.id.card);
        buttonList.add(new TMMainActivityButtonModel("Captions", R.drawable.ic_captions, 28));
        buttonList.add(new TMMainActivityButtonModel("Generate Hashtag", R.drawable.ic_get_generate_hashtag, 29));
        buttonList.add(new TMMainActivityButtonModel("Shadow Adder", R.drawable.ic_shadow_adder, 30));
        buttonList.add(new TMMainActivityButtonModel("Panoroma Post", R.drawable.ic_panorama_post, 31));
        buttonList.add(new TMMainActivityButtonModel("Grid Maker", R.drawable.ic_grid_maker, 32));
        buttonList.add(new TMMainActivityButtonModel("Stylish Fonts", R.drawable.ic_stylish_font, 34));
        recyclerView.setLayoutManager(new GridLayoutManager(inflate.getContext(), 2));
        if (recyclerView.getItemDecorationCount()==0){
            recyclerView.addItemDecoration(new TMGridSpacingItemDecoration(2,30,false));
        }
        recyclerView.setAdapter(new TMMainListAdapter(buttonList, inflate.getContext(), activity));
        return inflate;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("Result Called","111111111");
        Log.e("Request code", String.valueOf(requestCode));
        Log.e("result code", String.valueOf(resultCode));

        Uri uri = data.getData();
        if (resultCode==-1){
            if (requestCode==11){
                startCrop(uri,resultCode);
            }
            else if(requestCode==12){

            }
            else{
                if (requestCode==13){

                }
            }
        }
    }



    private void startCrop(Uri uri,int reqCode) {
        Uri uri2 = uri;
        String str = SAMPLE_CROPPED_IMAGE_NAME + ".png";
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("_");
        sb.append(currentTimeMillis);
        sb.append(".png");
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(getResources().getColor(R.color.white));
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        options.setToolbarWidgetColor(getResources().getColor(R.color.txt_color));
        options.setLogoColor(ContextCompat.getColor(getContext(), R.color.inactive_color));
      //  options.setActiveWidgetColor(ContextCompat.getColor(getContext(), R.color.active_color));
        options.setToolbarCancelDrawable(R.drawable.ic_back);
        if (reqCode == 1) {
            uCrop = UCrop.of(uri2, Uri.fromFile(new File(getContext().getCacheDir(), str)));
            options.setToolbarTitle("Panorama");
            options.setCropGridRowCount(1);
            options.setCropGridColumnCount(3);
            options.setAspectRatioOptions(1, new AspectRatio(ExifInterface.GPS_MEASUREMENT_2D, 2.0f, 1.0f), new AspectRatio(ExifInterface.GPS_MEASUREMENT_3D, 3.0f, 1.0f), new AspectRatio("4", 4.0f, 1.0f), new AspectRatio("5", 5.0f, 1.0f), new AspectRatio("6", 6.0f, 1.0f), new AspectRatio("7", 7.0f, 1.0f), new AspectRatio("8", 8.0f, 1.0f), new AspectRatio("9", 9.0f, 1.0f), new AspectRatio("10", 10.0f, 1.0f));
            uCrop.withOptions(options);
            uCrop.start(getContext(), TMPostFunctionFragment.this);
        }
        if (reqCode == 11) {
            Log.e("grid ","calleddddddddddd");
            uCrop = UCrop.of(uri2, Uri.fromFile(new File(getContext().getCacheDir(), str)));
            options.setToolbarTitle("Grid");
            options.setCropGridRowCount(1);
            options.setCropGridColumnCount(3);
            options.setAspectRatioOptions(0, new AspectRatio("3:1", 3.0f, 1.0f), new AspectRatio("3:2", 3.0f, 2.0f), new AspectRatio("3:3", 3.0f, 3.0f), new AspectRatio("3:4", 3.0f, 4.0f), new AspectRatio("3:5", 3.0f, 5.0f));
            options.setCompressionFormat(Bitmap.CompressFormat.PNG);
            options.setCompressionQuality(100);
            options.setHideBottomControls(false);
            options.setFreeStyleCropEnabled(false);
            uCrop.withOptions(options);
            uCrop.start(getContext(), TMPostFunctionFragment.this);
        }

    }
}
