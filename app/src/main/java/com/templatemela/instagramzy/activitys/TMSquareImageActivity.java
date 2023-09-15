package com.templatemela.instagramzy.activitys;

import static com.templatemela.instagramzy.utils.Utitlity.SQUARE_IMAGE_PATH;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.impulsive.zoomimageview.ZoomImageView;
import com.jackandphantom.blurimage.BlurImage;

import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.Utitlity;

import com.templatemela.instagramzy.interfaces.RecyclerViewItemClickListener;
import com.templatemela.instagramzy.myadapter.TMColorPickerAdapter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TMSquareImageActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewItemClickListener {
    private TMColorPickerAdapter PSColorPickerAdapter;
    public Bitmap bitmap;
    public ImageView blur_image;
    public LinearLayout blur_layout;

    private SeekBar blur_seekbar;
    private LinearLayout btn_back;
    private ArrayList<Integer> colorPickerColors;

    private ImageView color_piker;
    private LinearLayout color_select_layout;
  
    public int current_color_code;
  
    public int current_progress = 5;
    Dialog dialogTransparent;
    private LinearLayout first_layout;
    private FrameLayout framelayout_img;
    private Boolean from_main;
    private ImageView image_preview;
    private LinearLayout image_view_layout;
    private String imagepath;
    private ZoomImageView imageview;
    private ImageView img_backgroudn_color;
    private ImageView img_blur;
    private Boolean is_save = false;
    private LinearLayout layout_blur_btn;
    private LinearLayout layout_colorpicker_btn;
    private LinearLayout main_layout;
    private String output_path;
    private RecyclerView recycler_view_color;
    private ImageView save_image;
    private int width;

    public void onItemClick(int i, View view, String str, boolean z) {
    }

    @Override
    public void onBackPressed() {
       back();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    
    public void back() {
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
        setContentView((int) R.layout.activity_blur_image);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        findviewById();
        Intent intent = getIntent();
        from_main = Boolean.valueOf(intent.getBooleanExtra("from_main", true));
        if (from_main.booleanValue()) {
            imagepath = intent.getStringExtra("img_path");
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
            ViewGroup.LayoutParams layoutParams = main_layout.getLayoutParams();
            layoutParams.height = width;
            layoutParams.width = width;
            main_layout.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams layoutParams2 = image_view_layout.getLayoutParams();
            layoutParams2.height = width;
            layoutParams2.width = width;
            image_view_layout.setLayoutParams(layoutParams2);
            imageview.setImageURI(Uri.parse(imagepath));
            File file = new File(Utitlity.getPath(this, Uri.parse(imagepath)));
            if (file.exists()) {
                try {
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                } catch (Exception unused) {
                }
            }
            blur_seekbar.setProgress(current_progress);
            blur_image.setImageBitmap(BlurImage.with(getApplicationContext()).load(bitmap).intensity((float) current_progress).Async(true).getImageBlur());
        } else {
            output_path = intent.getStringExtra("image_path");
            save_image.setVisibility(View.GONE);
            first_layout.setVisibility(View.GONE);
            image_preview.setImageURI(Uri.parse(output_path));
        }
        blur_seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                blur_layout.setBackgroundColor(getResources().getColor(R.color.transparent));
                blur_image.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    public void findviewById() {
        image_view_layout = (LinearLayout) findViewById(R.id.image_view_layout);
        ArrayList<Integer> arrayList = new ArrayList<>();
        colorPickerColors = arrayList;
        arrayList.add(Integer.valueOf(getResources().getColor(R.color.black_50)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.black)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.blue_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.brown_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.green_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.orange_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.red_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.red_orange_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.sky_blue_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.violet_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.yellow_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.yellow_green_color_picker)));
        colorPickerColors.add(Integer.valueOf(getResources().getColor(R.color.white)));
        current_color_code = ContextCompat.getColor(this, R.color.black_50);
        color_piker = (ImageView) findViewById(R.id.color_piker);
        color_piker.setOnClickListener(this);
        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        imageview = (ZoomImageView) findViewById(R.id.imageview);
        blur_image = (ImageView) findViewById(R.id.blur_image);
        blur_seekbar = (SeekBar) findViewById(R.id.blur_seekbar);
        blur_seekbar.setOnSeekBarChangeListener(onSeekBarChanged());
        blur_layout = (LinearLayout) findViewById(R.id.blur_layout);
        layout_blur_btn = (LinearLayout) findViewById(R.id.layout_blur_btn);
        layout_blur_btn.setOnClickListener(this);
        layout_colorpicker_btn = (LinearLayout) findViewById(R.id.layout_colorpicker_btn);
        layout_colorpicker_btn.setOnClickListener(this);
        color_select_layout = (LinearLayout) findViewById(R.id.color_select_layout);
        recycler_view_color = (RecyclerView) findViewById(R.id.ColorRecyclerView);
        framelayout_img = (FrameLayout) findViewById(R.id.framelayout_img);
        save_image = (ImageView) findViewById(R.id.save_image);
        save_image.setOnClickListener(this);
        first_layout = (LinearLayout) findViewById(R.id.first_layout);
        image_preview = (ImageView) findViewById(R.id.image_preview);
        btn_back = (LinearLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        img_blur = (ImageView) findViewById(R.id.img_blur);
        img_backgroudn_color = (ImageView) findViewById(R.id.img_backgroudn_color);
        img_blur.setBackgroundResource(R.drawable.gradiant_btn);
        img_backgroudn_color.setBackgroundResource(android.R.color.transparent);

    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChanged() {
        return new SeekBar.OnSeekBarChangeListener() {
         @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Log.e("onProgressChanged", "onProgressChanged: " + i);
                if (i == 0) {
                    blur_image.setImageBitmap(bitmap);
                    return;
                }
                current_progress = i;
                blur_image.setImageBitmap(BlurImage.with(getApplicationContext()).load(bitmap).intensity((float) current_progress).Async(true).getImageBlur());
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                return;
            case R.id.color_piker:
                ColorPickerDialogBuilder.with(this, R.style.MyDialogTheme).setTitle("Choose color").initialColor(current_color_code).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setOnColorSelectedListener(new OnColorSelectedListener() {
                   @Override
                    public void onColorSelected(int i) {
                        int unused = current_color_code = i;
                    }
                }).setPositiveButton((CharSequence) "ok", (ColorPickerClickListener) new ColorPickerClickListener() {
                   @Override
                    public void onClick(DialogInterface dialogInterface, int i, Integer[] numArr) {
                        changeBackgroundColor(i);
                         current_color_code = i;
                    }
                }).setNegativeButton((CharSequence) "cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).build().show();
                return;
            case R.id.layout_blur_btn:
                blur_seekbar.setVisibility(View.VISIBLE);
                color_select_layout.setVisibility(View.GONE);
                blur_image.setVisibility(View.VISIBLE);
                blur_image.setImageBitmap(BlurImage.with(getApplicationContext()).load(bitmap).intensity((float) current_progress).Async(true).getImageBlur());
                img_blur.setBackgroundResource(R.drawable.gradiant_btn);
                img_backgroudn_color.setBackgroundResource(android.R.color.transparent);

                return;
            case R.id.layout_colorpicker_btn:
                blur_seekbar.setVisibility(View.GONE);
                color_select_layout.setVisibility(View.VISIBLE);
                img_blur.setBackgroundResource(android.R.color.transparent);;
                img_backgroudn_color.setBackgroundResource(R.drawable.gradiant_btn);
                blur_layout.setVisibility(View.VISIBLE);
                blur_image.setVisibility(View.GONE);

                changeBackgroundColor(current_color_code);
                recycler_view_color.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

                PSColorPickerAdapter = new TMColorPickerAdapter(this, colorPickerColors);
                recycler_view_color.setAdapter(PSColorPickerAdapter);
                PSColorPickerAdapter.setOnColorPickerClickListener(new TMColorPickerAdapter.OnColorPickerClickListener() {
                  @Override
                    public void onColorPickerClickListener(int i) {
                        changeBackgroundColor(i);
                    }
                });
                return;
            case R.id.save_image:
                framelayout_img.setDrawingCacheEnabled(true);
                framelayout_img.buildDrawingCache();
                Bitmap drawingCache = framelayout_img.getDrawingCache();
                String str = SQUARE_IMAGE_PATH;
                if (!new File(str).exists()) {
                    new File(str).mkdirs();
                }
                try {
                    output_path = SaveBitmap(drawingCache, str);
                } catch (Exception unused) {
                }
                save_image.setVisibility(View.GONE);
                is_save = true;
                    Intent intent = new Intent(this, TMSquarePreviewActivity.class);
                intent.putExtra("output_path", output_path);
                startActivity(intent);
                finish();
                return;
            default:
                return;
        }
    }

    public String SaveBitmap(Bitmap bitmap2, String str) {
        String valueOf = String.valueOf(System.currentTimeMillis());
        File file = new File(str, "square" + valueOf + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(file.getAbsolutePath()))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public void changeBackgroundColor(int i) {
        current_color_code = i;
        blur_image.setVisibility(View.GONE);
        blur_layout.setBackgroundColor(i);
    }
}
