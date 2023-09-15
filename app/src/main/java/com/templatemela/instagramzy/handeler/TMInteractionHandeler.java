package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.utils.ServiceTools;
import com.templatemela.instagramzy.utils.TMInteractionHelper;
import com.templatemela.instagramzy.services.TMLikerCommenterFetcher;
import org.greenrobot.eventbus.EventBus;

public class TMInteractionHandeler {
    Context context;
    Handler handler = new Handler(Looper.getMainLooper());
    TMInteractionHelper interactionHelper;
    Pref pref;

    public TMInteractionHandeler(Context context2) {
        context = context2;
        interactionHelper = new TMInteractionHelper(context2);
        pref = new Pref(context2);
    }

    public void getLikeCommentList(boolean z) {
        if (!z) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (TMInteractionHandeler.this.interactionHelper.getLikeCommentList(TMInteractionHandeler.this.pref.getSavedLikeCommentList()).size() == 0) {
                        TMInteractionHandeler.this.getLikeCommentList(true);
                    } else {
                        EventBus.getDefault().post(TMInteractionHandeler.this.interactionHelper.getLikeCommentList(TMInteractionHandeler.this.pref.getSavedLikeCommentList()));
                    }
                }
            }).start();
        } else if (!new ServiceTools().isAnyListFetching(this.context)) {
            this.context.startService(new Intent(this.context, TMLikerCommenterFetcher.class));
            Log.i("data__","start");
        } else {
            this.handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(TMInteractionHandeler.this.context, "Already Fetching list", 0).show();
                }
            });
        }
    }
}
