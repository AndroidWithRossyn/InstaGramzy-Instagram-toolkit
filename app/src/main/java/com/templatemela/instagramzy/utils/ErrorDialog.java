package com.templatemela.instagramzy.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.templatemela.instagramzy.R;

public class ErrorDialog {
    Button button;
    Context context;
    Dialog dialog;
    TextView textView;

    public ErrorDialog(Context context2) {
        context = context2;
        dialog = new Dialog(context2);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.error_dialog);
        button  = ((Button) dialog.findViewById(R.id.ok));
        textView = ((TextView) dialog.findViewById(R.id.message));
        button.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });
    }

    
    public void setMessage(String str) {
        textView.setText(str);
    }

    public void show() {
        dialog.show();
    }
}
