package com.templatemela.instagramzy.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.activitys.TMLoginActivity;

public class TMLoginRequiredDialog {
    Context context;
    Dialog dialog;
    Button login;
    Button notNow;

    public TMLoginRequiredDialog(final Context context2) {
        try {
            context = context2;
            dialog = new Dialog(context2);
            dialog.setContentView(R.layout.login_requried);
            notNow = (Button) dialog.findViewById(R.id.not_now);
            login = (Button) dialog.findViewById(R.id.login);
            dialog.setCancelable(false);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    ((Activity) context2).finish();
                }
            });
            notNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            login.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                    context2.startActivity(new Intent(context2, TMLoginActivity.class));
                    ((Activity) context2).finish();
                }
            });
        } catch (Exception unused) {
        }
    }

    public void showDialog() {
        try {
            dialog.show();
        } catch (Exception unused) {
        }
    }
}
