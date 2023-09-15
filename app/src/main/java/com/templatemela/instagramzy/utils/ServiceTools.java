package com.templatemela.instagramzy.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import com.templatemela.instagramzy.services.TMFollowerFetchService;
import com.templatemela.instagramzy.services.TMPostFetcherService;

public class ServiceTools {
    public boolean isAnyListFetching(Context context) {
        return isServiceRunning(context, TMFollowerFetchService.class) || isServiceRunning(context, TMPostFetcherService.class);
    }

    private boolean isServiceRunning(Context context, Class<?> cls) {
        for (ActivityManager.RunningServiceInfo next : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            Log.d("SyncStateContract", String.format("Service:%s", new Object[]{next.service.getClassName()}));
            if (next.service.getClassName().equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }
}
