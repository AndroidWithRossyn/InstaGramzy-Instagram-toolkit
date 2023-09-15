package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.handeler.TMUserDetails;

public class TMErrorActivity extends AppCompatActivity {
    Button close;
    Button close1;
    String errorLog = "";
    Functions functions;
    Intent intent;
    Button login;
    Button mail;
    RelativeLayout noInternet;
    RelativeLayout notLoggedIn;
    Button notNow;
    Button openAgain;
    String tag = "ErrorActivity__";
    RelativeLayout unknownError;
    TMUserDetails userDetails;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_error);
        noInternet = (RelativeLayout) findViewById(R.id.no_internet);
        notLoggedIn = (RelativeLayout) findViewById(R.id.login_re);
        unknownError = (RelativeLayout) findViewById(R.id.other_error);
        openAgain = (Button) findViewById(R.id.open);
        close = (Button) findViewById(R.id.close);
        close1 = (Button) findViewById(R.id.close1);
        mail = (Button) findViewById(R.id.mail);
        login = (Button) findViewById(R.id.login);
        notNow = (Button) findViewById(R.id.not_now);
        functions = new Functions(this);
        userDetails = new TMUserDetails(this);
        intent = getIntent();
        errorLog= intent.getStringExtra("EXTRA_STACK_TRACE");
        if (errorLog != null && errorLog.contains("Unable to start activity ComponentInfo") && errorLog.contains("MainActivity")) {
            Intent intent3 = new Intent(this, TMMainActivity.class);
            intent3.putExtra("extra", "s");
            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent3);
        }
        if (!functions.internetIsConnected()) {
            notLoggedIn.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
            unknownError.setVisibility(View.GONE);
        } else {
            notLoggedIn.setVisibility(View.GONE);
            noInternet.setVisibility(View.GONE);
            unknownError.setVisibility(View.VISIBLE);
        }
        openAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (errorLog.equals("")) {
                    finish();
                    return;
                }
                startActivity(new Intent(TMErrorActivity.this, TMLoginActivity.class));
                finish();
            }
        });
        notNow.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                startActivity(new Intent(TMErrorActivity.this, TMLoginActivity.class));
                finish();
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                finish();
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                String str = "";
                boolean z = false;
                try {
                    str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                    z = userDetails.isLoggedIn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"templatemela@gmail.com"});
                intent.putExtra("android.intent.extra.SUBJECT", "Error report");

                errorLog = str + "\n" + errorLog;

                errorLog = "Login status : " + z + "\n" + errorLog;
                intent.putExtra("android.intent.extra.TEXT", errorLog);
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
