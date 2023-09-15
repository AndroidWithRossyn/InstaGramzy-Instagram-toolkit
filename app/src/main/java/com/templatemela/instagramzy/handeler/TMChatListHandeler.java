package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.models.TMChatListModel;
import com.templatemela.instagramzy.models.TMChatModel;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TMChatListHandeler {
    Context context;
    Functions functions;
    Pref pref;
    String tag = "ChatListHandeler";
    Gson gson = new GsonBuilder().create();

    public TMChatListHandeler(Context context) {
        this.context = context;
        this.functions = new Functions(context);
        this.pref = new Pref(context);
    }

    public void fetchChatList(final String str) {
        new Thread(new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                String str2;
                String str3;
                String string = null;
                ArrayList arrayList = new ArrayList();
                String str4 = "https://i.instagram.com/api/v1/direct_v2/inbox/?persistentBadging=true&folder=&limit=10&thread_message_limit=1";
                if (!str.equals("")) {
                    str4 = str4 + "&cursor=" + str;
                    z = true;
                } else {
                    z = false;
                }
                String text = functions.page(str4).body().text();
                Log.i(tag, text);
                try {
                    JSONObject jSONObject = new JSONObject(text).getJSONObject("inbox");
                    JSONArray jSONArray = jSONObject.getJSONArray("threads");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        String string2 = jSONObject2.getString("thread_id");
                        String string3 = jSONObject2.getString("thread_title");
                        String string4 = jSONObject2.getJSONArray("users").getJSONObject(0).getString("full_name");
                        String string5 = jSONObject2.getJSONArray("users").getJSONObject(0).getString("profile_pic_url");
                        try {
                            try {
                                try {
                                    try {
                                        string = jSONObject2.getJSONArray("items").getJSONObject(0).getString("text");
                                    } catch (Exception unused) {
                                        string = jSONObject2.getJSONArray("items").getJSONObject(0).getJSONObject("reel_share").getString("text");
                                    }
                                } catch (Exception unused2) {
                                    string = jSONObject2.getJSONArray("items").getJSONObject(0).getString("item_type");
                                }
                            } catch (Exception unused3) {
                                str3 = "";
                            }
                        } catch (Exception unused4) {
                            string = jSONObject2.getJSONArray("items").getJSONObject(0).getJSONObject("action_log").getString("description");
                        }
                        str3 = string;
                        TMChatModel chatModel = new TMChatModel(string4, string3, string5, str3, string2, jSONObject2.getLong("last_activity_at"), jSONObject2.getInt("read_state") == 0);
                        arrayList.add(chatModel);
                        Log.i(tag, gson.toJson(chatModel));
                    }
                    try {
                        str2 = jSONObject.getString("oldest_cursor");
                    } catch (Exception unused5) {
                        str2 = "f";
                    }
                    EventBus.getDefault().post(new TMChatListModel(arrayList, str2, z));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
