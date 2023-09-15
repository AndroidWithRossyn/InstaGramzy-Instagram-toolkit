package com.templatemela.instagramzy.handeler;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class TMSearchHandeler {
    Context context;
    Functions functions;

    public TMSearchHandeler(Context context2) {
        context = context2;
        functions = new Functions(context2);
    }

    public void getSearchResult(String str) {
        final String str2 = "https://www.instagram.com/web/search/topsearch/?context=blended&query=" + str + "&include_reel=false";
        new Thread(new Runnable() {
            public void run() {
                try {
                    EventBus.getDefault().post(new JSONObject(functions.page(str2).text()).getJSONArray("users"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
