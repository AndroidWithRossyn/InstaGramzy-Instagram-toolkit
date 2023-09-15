package com.templatemela.instagramzy.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.templatemela.instagramzy.R;

public class Loader {
    private Context context;
    private Dialog dialog;
    private TextView messageTv;
    private ProgressBar progressBar;

    public Loader(Context context2) {
        context = context2;
        dialog = new Dialog(context2);
    }

    public void showLoader() {
        dialog.setContentView(R.layout.loader);
        messageTv = (TextView) dialog.findViewById(R.id.message);
        progressBar = (ProgressBar) dialog.findViewById(R.id.progressbar);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showOnlyAnimation() {
        try {
            dialog.setContentView(R.layout.loadingview);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception unused) {
        }
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setMessage(String str) {
        messageTv.setText(str);
    }

    public void setProgress(int i) {
        progressBar.setProgress(i);
    }
}
