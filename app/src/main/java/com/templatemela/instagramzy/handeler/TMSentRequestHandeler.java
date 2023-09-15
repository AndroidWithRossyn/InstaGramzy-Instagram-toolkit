package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.interfaces.SentRequestInterface;
import com.tonyodev.fetch2.database.DownloadDatabase;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMSentRequestHandeler {
    Context context;
    Functions functions;
    Handler handler;
    List<String> list = new ArrayList();
    Pref pref;
    SentRequestInterface sentRequestInterface;

    public TMSentRequestHandeler(Context context2) {
        context = context2;
        functions = new Functions(context2);
        pref = new Pref(context2);
        handler = new Handler(context2.getMainLooper());
        sentRequestInterface = (SentRequestInterface) context2;
    }

    public void getList(Boolean bool) {
        list.clear();
        if (bool.booleanValue()) {
            final List<String> fetchList = fetchList();
            handler.post(new Runnable() {
                public void run() {
                    sentRequestInterface.onComplete(fetchList);
                }
            });
            return;
        }
        final List<String> sentRequestList = pref.getSentRequestList();
        if (sentRequestList.size() == 0) {
            getList(true);
        } else {
            handler.post(new Runnable() {
                public void run() {
                    sentRequestInterface.onComplete(sentRequestList);
                }
            });
        }
    }

    private List<String> fetchList() {
        String str = "";
        String str2 = "https://www.instagram.com/accounts/access_tool/current_follow_requests?__a=1&limit=25";
        while (!str.equals("null")) {
            try {
                JSONObject jSONObject = new JSONObject(functions.page(str2).text()).getJSONObject("data");
                JSONArray jSONArray = jSONObject.getJSONArray("data");
                Log.i(DownloadDatabase.TABLE_NAME, jSONArray.toString());
                for (int i = 0; i < jSONArray.length(); i++) {
                    list.add(jSONArray.getJSONObject(i).getString("text"));
                }
                try {
                    str = jSONObject.getString("cursor");
                    Log.i(DownloadDatabase.TABLE_NAME, jSONObject.toString());
                    str2 = "https://www.instagram.com/accounts/access_tool/current_follow_requests?__a=1&limit=25" + "&cursor=" + str;
                } catch (Exception unused) {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pref.saveSentRequestData(list);
        return list;
    }
}
