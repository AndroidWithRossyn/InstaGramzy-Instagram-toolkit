package com.templatemela.instagramzy.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.templatemela.instagramzy.R;

import java.util.Random;

public class RateDialog {
    private AlertDialog alert;
   
    public Context context;
    int count = 0;
   
    public Pref functions;
    Button notNow;
    private int random;
    Button rate;
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;

    public RateDialog(final Context context2) {
        context = context2;
        Pref pref = new Pref(context2);
        functions = pref;
        if (pref.read("random").equals("null")) {
            new Random().nextInt(2);
            Log.i("random", 1 + "");
            Pref pref2 = functions;
            pref2.write("random", 1 + "");
            random = 1;
        } else {
            random = Integer.parseInt(functions.read("random"));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context2);
        builder.setTitle((CharSequence) "Free trial expired!");
        builder.setMessage((CharSequence) "Rate this app 5 star to do more cool stuffs !").setCancelable(false).setPositiveButton((CharSequence) "Rate 5 star", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context2.getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context2.getPackageName())));
                }
                functions.write("dc", "0");
            }
        }).setNeutralButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert = builder.create();
    }

    private void showNormal() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView((int) R.layout.rate_dialog);
        star1 = (ImageView) bottomSheetDialog.findViewById(R.id.star1);
        star2 = (ImageView) bottomSheetDialog.findViewById(R.id.star2);
        star3 = (ImageView) bottomSheetDialog.findViewById(R.id.star3);
        star4 = (ImageView) bottomSheetDialog.findViewById(R.id.star4);
        star5 = (ImageView) bottomSheetDialog.findViewById(R.id.star5);
        rate = (Button) bottomSheetDialog.findViewById(R.id.rate_btn);
        notNow = (Button) bottomSheetDialog.findViewById(R.id.not_now_btn);
        rate.setEnabled(false);
        rate.setTextColor(-7829368);
        final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(context);
        bottomSheetDialog2.setContentView((int) R.layout.bottom_dialog);
        final TextView textView = (TextView) bottomSheetDialog2.findViewById(R.id.title);
        final TextView textView2 = (TextView) bottomSheetDialog2.findViewById(R.id.content);
        final Button button = (Button) bottomSheetDialog2.findViewById(R.id.ok);
        ((Button) bottomSheetDialog2.findViewById(R.id.cancel)).setVisibility(View.GONE);
        star1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_border_24);
                star3.setImageResource(R.drawable.ic_baseline_star_border_24);
                star4.setImageResource(R.drawable.ic_baseline_star_border_24);
                star5.setImageResource(R.drawable.ic_baseline_star_border_24);
                rate.setEnabled(true);
                rate.setTextColor(ContextCompat.getColor(context, R.color.white));
                count = 1;
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_border_24);
                star4.setImageResource(R.drawable.ic_baseline_star_border_24);
                star5.setImageResource(R.drawable.ic_baseline_star_border_24);
                rate.setEnabled(true);
                rate.setTextColor(ContextCompat.getColor(context, R.color.white));
                count = 2;
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_24);
                star4.setImageResource(R.drawable.ic_baseline_star_border_24);
                star5.setImageResource(R.drawable.ic_baseline_star_border_24);
                rate.setEnabled(true);
                rate.setTextColor(ContextCompat.getColor(context, R.color.white));
                count = 3;
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_24);
                star4.setImageResource(R.drawable.ic_baseline_star_24);
                star5.setImageResource(R.drawable.ic_baseline_star_border_24);
                rate.setEnabled(true);
                rate.setTextColor(ContextCompat.getColor(context, R.color.white));
                count = 4;
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_24);
                star4.setImageResource(R.drawable.ic_baseline_star_24);
                star5.setImageResource(R.drawable.ic_baseline_star_24);
                rate.setEnabled(true);
                rate.setTextColor(ContextCompat.getColor(context, R.color.white));
                count = 5;
            }
        });
        final BottomSheetDialog bottomSheetDialog3 = bottomSheetDialog;
        rate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (count > 2) {
                    textView.setText("Show some love");
                    textView2.setText("Please rate us on playstore. Your honest opinion will help others to find this app");
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            try {
                                Context access$100 = context;
                                access$100.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName())));
                            } catch (ActivityNotFoundException unused) {
                                Context access$1002 = context;
                                access$1002.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
                            }
                            bottomSheetDialog2.dismiss();
                        }
                    });
                    bottomSheetDialog2.show();
                } else {
                    textView.setText("Feedback");
                    textView2.setText("Will you let us know why you don't like this app? your honest opinion will help us to improve this app.");
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.setType("message/rfc822").setData(Uri.parse("mailto:wishy.bugreports@gmail.com")).putExtra("android.intent.extra.SUBJECT", "Bugs").setPackage("com.google.android.gm");
                            context.startActivity(intent);
                            bottomSheetDialog2.dismiss();
                        }
                    });
                    bottomSheetDialog2.show();
                }
                bottomSheetDialog3.dismiss();
                functions.write("dc", "0");
            }
        });
        notNow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Pref access$000 = functions;
                access$000.write("mills", (System.currentTimeMillis() + 7200000) + "");
                bottomSheetDialog.dismiss();
            }
        });
        if (!bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    public void increaseCount() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("count", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("count", sharedPreferences.getInt("count", 0) + 1);
        edit.commit();
    }

    public Boolean canShow() {
        int i = context.getSharedPreferences("count", 0).getInt("count", 0);
        Log.i("count", i + "");
        if (i > 8 && !functions.read("dc").equals("0")) {
            if (random == 1) {
                return true;
            }
            if (functions.read("mills").equals("null")) {
                return true;
            }
            if (Long.parseLong(functions.read("mills")) < System.currentTimeMillis()) {
                return true;
            }
        }
        return false;
    }

    public void showDialog() {
        if (random == 1) {
            alert.show();
        } else if (functions.read("mills").equals("null")) {
            showNormal();
        } else if (Long.parseLong(functions.read("mills")) < System.currentTimeMillis()) {
            showNormal();
        }
    }
}
