package com.templatemela.instagramzy.myadapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.magic_text.TMMagicTextActivity;
import com.templatemela.instagramzy.models.TMMainActivityButtonModel;
import com.templatemela.instagramzy.activitys.TMCaptionTypeActivity;

import com.templatemela.instagramzy.activitys.TMDownloadedViewActivity;
import com.templatemela.instagramzy.activitys.TMHashTagActivity;
import com.templatemela.instagramzy.activitys.TMInteractionScannerActivity;
import com.templatemela.instagramzy.activitys.TMListActivity;
import com.templatemela.instagramzy.activitys.TMPostActivity;
import com.templatemela.instagramzy.activitys.TMSearchActivity;
import com.templatemela.instagramzy.activitys.TMSentRequestActivity;
import com.templatemela.instagramzy.utils.TMAdsUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class TMMainListAdapter extends RecyclerView.Adapter<TMMainListAdapter.ViewHolder>implements EasyPermissions.PermissionCallbacks {
    Activity activity;
    Intent cIntent;
    Context context;
    Intent fIntent;
    Intent iIntent;
    List<TMMainActivityButtonModel> list;
    Intent pIntent;
    int permissionStatus;
    String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    Pref pref;
    Intent sIntent;
    public static int requestMode = 1;
    String storagePermission = "android.permission.WRITE_EXTERNAL_STORAGE";
    private String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    public int PERMISSION_REQUEST_CODE = 55;

    public TMMainListAdapter(List<TMMainActivityButtonModel> list2, Context context2, Activity activity2) {
        this.list = list2;
        this.context = context2;
        this.activity = activity2;
        this.fIntent = new Intent(context2, TMListActivity.class);
        this.pIntent = new Intent(context2, TMPostActivity.class);
        this.iIntent = new Intent(context2, TMInteractionScannerActivity.class);
        this.sIntent = new Intent(context2, TMSearchActivity.class);

        this.pref = new Pref(context2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.button_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final TMMainActivityButtonModel mainActivityButtonModel = this.list.get(i);
        Glide.with(this.context).load(Integer.valueOf(mainActivityButtonModel.getPicture())).into(viewHolder.icon);
        TMAdsUtils.initAd(context);
        TMAdsUtils.loadInterAd(context);
        viewHolder.textView.setText(mainActivityButtonModel.getText());
        if (!mainActivityButtonModel.getListCount().trim().equals("0")) {
            viewHolder.listCountTv.setText(mainActivityButtonModel.getListCount() + "");
        } else {
            viewHolder.listCountTv.setText("");
        }
        viewHolder.card.setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View view) {
                switch (mainActivityButtonModel.getId()) {
                    case 0:
                        fIntent.putExtra("type", 0);
                        fIntent.putExtra("title", " Don't Follow Me Back");
                        TMAdsUtils.showInterAd(context,fIntent);
                     
                        return;
                    case 1:
                        fIntent.putExtra("type", 1);
                        fIntent.putExtra("title", " I Don't Follow Back");
                        TMAdsUtils.showInterAd(context,fIntent);
                        return;
                    case 2:
                        fIntent.putExtra("type", 2);
                        fIntent.putExtra("title", " Mutual Followers");
                       TMAdsUtils.showInterAd(context,fIntent);
                        return;
                    case 3:
                        context.startActivity(new Intent(context, TMSentRequestActivity.class));
                        return;
                    case 4:
                        fIntent.putExtra("type", 4);
                        fIntent.putExtra("title", " Unfollowed You Recently");
                       TMAdsUtils.showInterAd(context,fIntent);
                        return;
                    case 5:
                        fIntent.putExtra("type", 5);
                        fIntent.putExtra("title", " Followed You Recently");
                       TMAdsUtils.showInterAd(context,fIntent);
                        return;
                    case 6:
                        pIntent.putExtra("type", 6);
                        pIntent.putExtra("title", " Most Viewed Videos");
                        TMAdsUtils.showInterAd(context,pIntent);
                       
                        return;
                    case 7:
                        pIntent.putExtra("type", 7);
                        pIntent.putExtra("title", " Less Viewed Videos");
                        TMAdsUtils.showInterAd(context,pIntent);
                        return;
                    case 8:
                        pIntent.putExtra("type", 8);
                        pIntent.putExtra("title", " Most Liked Posts");
                        TMAdsUtils.showInterAd(context,pIntent);
                        return;
                    case 9:
                        pIntent.putExtra("type", 9);
                        pIntent.putExtra("title", " Less Liked Posts");
                        TMAdsUtils.showInterAd(context,pIntent);
                        return;
                    case 10:
                        pIntent.putExtra("type", 10);
                        pIntent.putExtra("title", " Most Popular Posts");
                        TMAdsUtils.showInterAd(context,pIntent);
                        return;
                    case 11:
                        pIntent.putExtra("type", 11);
                        pIntent.putExtra("title", " Less Popular Posts");
                        TMAdsUtils.showInterAd(context,pIntent);
                        return;
                    case 12:
                        pIntent.putExtra("type", 12);
                        pIntent.putExtra("title", " Most Commented Posts");
                        TMAdsUtils.showInterAd(context,pIntent);
                        return;
                    case 13:
                        pIntent.putExtra("type", 13);
                        pIntent.putExtra("title", " Less Commented Posts");
                        TMAdsUtils.showInterAd(context,pIntent);
                        return;
                    case 14:
                        iIntent.putExtra("type", 14);
                        iIntent.putExtra("title", " Who Liked The Most");
                        TMAdsUtils.showInterAd(context,iIntent);
                        
                        return;
                    case 15:
                        iIntent.putExtra("type", 15);
                        iIntent.putExtra("title", " Who Commented The Most");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 16:
                        iIntent.putExtra("type", 16);
                        iIntent.putExtra("title", " Who Interacted The Most");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 17:
                        iIntent.putExtra("type", 17);
                        iIntent.putExtra("title", " Follower With Less Like");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 18:
                        iIntent.putExtra("type", 18);
                        iIntent.putExtra("title", " Follower With Less Comment");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 19:
                        iIntent.putExtra("type", 19);
                        iIntent.putExtra("title", " Follower With Least Interaction");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 20:
                        iIntent.putExtra("type", 20);
                        iIntent.putExtra("title", " Mutual With Less Interaction");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 21:
                        iIntent.putExtra("type", 21);
                        iIntent.putExtra("title", " Don't Follow But Interacted Most");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 22:
                        iIntent.putExtra("type", 22);
                        iIntent.putExtra("title", " Removed Their Likes");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 23:
                        iIntent.putExtra("type", 23);
                        iIntent.putExtra("title", " Remove Their Comments");
                         TMAdsUtils.showInterAd(context,iIntent);
                        return;
                    case 24:
                        if (pref.getPermissionState(storagePermission) == 0 || ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                            sIntent.putExtra("type", 0);
                            sIntent.putExtra("title", " Download Dp");
                            TMAdsUtils.showInterAd(context,sIntent);
                            return;
                        } else if (pref.getPermissionState(storagePermission) == 2) {
                            deniedPermanently();
                            return;
                        } else {
                            boolean unused = checkPermissions();
                            return;
                        }
                    case 25:
                        if (pref.getPermissionState(storagePermission) == 0 || ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                            sIntent.putExtra("type", 1);
                            sIntent.putExtra("title", " Download Highlights");
                            TMAdsUtils.showInterAd(context,sIntent);
                           
                            return;
                        } else if (pref.getPermissionState(storagePermission) == 2) {
                            deniedPermanently();
                            return;
                        } else {
                            boolean unused2 = checkPermissions();
                            return;
                        }
                    case 26:
                        if (pref.getPermissionState(storagePermission) == 0 || ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                            sIntent.putExtra("type", 2);
                            sIntent.putExtra("title", " Download Stories");
                            TMAdsUtils.showInterAd(context,sIntent);
                            return;
                        } else if (pref.getPermissionState(storagePermission) == 2) {
                            deniedPermanently();
                            return;
                        } else {
                            boolean unused3 = checkPermissions();
                            return;
                        }
                    case 27:
                        TMAdsUtils.showInterAd(context,new Intent(context, TMDownloadedViewActivity.class));

                        return;
                    case 28:
                        TMAdsUtils.showInterAd(context,new Intent(context, TMCaptionTypeActivity.class));

                        return;
                    case 29:
                        TMAdsUtils.showInterAd(context,new Intent(context, TMHashTagActivity.class));
                        return;
                    case 30:
                       /* if (pref.getPermissionState(storagePermission) == 0 || ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                            cIntent = new Intent(context, Croper.class);
                            cIntent.putExtra("type", 3);
                            context.startActivity(cIntent);
                            return;
                        } else if (pref.getPermissionState(storagePermission) == 2) {
                            deniedPermanently();
                            return;
                        } else {
                            boolean unused4 = checkPermissions();
                            return;
                        }*/
                        if (hasStoragePermission(perms)) {
                            if (Build.VERSION.SDK_INT >= 26) {
                                checkFolder();
                            }
                            pickFromGallery(13);
                            return;
                        }
                        requestmyPermission(PERMISSION_REQUEST_CODE, perms);
                    case 31:
                      /*  if (pref.getPermissionState(storagePermission) == 0 || ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                            cIntent.putExtra("type", 0);
                            context.startActivity(cIntent);
                            return;
                        } else if (pref.getPermissionState(storagePermission) == 2) {
                            deniedPermanently();
                            return;
                        } else {
                            boolean unused5 = checkPermissions();
                            return;
                        }*/

                        if (hasStoragePermission(perms)) {
                            if (Build.VERSION.SDK_INT >= 26) {
                                checkFolder();
                            }
                            pickFromGallery(12);
                            return;
                        }
                        requestmyPermission(PERMISSION_REQUEST_CODE, perms);
                        return;
                    case 32:
                       /* if (pref.getPermissionState(storagePermission) == 0 || ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                            cIntent = new Intent(context, Croper.class);
                            cIntent.putExtra("type", 1);
                            context.startActivity(cIntent);
                            return;
                        } else if (pref.getPermissionState(storagePermission) == 2) {
                            deniedPermanently();
                            return;
                        } else {
                            boolean unused6 = checkPermissions();
                            return;
                        }*/
                        if (hasStoragePermission(perms)) {
                            if (Build.VERSION.SDK_INT >= 26) {
                                checkFolder();
                            }
                            pickFromGallery(11);
                            return;
                        }
                        requestmyPermission(PERMISSION_REQUEST_CODE, perms);
                        case 34:
                            Intent intent= new Intent(context, TMMagicTextActivity.class);
                            TMAdsUtils.showInterAd(context,intent);
                        return;

                        default:
                        return;
                }
            }
        });
    }


    public void initAffterPermission() {
        if (Build.VERSION.SDK_INT >= 26) {
            checkFolder();
        }
        pickFromGallery(1);
    }

    private void pickFromGallery(int code) {
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        ((Activity) context).startActivityForResult(intent, code);
    }

    @Override
    public void onPermissionsGranted(int i, List<String> list) {
        if (i == PERMISSION_REQUEST_CODE) {
            try {
                if (list.size() == 2) {
                    initAffterPermission();
                }
            } catch (Exception unused) {
            }
        }
    }





    @Override
    public void onPermissionsDenied(int i, List<String> list) {
        if (i != PERMISSION_REQUEST_CODE && EasyPermissions.somePermissionPermanentlyDenied((Activity) context, list)) {
            new AppSettingsDialog.Builder((Activity) context).build().show();
        }
    }

    public void requestmyPermission(int i, String... strArr) {
        EasyPermissions.requestPermissions((Activity) context, "Permission denied!\nWe need these permissions to run this app.", i, strArr);
    }
    private boolean hasStoragePermission(String... strArr) {
        return EasyPermissions.hasPermissions(context, strArr);
    }


    public void checkFolder() {
        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + context.getString(R.string.folder_name) + File.separator + context.getString(R.string.folder_panorama));
        boolean exists = file.exists();
        StringBuilder sb = new StringBuilder();
        sb.append("checkFolder: ");
        sb.append(exists);
        Log.e("checkFolder", sb.toString());
        if (!exists) {
            file.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file2 = new File(Environment.getExternalStorageDirectory().toString() + File.separator + context.getString(R.string.folder_name) + File.separator + context.getString(R.string.folder_grid));
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File file3 = new File(Environment.getExternalStorageDirectory().toString() + File.separator + context.getString(R.string.folder_name) + File.separator + context.getString(R.string.folder_crop));
        if (!file3.exists()) {
            file3.mkdirs();
        }
    }

    public void deniedPermanently() {
        new AlertDialog.Builder(this.context).setTitle("Permissions Required").setMessage("You have forcefully denied some of the required permissions for this action. Please open settings, go to permissions and allow them.").setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", context.getPackageName(), (String) null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setCancelable(false).create().show();
    }

   
    public boolean checkPermissions() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.permissions) {
            if (ContextCompat.checkSelfPermission(this.activity, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this.activity, (String[]) arrayList.toArray(new String[arrayList.size()]), 100);
        return false;
    }

    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView icon;
        TextView listCountTv;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.card = (CardView) view.findViewById(R.id.card);
            this.icon = (ImageView) view.findViewById(R.id.icon);
            this.textView = (TextView) view.findViewById(R.id.text);
            this.listCountTv = (TextView) view.findViewById(R.id.listCount);
        }
    }
}
