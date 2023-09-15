package com.templatemela.instagramzy.activitys;

import static com.templatemela.instagramzy.utils.Utitlity.PANORAMAS_PATH;
import static com.templatemela.instagramzy.utils.Utitlity.TEMP_PATH;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.TMAdsUtils;

import com.templatemela.instagramzy.utils.Utitlity;
import com.templatemela.instagramzy.myadapter.TMViewPagerAdapter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TMPanoramaResultActivity extends TMBaseActivity implements View.OnClickListener {
    private static final String CHANNEL_ID = "3000";
    private static final int DOWNLOAD_NOTIFICATION_ID_DONE = 911;
    private static final String TAG = "PSPanoramaResultActivity";
    String MY_PREFS_NAME = "share_pref";
    public String SelectFileName = "";
    LinearLayout btn_back;
  
    public TextView count;
    private LinearLayout delete_images;
    private String delete_temp_path;
  
    public ProgressDialog dialog;
    private String directory_name;
  
    public String directory_path;
    private TextView[] dots;
    private Boolean from_main;
    LinearLayout image_layout;
    ArrayList<String> images = new ArrayList<>();
  
    public Boolean is_save = false;
    LinearLayout layoutDots;

  
    public LinearLayout ll_count;
  
    public FrameLayout mBannerParentLayout;
    LinearLayout adsView;
  
    public String result_image_uri;
    ImageView save_image;
    private String select_image_uri;
    int sequen;
    ArrayList<String> sequence_images = new ArrayList<>();

  
    public String temp_path;
    ImageView uCropView;
    ViewPager viewPager;


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        delete_temp_folder(delete_temp_path);
        finish();
    }


    public boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            for (int i = 0; i < list.length; i++) {
                File file2 = new File(file, list[i]);
                boolean deleteDir = deleteDir(new File(file, list[i]));
                sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                if (!deleteDir) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public void delete_temp_folder(String str) {
        if (new File(str).exists()) {
            deleteDir(new File(str));
        }
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
        setContentView((int) R.layout.activity_result);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        adsView =(LinearLayout) findViewById(R.id.adsView);
        TMAdsUtils.initAd(TMPanoramaResultActivity.this);
        TMAdsUtils.loadBigBannerAds(TMPanoramaResultActivity.this,adsView);
        FindViewById();
        delete_temp_path = Environment.getExternalStorageDirectory().toString() + File.separator + getString(R.string.folder_name) + File.separator + getString(R.string.folder_panorama) + File.separator + ".temp";
        int i = getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams layoutParams = image_layout.getLayoutParams();
        layoutParams.height = i;
        layoutParams.width = i;
        image_layout.setLayoutParams(layoutParams);
        Intent intent = getIntent();
        from_main = Boolean.valueOf(intent.getBooleanExtra("from_main", false));
        if (from_main.booleanValue()) {
            sequen = intent.getIntExtra("row_count", 2);
            select_image_uri = intent.getStringExtra("select_image_uri");
            result_image_uri = intent.getStringExtra("result_uri");
            SelectFileName = Utitlity.getFileNameWithoutExtension(new File(Utitlity.getPath(this, Uri.parse(select_image_uri))));
            new spiltimages().execute(new Void[0]);
        } else {
            save_image.setVisibility(View.GONE);
            delete_images.setVisibility(View.VISIBLE);

            directory_name = intent.getStringExtra("directory_name");
            directory_path = Environment.getExternalStorageDirectory().toString() + File.separator + getString(R.string.folder_name) + File.separator + getString(R.string.folder_panorama) + File.separator + directory_name;
            sequence_images = new ArrayList<>();
            File[] listFiles = new File(directory_path).listFiles();
            int length = listFiles.length;
            for (int i2 = 0; i2 < listFiles.length; i2++) {
                Log.e("directory_name", "onCreate: " + listFiles[i2].getAbsolutePath());
                if (!listFiles[i2].getAbsolutePath().contains("main_image")) {
                    sequence_images.add(listFiles[i2].getAbsolutePath());
                }
            }
            Collections.sort(sequence_images, new Comparator<String>() {
                public int compare(String str, String str2) {
                    return String.valueOf(Utitlity.getFileNameWithoutExtension(new File(str))).compareToIgnoreCase(String.valueOf(Utitlity.getFileNameWithoutExtension(new File(str2))));
                }
            });
            ll_count.setVisibility(View.VISIBLE);
            viewPager.measure(-1, -2);
            viewPager.setAdapter(new TMViewPagerAdapter(this, sequence_images));
            count.setText("1/" + sequence_images.size());
            addBottomDots(0);
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
            public void onPageScrollStateChanged(int i) {
            }
            @Override
            public void onPageScrolled(int i, float f, int i2) {
            }
            @Override
            public void onPageSelected(int i) {
                addBottomDots(i);
                count.setText("" + (i + 1) + "/" + sequence_images.size());
                StringBuilder sb = new StringBuilder();
                sb.append("onPageSelected: ");
                sb.append(i);
                Log.e("onPageSelected", sb.toString());
            }
        });
    }

    public void FindViewById() {
        ll_count = (LinearLayout) findViewById(R.id.ll_count);
        btn_back = (LinearLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        image_layout = (LinearLayout) findViewById(R.id.image_layout);
        //    uCropView = (ImageView) findViewById(R.id.ucrop);
        count = (TextView) findViewById(R.id.count);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        save_image = (ImageView) findViewById(R.id.save_images);
        save_image.setOnClickListener(this);

        layoutDots = (LinearLayout) findViewById(R.id.layoutDots);
        delete_images = (LinearLayout) findViewById(R.id.delete_images);
        delete_images.setOnClickListener(this);
    }

  
    public void addBottomDots(int i) {
        TextView[] textViewArr;
        dots = new TextView[sequence_images.size()];
        layoutDots.removeAllViews();
        int i2 = 0;
        while (true) {
            textViewArr = dots;
            if (i2 >= textViewArr.length) {
                break;
            }
            textViewArr[i2] = new TextView(this);
            dots[i2].setText(Html.fromHtml("&#8226;"));
            dots[i2].setTextSize(35.0f);
            dots[i2].setTextColor(getResources().getColor(R.color.dot_dark_screen3));
            layoutDots.addView(dots[i2]);
            i2++;
        }
        if (textViewArr.length > 0) {
            textViewArr[i].setTextColor(getResources().getColor(R.color.active_color));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                return;
            case R.id.delete_images:
                if (new File(directory_path).exists()) {
                    DialogInterface.OnClickListener r4 = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == -1) {
                                delete_temp_folder(directory_path);
                                onBackPressed();
                            }
                        }
                    };
                    new AlertDialog.Builder(this, R.style.MyDialogTheme).setMessage("Delete this photos?").setPositiveButton("Yes", r4).setNegativeButton("No", r4).show();
                    return;
                }
                Toast.makeText(this, "Photo doesn't exit", Toast.LENGTH_LONG).show();
                return;

            case R.id.save_images:
                new saveimages().execute(new Void[0]);
                return;

            default:
                return;
        }
    }

    public void Spilt_images(String str) {
        try {
            if (Uri.parse(str) != null) {
                images = splitBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(str)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void SaveBitmapSpilt(Bitmap bitmap, int i, String str) {
        File file = new File(str, String.format("%02d", new Object[]{Integer.valueOf(i + 1)}) + ".jpeg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(file.getAbsolutePath()))));
            sequence_images.add(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public ArrayList<String> splitBitmap(Bitmap bitmap) {
        String.valueOf(System.currentTimeMillis());
        temp_path = TEMP_PATH + SelectFileName;
        if (!new File(temp_path).exists()) {
            new File(temp_path).mkdirs();
        }
        int i = 0;
        for (int i2 = 0; i2 < sequen; i2++) {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, i, 0, bitmap.getWidth() / sequen, bitmap.getHeight());
            i += bitmap.getWidth() / sequen;
            images.add(String.valueOf(createBitmap));
            SaveBitmapSpilt(createBitmap, i2, temp_path);
        }
        return images;
    }

  
    public void copyFileToDownloads(Uri uri, String str) throws Exception {
        File file = new File(str, "main_image.png");
        FileInputStream fileInputStream = new FileInputStream(new File(uri.getPath()));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        channel.transferTo(0, channel.size(), fileOutputStream.getChannel());
        fileInputStream.close();
        fileOutputStream.close();
    }

    public void copyDirectoryOneLocationToAnotherLocation(File file, File file2) throws IOException {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdir();
            }
            String[] list = file.list();
            for (int i = 0; i < file.listFiles().length; i++) {
                copyDirectoryOneLocationToAnotherLocation(new File(file, list[i]), new File(file2, list[i]));
            }
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileInputStream.close();
                fileOutputStream.close();
                sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(file2.getAbsolutePath()))));
                return;
            }
        }
    }

    private class saveimages extends AsyncTask<Void, Void, Void> {
        private saveimages() {
        }


        @Override
        public void onPreExecute() {
            ProgressDialog unused = dialog = new ProgressDialog(TMPanoramaResultActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }


        @Override
        public Void doInBackground(Void... voidArr) {
            String str =PANORAMAS_PATH+ SelectFileName + "_" + String.valueOf(System.currentTimeMillis());
            if (!new File(str).exists()) {
                new File(str).mkdirs();
            }
            try {
                if (result_image_uri != null) {
                    copyFileToDownloads(Uri.parse(Utitlity.getPath(TMPanoramaResultActivity.this, Uri.parse(result_image_uri))), str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                copyDirectoryOneLocationToAnotherLocation(new File(temp_path), new File(str));
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        @Override
        public void onPostExecute(Void voidR) {
            dialog.dismiss();
            is_save = true;
            save_image.setVisibility(View.GONE);
            Toast.makeText(TMPanoramaResultActivity.this, "Image Successfully Saved To Gallery", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private class spiltimages extends AsyncTask<Void, Void, Void> {
        private spiltimages() {
        }


        @Override
        public void onPreExecute() {
            dialog = new ProgressDialog(TMPanoramaResultActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }


        @Override
        public Void doInBackground(Void... voidArr) {
            Spilt_images(result_image_uri);
            return null;
        }


        @Override
        public void onPostExecute(Void voidR) {
            dialog.dismiss();
            ll_count.setVisibility(View.VISIBLE);
            viewPager.measure(-1, -2);
            viewPager.setAdapter(new TMViewPagerAdapter(TMPanoramaResultActivity.this, sequence_images));
            count.setText("1/" + sequence_images.size());
            addBottomDots(0);
        }
    }
}
