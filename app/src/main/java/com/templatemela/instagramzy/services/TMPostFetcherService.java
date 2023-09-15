package com.templatemela.instagramzy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.models.TMPostModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.handeler.TMUserDetails;

import java.util.ArrayList;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMPostFetcherService extends Service {
    Functions functions;
    Pref pref;
    int totalPostCount = 0;
    TMUserDetails userDetails;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        this.functions = new Functions(getApplicationContext());
        this.pref = new Pref(getApplicationContext());
        this.userDetails = new TMUserDetails(getApplicationContext());
        Log.i("data__", "in service");
        this.totalPostCount = this.userDetails.getUser(false).getPostCount();
        new Thread(new Runnable() {
            public void run() {
                TMPostFetcherService.this.getAllPost();
            }
        }).start();
        return Service.START_REDELIVER_INTENT;
    }

    public void getAllPost() {
        String str;
        String str2;
        String str3;
        String str4;
        JSONArray jSONArray;
        int i;
        ArrayList arrayList = new ArrayList();
        String str5 = "https://i.instagram.com/api/v1/feed/user/" + this.functions.getUserId() + "/";
        String str6 = str5;
        String str7 = "";

        try {
            JSONObject jSONObject = new JSONObject(this.functions.page(str6).text());
            JSONArray jSONArray2 = jSONObject.getJSONArray("items");
            Log.e("count items", jSONArray2.toString());

            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                JSONObject jSONObject2 = jSONArray2.getJSONObject(i2);
                this.functions.printBigData("test_data", jSONObject2.toString());
                String string = jSONObject2.getString("pk");
                String string2 = jSONObject2.getString("code");
                int i3 = jSONObject2.getInt("comment_count");
                int i4 = jSONObject2.getInt("like_count");
                Log.i("user_data__", string + "");
                str4 = "";
                i = 1;
                jSONArray = jSONArray2;
                int i5 = jSONObject2.getInt("media_type");
                int indexOf = jSONObject2.toString().indexOf("https:", jSONObject2.toString().indexOf("candidates\":"));
                String replaceAll = jSONObject2.toString().substring(indexOf, jSONObject2.toString().indexOf("\"", indexOf)).replaceAll("\\\\", "");
                Log.i("data__", string + " like: " + i4 + " comment: " + i3 + " loc: " + str4 + " type: " + i5 + " views: " + i + " " + replaceAll);
                arrayList.add(new TMPostModel(string, str4, string2, replaceAll, i4, i3, i5, i));
                EventBus.getDefault().post(new TMStrModel("Fetching Posts : " + arrayList.size() + "/" + this.totalPostCount + " loaded", 1));

                Log.e("i2====", String.valueOf(i2));
            }
            Log.e("size==",arrayList.toString());
            EventBus.getDefault().post(arrayList);
            this.pref.savePosts(arrayList);
            EventBus.getDefault().post(new TMStrModel("finish", 1));
            stopSelf();

                    /*String string3 = jSONObject.getString("next_max_id");
                    Log.i(str3, string3 + "");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    str7 = string3;
                    str5 = str;
                    str6 = str2;*/


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}