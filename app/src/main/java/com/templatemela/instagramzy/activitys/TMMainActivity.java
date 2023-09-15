package com.templatemela.instagramzy.activitys;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomsheet.BottomSheetDialog;


import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.Utitlity;
import com.templatemela.instagramzy.interfaces.LoginInterface;
import com.templatemela.instagramzy.models.TMBottomItemModel;
import com.templatemela.instagramzy.myadapter.TMBottomAdepter;
import com.templatemela.instagramzy.myadapter.TMPageAdapter;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;




public class TMMainActivity extends AppCompatActivity implements LoginInterface , TMBottomAdepter.BottomItemClick {
    String activity = "";

    BottomSheetDialog bottomSheetDialog;
    int count = 0;
    String extra = "";
    Functions functions;
    HashMap<String, Long> hashMap;
    boolean isLoogedIn = false;
    Boolean isPremium = false;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";
    String m_androidId;
    Button notNow;
    String packageName;
    String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    Pref pref;
    Button purchase;
    private Uri selectedUri;
    boolean shouldRun = true;
    String tag = "MainActivity_";
    TextView textView;
    Thread thread;
    long tpp = 259200000;
    TMUserDetails userDetails;
    ViewPager viewPager;
    private UCrop uCrop;
    private int reqCode;
    RecyclerView bottomList;
    List<TMBottomItemModel> bottomItemModels;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    int RecyclerViewItemPosition;
    TMBottomAdepter bottomAdepter;
    private LinearLayoutManager HorizontalLayout;

    @Override
    public void onLogInComplete() {
    }



    public void onPurchase() {
    }

    
    public void setData() {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);

        bottomList = (RecyclerView) findViewById(R.id.bottom_bar_view);
        bottomItemModels=new ArrayList<>();
        bottomItemModels.add(new TMBottomItemModel(1,R.drawable.ic_home,1,1));
        bottomItemModels.add(new TMBottomItemModel(1,R.drawable.ic_post,1,1));
        bottomItemModels.add(new TMBottomItemModel(1,R.drawable.ic_message,1,1));
        bottomItemModels.add(new TMBottomItemModel(1,R.drawable.ic_download_1,1,1));
        viewPager = (ViewPager) findViewById(R.id.viewp);
        bottomAdepter=new TMBottomAdepter(bottomItemModels,this::onBottomItemClick);
        HorizontalLayout
                = new LinearLayoutManager(
                TMMainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        bottomList.setLayoutManager(HorizontalLayout);
        bottomList.setAdapter(bottomAdepter);
        functions = new Functions(this);
        pref = new Pref(this);
        userDetails = new TMUserDetails(this);
        count = pref.getLCount();
        String str = tag;
        Log.i(str, count + "");
        m_androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        extra = getIntent().getStringExtra("extra");
        int intExtra = getIntent().getIntExtra("action", 0);
        viewPager.setAdapter(new TMPageAdapter(this, this, getSupportFragmentManager(), 4));
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);

        if (intExtra == 1) {
            viewPager.setCurrentItem(2);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                bottomAdepter.refreshBottomBar(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        pref = new Pref(this);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView((int) R.layout.time_left);
        notNow = (Button) bottomSheetDialog.findViewById(R.id.not_now);
        purchase = (Button) bottomSheetDialog.findViewById(R.id.purchase);
        textView = (TextView) bottomSheetDialog.findViewById(R.id.text);
        notNow.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        checkPermissions();
        hashMap = new HashMap<>();
        hashMap.put("install_time", Long.valueOf(System.currentTimeMillis()));
        initThread();

        Log.i(tag, "Cache size " + getDirSize(getCacheDir()));
        if (getDirSize(getCacheDir()) > 20971520) {
            deleteCache();
        }
        if (!thread.isAlive()) {
            thread.start();
        }
        if (count > 20 && !pref.isRated() && count < 5000) {

        }
    }

    public long getDirSize(File file) {
        long length = 0;
        long j = 0;
        for (File file2 : file.listFiles()) {
            if (file2 == null || !file2.isDirectory()) {
                if (file2 != null && file2.isFile()) {
                    length = file2.length();
                }
            } else {
                length = getDirSize(file2);
            }
            j += length;
        }
        return j;
    }

    public void clearCache() {
        for (File delete : getCacheDir().listFiles()) {
            delete.delete();
        }
    }

    public void deleteCache() {
        try {
            deleteDir(getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
            return file.delete();
        } else if (file == null || !file.isFile()) {
            return false;
        } else {
            return file.delete();
        }
    }

    public String timeLeft(long j) {
        String str;
        long currentTimeMillis = j - System.currentTimeMillis();
        List asList = Arrays.asList(new String[]{"year", "month", "day", "hour", "minute", "second"});
        int i = 0;
        List asList2 = Arrays.asList(new Long[]{Long.valueOf(TimeUnit.DAYS.toMillis(365)), Long.valueOf(TimeUnit.DAYS.toMillis(30)), Long.valueOf(TimeUnit.DAYS.toMillis(1)), Long.valueOf(TimeUnit.HOURS.toMillis(1)), Long.valueOf(TimeUnit.MINUTES.toMillis(1)), Long.valueOf(TimeUnit.SECONDS.toMillis(1))});
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            if (i >= asList2.size()) {
                break;
            }
            long longValue = currentTimeMillis / ((Long) asList2.get(i)).longValue();
            if (longValue > 0) {
                stringBuffer.append(longValue);
                stringBuffer.append(" ");
                stringBuffer.append((String) asList.get(i));
                if (longValue != 1) {
                    str = "s";
                } else {
                    str = "";
                }
                stringBuffer.append(str);
                stringBuffer.append(" left of your free trial ");
            } else {
                i++;
            }
        }
        if ("".equals(stringBuffer.toString())) {
            return "Your free trial is over ";
        }
        return stringBuffer.toString();
    }

    
    public void initThread() {
        if (thread == null) {
            thread = new Thread(new Runnable() {
                public void run() {
                    shouldRun = true;
                    while (shouldRun) {
                        try {
                            ComponentName componentName = ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getRunningTasks(1).get(0).topActivity;
                            String className = componentName.getClassName();
                            if (!className.equals(activity) && componentName.getPackageName().equals(getPackageName())) {
                                showAd(className);
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i(tag, className);
                        } catch (Exception unused) {
                        }
                    }
                }
            });
        }
    }

    
    public void showAd(String str) {
        if (isLoogedIn) {
            activity = str;
            Log.i(tag, "......");
            if (!str.equals(getPackageName() + ".myactivities.Purchase")) {
                if (!str.equals(getPackageName() + ".myactivities.Login")) {
                    if (!str.equals(getPackageName() + ".myactivities.MainLoginScreen")) {
                        if (!str.equals(getPackageName() + ".myactivities.Rate")) {
                            int i = count + 1;
                            count = i;
                            pref.setLCount(i);
                            if (count % 8 == 0) {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Log.i(tag, "showing ads...");

                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    


    private boolean checkPermissions() {
        ArrayList arrayList = new ArrayList();
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 100);
        return false;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100) {
            if (iArr.length > 0 && iArr[0] == 0) {
                pref.writePermissionState("android.permission.WRITE_EXTERNAL_STORAGE", 0);
            } else if (shouldShowRequestPermissionRationale("android.permission.WRITE_EXTERNAL_STORAGE")) {
                pref.writePermissionState("android.permission.WRITE_EXTERNAL_STORAGE", 1);
            } else {
                pref.writePermissionState("android.permission.WRITE_EXTERNAL_STORAGE", 2);
            }
            Pref pref2 = new Pref(this);
            pref2.write("storage_requestCode", iArr[0] + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Result Called","111111111");
        Log.e("Request code", String.valueOf(requestCode));
        Log.e("result code", String.valueOf(resultCode));
        Uri uri=null;
        if(data!=null){
            uri = data.getData();
        }
        if (resultCode==-1){
            if (requestCode==11){
                reqCode=requestCode;
                selectedUri=uri;
                startCrop(uri,requestCode);
            }
            else if(requestCode==12){
                selectedUri=uri;
                reqCode=requestCode;
                startCrop(uri,requestCode);
            }
            else if(requestCode==13) {
                reqCode = requestCode;
                selectedUri = uri;
                Log.e("Exit__or__not", "onActivityResult: " + new File(Utitlity.getPath(this, selectedUri)).exists());
                if (Boolean.valueOf(new File(Utitlity.getPath(this, selectedUri)).exists()).booleanValue()) {
                    if (uri == null) {
                        Toast.makeText(this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_LONG).show();
                    } else  {
                        Log.e("selectedUri", "onActivityResult: " + selectedUri);
                        Intent intent2 = new Intent(this, TMSquareImageActivity.class);
                        intent2.putExtra("from_main", true);
                        intent2.putExtra("img_path", selectedUri.toString());
                        startActivity(intent2);
                    }
                }
            }
            else if (requestCode == 69) {
                Toast.makeText(TMMainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
                handleCropResult(data);
            }
        }
    }
    private void handleCropResult(Intent intent) {
        Uri output = UCrop.getOutput(intent);
        if (output != null) {
            int outputRowCount = UCrop.getOutputRowCount(intent) + 1;
            int outputColumnCount = UCrop.getOutputColumnCount(intent) + 1;
            Log.e("request code",String.valueOf(reqCode));
            if (reqCode == 12) {
                int sequen = outputColumnCount;
                try {
                    Intent intent2 = new Intent(this, TMPanoramaResultActivity.class);
                    intent2.putExtra("from_main", true);
                    intent2.putExtra("row_count", sequen);
                    intent2.putExtra("select_image_uri", selectedUri.toString());
                    intent2.putExtra("result_uri", output.toString());
                    startActivity(intent2);
                } catch (Exception unused) {
                }
            }
            if (reqCode == 11) {
                Intent intent3 = new Intent(this, TMGridResultActivity.class);
                intent3.putExtra("from_main", true);
                intent3.putExtra("row_count", outputRowCount);
                intent3.putExtra("column_count", outputColumnCount);
                intent3.putExtra("selected_uri", selectedUri.toString());
                intent3.putExtra("resultUri", output.toString());
                startActivity(intent3);
                return;
            }
            return;
        }
        Toast.makeText(this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_LONG).show();
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
        options.setLogoColor(ContextCompat.getColor(TMMainActivity.this, R.color.inactive_color));
     // options.setActiveWidgetColor(ContextCompat.getColor(MainActivity.this, R.color.active_color));
        options.setToolbarCancelDrawable(R.drawable.ic_back);
        if (reqCode == 12) {
            uCrop = UCrop.of(uri2, Uri.fromFile(new File(getCacheDir(), str)));
            options.setToolbarTitle("Panorama");
            options.setCropGridRowCount(1);
            options.setCropGridColumnCount(3);
            options.setAspectRatioOptions(1, new AspectRatio(ExifInterface.GPS_MEASUREMENT_2D, 2.0f, 1.0f), new AspectRatio(ExifInterface.GPS_MEASUREMENT_3D, 3.0f, 1.0f), new AspectRatio("4", 4.0f, 1.0f), new AspectRatio("5", 5.0f, 1.0f), new AspectRatio("6", 6.0f, 1.0f), new AspectRatio("7", 7.0f, 1.0f), new AspectRatio("8", 8.0f, 1.0f), new AspectRatio("9", 9.0f, 1.0f), new AspectRatio("10", 10.0f, 1.0f));
            uCrop.withOptions(options);
            uCrop.start(this);
        }
       else if (reqCode == 11) {
            Log.e("grid ","calleddddddddddd");
            uCrop = UCrop.of(uri2, Uri.fromFile(new File(getCacheDir(), str)));
            options.setToolbarTitle("Grid");
            options.setCropGridRowCount(1);
            options.setCropGridColumnCount(3);
            options.setAspectRatioOptions(0, new AspectRatio("3:1", 3.0f, 1.0f), new AspectRatio("3:2", 3.0f, 2.0f), new AspectRatio("3:3", 3.0f, 3.0f), new AspectRatio("3:4", 3.0f, 4.0f), new AspectRatio("3:5", 3.0f, 5.0f));
            options.setCompressionFormat(Bitmap.CompressFormat.PNG);
            options.setCompressionQuality(100);
            options.setHideBottomControls(false);
            options.setFreeStyleCropEnabled(false);
            uCrop.withOptions(options);
            uCrop.start(this,69);
        }

    }

    public void onLogOutComplete() {
        finish();
    }

    @Override
    public void onBottomItemClick(int pos) {
        viewPager.setCurrentItem(pos);
    }


    class UpdateChecker extends AsyncTask {
        String cverson






                ;
        String data = null;

        UpdateChecker() {
        }

        
        public Object doInBackground(Object[] objArr) {
            TMMainActivity mainActivity = TMMainActivity.this;
            mainActivity.packageName = mainActivity.getPackageName();
           /* try {
                data = Jsoup.connect("https://play.google.com/store/apps/details?id=" + packageName).get().toString();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }*/

            return null;
        }

        
        public void onPostExecute(Object obj) {
            super.onPostExecute(obj);
            try {
                String str = data;
                int indexOf = data.indexOf("htlgb", str.indexOf("IQ1z0d", data.indexOf("Current Version") + 15) + 7) + 7;
                String substring = data.substring(indexOf, data.indexOf("</span>", indexOf));
                cverson = substring;
                float parseFloat = Float.parseFloat(substring);
                Log.i("version", cverson);
                String str2 = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                float parseFloat2 = Float.parseFloat(str2);
                Log.i("version", str2);
                if (parseFloat2 < parseFloat) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TMMainActivity.this);
                    builder.setTitle("Update available");
                    builder.setMessage("New update available. Update your app to fix problems and to get new features.").setCancelable(false).setPositiveButton("Update now", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                TMMainActivity mainActivity = TMMainActivity.this;
                                mainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
                            } catch (ActivityNotFoundException unused) {
                                TMMainActivity mainActivity2 = TMMainActivity.this;
                                mainActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                            }
                        }
                    }).setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    public void onDestroy() {
        super.onDestroy();
        shouldRun = false;
    }

    public void onBackPressed() {
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(0);
          //  animatedBottomBar.setupWithViewPager(viewPager);
            return;
        }

        finish();
    }
}
