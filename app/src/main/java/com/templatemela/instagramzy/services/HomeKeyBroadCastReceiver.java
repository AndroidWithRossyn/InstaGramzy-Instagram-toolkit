package com.templatemela.instagramzy.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.templatemela.instagramzy.models.TMStrModel;
import org.greenrobot.eventbus.EventBus;

public class HomeKeyBroadCastReceiver extends BroadcastReceiver {
    final String SYSTEM_DIALOGS_REASON_LONG_PRESS_HOME_KEY = "globalactions";
    final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    final String SYSTEM_DIALOG_REASON_KEY = "reason";
    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    String tag = "HomeKeyBroadCastReceiver";

    public void onReceive(Context context, Intent intent) {
        String stringExtra;
        if (intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS") && (stringExtra = intent.getStringExtra("reason")) != null) {
            if (stringExtra.equals("homekey") || stringExtra.equals("recentapps") || stringExtra.equals("globalactions")) {
                Log.i(this.tag, "SYSTEM_DIALOG_REASON_HOME_KEY");
                EventBus.getDefault().post(new TMStrModel("home", 100));
            }
        }
    }
}
