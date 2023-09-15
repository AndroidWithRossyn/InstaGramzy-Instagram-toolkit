package com.templatemela.instagramzy.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;
import com.templatemela.instagramzy.chat_popup.TMFloatingWidgetService;

public class ActionReceiver extends BroadcastReceiver {
    Context context;

    
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getIntExtra("action", 0) == 0) {
            createFloatingWidget();
        }
        context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    public void createFloatingWidget() {
        String string = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        boolean z = string != null && string.contains(context.getPackageName());
        if (Build.VERSION.SDK_INT < 23) {
            startFloatingWidgetService();
        } else if (!Settings.canDrawOverlays(context)) {
            Toast.makeText(context, "Enable overlay permission for this app", Toast.LENGTH_SHORT).show();
            if (!z) {
                Toast.makeText(context, "Enable notification permission", Toast.LENGTH_SHORT).show();
            }
        } else if (!z) {
            Toast.makeText(context, "Enable notification permission", Toast.LENGTH_SHORT).show();
        } else {
            startFloatingWidgetService();
        }
    }

    private void startFloatingWidgetService() {
        context.startService(new Intent(context, TMFloatingWidgetService.class));
    }
}
