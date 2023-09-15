package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.templatemela.instagramzy.R;

import com.templatemela.instagramzy.utils.Utitlity;
import com.templatemela.instagramzy.interfaces.TMShareImgPost;


import java.io.File;

public class TMSquarePreviewActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout bannerads_placement;
    private ImageView btn_back;
    private ImageView delete_image;
    private ImageView image_preview;
    public FrameLayout mBannerParentLayout;
    public String output_path;
    private ImageView share_image;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_square_preview);
        FindViewById();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        output_path= getIntent().getStringExtra("output_path");
        image_preview.setImageURI(Uri.parse(output_path));
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void FindViewById() {
        bannerads_placement = (LinearLayout) findViewById(R.id.bannerads_placement);
        image_preview = (ImageView) findViewById(R.id.image_preview);
        delete_image = (ImageView) findViewById(R.id.delete_image);
        delete_image.setOnClickListener(this);
        share_image= (ImageView) findViewById(R.id.share_image);
        share_image.setOnClickListener(this);
        btn_back = (ImageView) findViewById(R.id.backBtn);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id != R.id.delete_image) {
            if (id == R.id.share_image) {
                if (!new File(output_path).exists()) {
                    Toast.makeText(this, "Photo doesn't exit", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else if (Utitlity.isAppInstalled(this, "com.instagram.android")) {
                    TMShareImgPost.ShareHandler(this, Uri.parse(output_path), "com.instagram.android", 1000);
                } else {
                    Toast.makeText(this, "No Instagram application Install", Toast.LENGTH_LONG).show();
                }
            }
        } else if (new File(output_path).exists()) {

                        deleteFile(output_path);
                        onBackPressed();

        } else {
            Toast.makeText(this, "Photo doesn't exit", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }

    public boolean deleteFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            getContentResolver().delete(uri, "_data =?", new String[]{str});
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
        }
        return false;
    }
}
