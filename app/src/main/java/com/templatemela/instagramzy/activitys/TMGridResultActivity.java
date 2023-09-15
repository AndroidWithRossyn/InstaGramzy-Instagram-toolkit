package com.templatemela.instagramzy.activitys;

import static com.templatemela.instagramzy.utils.Utitlity.GRID_PATH;
import static com.templatemela.instagramzy.utils.Utitlity.GRID_TEMP;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.templatemela.instagramzy.R;

import com.templatemela.instagramzy.utils.Utitlity;
import com.templatemela.instagramzy.interfaces.TMMyGlobalLayoutListener;
import com.templatemela.instagramzy.interfaces.TMMyRecycleItemDecoration;
import com.templatemela.instagramzy.interfaces.TMShareImgPost;
import com.templatemela.instagramzy.models.TMGridItem;
import com.templatemela.instagramzy.myadapter.OnItemClick;
import com.templatemela.instagramzy.myadapter.TMGridAdpater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TMGridResultActivity extends AppCompatActivity implements View.OnClickListener, OnItemClick {

    public TMGridAdpater PSGridAdpater;
    private LinearLayout btn_back;
    public int column;
    public String create_filename;
    public LinearLayout delete_images;
    String delete_temp_path;
    private String directory_name;
    public String directory_path;
    public String final_path;
    public Boolean from_main;
    public ArrayList<TMGridItem> grid_list_images;
    public Boolean is_save = false;
    public FrameLayout layout_loading;
    public FrameLayout mBannerParentLayout;
    public ArrayList<Integer> number_list;
    public ProgressDialog progressDialog;
    public RecyclerView recycler_view_grid;
    private String resultUri;
    public int row;
    public ImageView save_images;
    private String selected_uri;
    public String temp_path;

    public void CheckEmpty(Boolean bool) {
    }


    @Override
    public void onBackPressed() {
        back();
    }

    
    public void back() {
        delete_folder(delete_temp_path);
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
        setContentView((int) R.layout.activity_grid_result);
        findviewbyId();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        delete_temp_path = GRID_TEMP;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        Intent intent = getIntent();
        from_main = Boolean.valueOf(intent.getBooleanExtra("from_main", true));
        if (from_main.booleanValue()) {
            save_images.setVisibility(View.VISIBLE);
            delete_images.setVisibility(View.GONE);
            row = intent.getIntExtra("row_count", 1);
            column = intent.getIntExtra("column_count", 3);
            resultUri = intent.getStringExtra("resultUri");
            selected_uri = intent.getStringExtra("selected_uri");
            if (selected_uri != null) {
                selected_uri = Utitlity.getPath(this, Uri.parse(selected_uri));
                create_filename = Utitlity.getFileNameWithoutExtension(new File(selected_uri));
            }
            new saveimages().execute(new Void[0]);
            return;
        }
        save_images.setVisibility(View.GONE);
        delete_images.setVisibility(View.VISIBLE);
        grid_list_images = new ArrayList<>();
        directory_name = intent.getStringExtra("directory_name");
        directory_path = Environment.getExternalStorageDirectory().toString() + File.separator + getString(R.string.folder_name) + File.separator + getString(R.string.folder_grid) + File.separator + directory_name;
        File[] listFiles = new File(directory_path).listFiles();
        int length = listFiles.length - 1;
        for (int i = 0; i < listFiles.length; i++) {
            if (!listFiles[i].getAbsolutePath().contains("main_image")) {
                number_list.add(Integer.valueOf(length));
                grid_list_images.add(new TMGridItem(length, listFiles[i].getAbsolutePath(), false));
                length--;
            }
        }
        row = listFiles.length - 0;

        column = listFiles.length - row;
        column = column - 1;
        Collections.sort(grid_list_images, new Comparator<TMGridItem>() {
            @Override
            public int compare(TMGridItem gridItem, TMGridItem gridItem2) {
                return String.valueOf(Utitlity.getFileNameWithoutExtension(new File(gridItem.getGridImagePath()))).compareToIgnoreCase(String.valueOf(Utitlity.getFileNameWithoutExtension(new File(gridItem2.getGridImagePath()))));
            }
        });
        Collections.reverse(grid_list_images);
        PSGridAdpater = new TMGridAdpater(this, grid_list_images, number_list, 1);
        recycler_view_grid.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_view_grid.addItemDecoration(new TMMyRecycleItemDecoration(3, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f, getResources().getDisplayMetrics()), true));
        recycler_view_grid.setAdapter(PSGridAdpater);
        recycler_view_grid.getViewTreeObserver().addOnGlobalLayoutListener(new TMMyGlobalLayoutListener(recycler_view_grid, layout_loading, column, row));
        PSGridAdpater.setClickListener(this);
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

    public void findviewbyId() {
        number_list = new ArrayList<>();
        save_images = (ImageView) findViewById(R.id.save_images);
        save_images.setOnClickListener(this);
        recycler_view_grid = (RecyclerView) findViewById(R.id.recycler_view_grid);
        layout_loading = (FrameLayout) findViewById(R.id.layout_loading);
        delete_images= (LinearLayout) findViewById(R.id.delete_images);
        delete_images.setOnClickListener(this);
        btn_back = (LinearLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == R.id.delete_images) {
            DialogInterface.OnClickListener r3 = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -1) {
                        new DeleteFolder().execute(new Void[0]);
                    }
                }
            };
            new AlertDialog.Builder(this, R.style.MyDialogTheme).setMessage("Delete this photos?").setPositiveButton("Yes", r3).setNegativeButton("No", r3).show();
        } else if (id == R.id.save_images) {
            new MoveFolder().execute(new Void[0]);
        }
    }

    public void delete_folder(String str) {
        if (new File(str).exists()) {
            deleteDir(new File(str));
        }
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

    public void ShareItemClick(String str, int i, Boolean bool) {
        TMShareImgPost.ShareHandler(this, Uri.parse(str), "com.instagram.android", 1000);
        PSGridAdpater.notifyDataSetChanged();
    }

    public void CropImageInRatio() {
        grid_list_images = new ArrayList<>();
        int i = row * column;
        temp_path = Environment.getExternalStorageDirectory().toString() + File.separator +Environment.DIRECTORY_PICTURES+File.separator+ getString(R.string.folder_name) + File.separator + getString(R.string.folder_grid) + File.separator + ".temp" + File.separator + create_filename + "_" + String.valueOf(System.currentTimeMillis());
        if (!new File(temp_path).exists()) {
            new File(temp_path).mkdirs();
        }
        try {
            if (resultUri != null) {
                copyFileToDownloads(Uri.parse(resultUri), temp_path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap decodeFile = BitmapFactory.decodeFile(new File(resultUri.replace("file://", "").replace("file", "")).getPath());
        if (decodeFile != null && decodeFile.getWidth() > 0 && decodeFile.getHeight() > 0) {
            int width = decodeFile.getWidth() / column;
            int height = decodeFile.getHeight() / row;
            for (int i2 = 0; i2 < row; i2++) {
                for (int i3 = 0; i3 < column; i3++) {
                    Bitmap createBitmap = Bitmap.createBitmap(decodeFile, width * i3, height * i2, width, height);
                    number_list.add(Integer.valueOf(i));
                    SaveBitmap(createBitmap, i, temp_path);
                    i--;
                }
            }
            Collections.sort(number_list, Collections.reverseOrder());
        }
    }

    public boolean SaveBitmap(Bitmap bitmap, int i, String str) {
        int i2 = i + 1;
        File file = new File(str, String.format("%02d", new Object[]{Integer.valueOf(i)}) + ".jpeg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(file.getAbsolutePath()))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        grid_list_images.add(new TMGridItem(i2, file.getAbsolutePath(), false));
        return true;
    }

    private void copyFileToDownloads(Uri uri, String str) throws Exception {
        File file = new File(str, "main_image.png");
        FileInputStream fileInputStream = new FileInputStream(new File(uri.getPath()));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        channel.transferTo(0, channel.size(), fileOutputStream.getChannel());
        fileInputStream.close();
        fileOutputStream.close();
    }

    private class saveimages extends AsyncTask<Void, Void, Void> {
        private saveimages() {
        }

        public void onPreExecute() {
            progressDialog.show();
        }

        
        public Void doInBackground(Void... voidArr) {
            CropImageInRatio();
            return null;
        }

        
        public void onPostExecute(Void voidR) {
            progressDialog.cancel();

            PSGridAdpater = new TMGridAdpater(TMGridResultActivity.this, grid_list_images, number_list, 1);
            recycler_view_grid.setLayoutManager(new GridLayoutManager(TMGridResultActivity.this, 3));
            recycler_view_grid.addItemDecoration(new TMMyRecycleItemDecoration(3, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f, getResources().getDisplayMetrics()), true));
            recycler_view_grid.setAdapter(PSGridAdpater);
            recycler_view_grid.getViewTreeObserver().addOnGlobalLayoutListener(new TMMyGlobalLayoutListener(recycler_view_grid, layout_loading, column, row));
            PSGridAdpater.setClickListener(TMGridResultActivity.this);
        }
    }

    private class MoveFolder extends AsyncTask<Void, Void, Void> {
        private MoveFolder() {
        }


        @Override
        public void onPreExecute() {
            progressDialog.show();
        }

        @Override
        public Void doInBackground(Void... voidArr) {
            String valueOf = String.valueOf(System.currentTimeMillis());

            final_path = GRID_PATH + create_filename + "_" + valueOf;
            if (!new File(final_path).exists()) {
                new File(final_path).mkdirs();
            }
            try {
                copyDirectoryOneLocationToAnotherLocation(new File(temp_path), new File(final_path));
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }



        @Override
        public void onPostExecute(Void voidR) {
            is_save = true;
            progressDialog.cancel();
            save_images.setVisibility(View.GONE);
            delete_images.setVisibility(View.VISIBLE);
            Toast.makeText(TMGridResultActivity.this, "Image Successfully Saved To Gallery", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private class DeleteFolder extends AsyncTask<Void, Void, Void> {
        private DeleteFolder() {
        }


        @Override
        public void onPreExecute() {
            progressDialog.show();
        }


        @Override
        public Void doInBackground(Void... voidArr) {
            if (from_main.booleanValue()) {

                delete_folder(final_path);
                delete_folder(temp_path);
                return null;
            } else if (directory_path == null) {
                return null;
            } else {
                delete_folder(directory_path);
                return null;
            }
        }


        @Override
        public void onPostExecute(Void voidR) {
            progressDialog.cancel();
            onBackPressed();
        }
    }
}
