package com.templatemela.instagramzy.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.nativetemplates.TemplateView;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.common.TMLoginRequiredDialog;
import com.templatemela.instagramzy.utils.TMAdsUtils;
import com.templatemela.instagramzy.utils.TMGridSpacingItemDecoration;
import com.templatemela.instagramzy.models.TMMainActivityButtonModel;

import com.templatemela.instagramzy.activitys.TMErrorActivity;
import com.templatemela.instagramzy.myadapter.TMMainListAdapter;
import com.templatemela.instagramzy.handeler.TMUserDetails;

import java.util.ArrayList;
import java.util.List;

public class TMDownloadFilesFragment extends Fragment {
    Activity activity;
    CardView button;

    List<TMMainActivityButtonModel> buttonList;
    EditText editText;
    String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    Pref pref;
    RecyclerView recyclerView;
    TemplateView my_template;

public TMDownloadFilesFragment(){

}
    public TMDownloadFilesFragment(Activity activity2) {
        activity = activity2;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.download_fragment, viewGroup, false);
        buttonList = new ArrayList();
        pref = new Pref(getContext());
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyler_view);
        my_template=(TemplateView)inflate.findViewById(R.id.my_template);
        TMAdsUtils.initAd(getContext());
        TMAdsUtils.loadNativeAd(getContext(),my_template);
        buttonList.add(new TMMainActivityButtonModel("Download Dp", R.drawable.ic_download_dp, 24));
        buttonList.add(new TMMainActivityButtonModel("Save Highlights", R.drawable.ic_save_highlights, 25));
        buttonList.add(new TMMainActivityButtonModel("Download Stories", R.drawable.ic_download_stories, 26));
        buttonList.add(new TMMainActivityButtonModel("My Saved Files", R.drawable.ic_downloaded_files, 27));
        recyclerView.setLayoutManager(new GridLayoutManager(inflate.getContext(), 2));
        if (recyclerView.getItemDecorationCount()==0){
            recyclerView.addItemDecoration(new TMGridSpacingItemDecoration(2,40,false));
        }
        recyclerView.setAdapter(new TMMainListAdapter(buttonList, inflate.getContext(), activity));
        editText = (EditText) inflate.findViewById(R.id.editText);
        button = (CardView) inflate.findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new Functions(getContext()).internetIsConnected()) {
                    startActivity(new Intent(getContext(), TMErrorActivity.class));
                } else if (!new TMUserDetails(getContext()).isLoggedIn()) {
                    new TMLoginRequiredDialog(getContext()).showDialog();
                } else {
                    String obj = editText.getText().toString();
                    if (obj.equals("")) {
                        Toast.makeText(getContext(), "Paste a post url to download the post", Toast.LENGTH_LONG).show();
                    } else if (!obj.contains("https://www.instagram.com/")) {
                        Toast.makeText(getContext(), "Invalid post url", Toast.LENGTH_LONG).show();
                    } else {
                        int permissionState = new Pref(getContext()).getPermissionState("android.permission.WRITE_EXTERNAL_STORAGE");
                        if (permissionState == 0 || ContextCompat.checkSelfPermission(getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {

                        } else if (permissionState == 2) {
                            deniedPermanently();
                        } else {
                            boolean unused = checkPermissions();
                        }
                    }
                }
            }
        });

        return inflate;
    }


    public void deniedPermanently() {
        new AlertDialog.Builder(getContext()).setTitle("Permissions Required").setMessage("You have forcefully denied some of the required permissions for this action. Please open settings, go to permissions and allow them.").setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", getContext().getPackageName(), (String) null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setCancelable(false).create().show();
    }


    public boolean checkPermissions() {
        ArrayList arrayList = new ArrayList();
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(activity, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(activity, (String[]) arrayList.toArray(new String[arrayList.size()]), 100);
        return false;
    }
}
