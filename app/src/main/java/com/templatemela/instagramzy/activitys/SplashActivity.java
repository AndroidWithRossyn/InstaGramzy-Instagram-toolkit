package com.templatemela.instagramzy.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.handeler.TMUserDetails;

public class SplashActivity extends AppCompatActivity {
    TMUserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        userDetails = new TMUserDetails(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                tryToLogIn();
            }
        }, 2000);
    }



    public void tryToLogIn() {
        new Thread(new Runnable() {
            public void run() {
                TMUserModel user = userDetails.getUser(true);
                if (user == null || user.getUsername().equals("guest")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                         startActivity(new Intent(getApplicationContext(),TMLoginActivity.class));
                         finish();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userDetails.login();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    startActivity(new Intent(getApplicationContext(), TMMainActivity.class));
                                    finish();
                                }
                            });
                        }
                    });
                }
            }
        }).start();
    }
}