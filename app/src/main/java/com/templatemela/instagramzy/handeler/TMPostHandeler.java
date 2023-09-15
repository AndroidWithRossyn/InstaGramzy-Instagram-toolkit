package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.utils.ServiceTools;
import com.templatemela.instagramzy.models.TMPostModel;
import com.templatemela.instagramzy.services.TMPostFetcherService;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class TMPostHandeler {
    Context context;
    Functions functions;
    Handler handler;
    Pref pref;
    TMUserDetails userDetails;
    String userid;

    public TMPostHandeler(Context context2) {
        context = context2;
        pref = new Pref(context2);
        userDetails = new TMUserDetails(context2);
        handler = new Handler(context2.getMainLooper());
    }


    public void getPosts(Boolean bool) {
        if (!bool.booleanValue()) {
            final List<TMPostModel> allSavedPost = pref.getAllSavedPost();
            Log.i("data__", "list_size" + allSavedPost.size());
            if (allSavedPost.size() == 0) {
                getPosts(true);
            } else {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(1100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.post(new Runnable() {
                            public void run() {
                                EventBus.getDefault().post(allSavedPost);
                            }
                        });
                    }
                }).start();
            }
        } else if (!new ServiceTools().isAnyListFetching(context)) {
            context.startService(new Intent(context, TMPostFetcherService.class));
            Log.i("data__", "start");
        } else {
            Toast.makeText(context, "Already fetching a list", 0).show();
        }
    }

    public Boolean isPostCountChanged() {
        if (userDetails.getUser(false).getPostCount() == userDetails.getUser(true).getPostCount()) {
            return false;
        }
        return true;
    }
}
