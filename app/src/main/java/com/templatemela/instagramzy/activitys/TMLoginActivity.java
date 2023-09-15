package com.templatemela.instagramzy.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.airbnb.lottie.LottieAnimationView;


import com.templatemela.instagramzy.utils.CustomWebview;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import com.templatemela.instagramzy.utils.TMAdsUtils;


public class TMLoginActivity extends AppCompatActivity {
    CheckBox checkBox;
    Context context;
    Functions functions;
    TextView ia;
    Boolean isLoggedIn = false;
    boolean isOnActivity = true;
    CardView loginBtn;
    LottieAnimationView lottieAnimationView;
    TextView pp;

    ProgressBar progressBar;
    TMUserDetails userDetails;
    public static final String API_KEY = "";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_login);
        TMAdsUtils.initAd(TMLoginActivity.this);
        TMAdsUtils.loadBigBannerAds(TMLoginActivity.this,findViewById(R.id.adsView));
        pp = (TextView) findViewById(R.id.pp);
        ia = (TextView) findViewById(R.id.ia);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TMLoginActivity.this, CustomWebview.class);
                intent.putExtra("url", "file:///android_asset/privacy_policy.html");
                startActivity(intent);
            }
        });


        context = this;
        functions = new Functions(this);

        loginBtn = (CardView) findViewById(R.id.login_btn);
        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottie_layer);
        userDetails = new TMUserDetails(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    startActivity(new Intent(context, TMMainLoginActivity.class));
                    finish();
                    return;
                }
                Toast.makeText(TMLoginActivity.this, "Please accept privacy policy", Toast.LENGTH_LONG).show();
            }
        });




    }





    @Override
    public void onPause() {
        super.onPause();
        isOnActivity = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnActivity = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    public void applyOverrideConfiguration(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 25) {
            configuration.uiMode &= -49;
        }
        super.applyOverrideConfiguration(configuration);
    }
}
