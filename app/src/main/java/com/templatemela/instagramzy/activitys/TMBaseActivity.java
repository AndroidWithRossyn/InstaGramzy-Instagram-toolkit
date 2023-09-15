package com.templatemela.instagramzy.activitys;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.templatemela.instagramzy.R;


public class TMBaseActivity extends AppCompatActivity {
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    private AlertDialog mAlertDialog;


    @Override
    public void onStop() {
        super.onStop();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }
    }


    public void requestPermission(final String str, String str2, final int i) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
            showAlertDialog(getString(R.string.permission_title_rationale), str2, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(TMBaseActivity.this, new String[]{str}, i);
                }
            }, getString(R.string.label_ok), (DialogInterface.OnClickListener) null, getString(R.string.label_cancel));
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{str}, i);
    }



    public void showAlertDialog(String str, String str2, DialogInterface.OnClickListener onClickListener, String str3, DialogInterface.OnClickListener onClickListener2, String str4) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) str);
        builder.setMessage((CharSequence) str2);
        builder.setPositiveButton((CharSequence) str3, onClickListener);
        builder.setNegativeButton((CharSequence) str4, onClickListener2);
        this.mAlertDialog = builder.show();
    }

}
