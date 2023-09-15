package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.models.TMMessageItemModel;
import com.templatemela.instagramzy.models.TMMessageModelList;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TMMessageListHadeler {
    Context context;
    Functions functions;
    Pref pref;
    String tag = "MessageListHadeler";
    Gson gson = new GsonBuilder().create();

    public TMMessageListHadeler(Context context) {
        context = context;
        functions = new Functions(context);
        pref = new Pref(context);
    }

    public void fetchMessage(final String str, final String str2) {
        new Thread(new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                JSONException e;
                String str3;
                String str4;
                String str5;
                String str6 = null;
                String string = null;
                String str7 = "media_share";
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                String str8 = "https://i.instagram.com/api/v1/direct_v2/threads/" + str;
                if (!str2.equals("")) {
                    str8 = str8 + "?cursor=" + str2;
                    z = true;
                } else {
                    z = false;
                }
                String text = functions.page(str8).body().text();
                Log.i(tag, text);
                try {
                    JSONObject jSONObject = new JSONObject(text).getJSONObject("thread");
                    JSONArray jSONArray = jSONObject.getJSONArray("items");
                    JSONArray jSONArray2 = jSONObject.getJSONArray("users");
                    int i = 0;
                    while (i < jSONArray2.length()) {
                        try {
                            hashMap.put(jSONArray2.getJSONObject(i).getLong("pk") + "", jSONArray2.getJSONObject(i).getString("profile_pic_url"));
                            i++;
                            str7 = str7;
                        } catch (JSONException e2) {
                            e = e2;
                            e.printStackTrace();
                            return;
                        }
                    }
                    String str9 = str7;
                    EventBus.getDefault().post(hashMap);
                    boolean z2 = jSONObject.getBoolean("has_older");
                    int i2 = 0;
                    while (i2 < jSONArray.length()) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        String string2 = jSONObject2.getString("item_id");
                        String str10 = jSONObject2.getLong("user_id") + "";
                        String string3 = jSONObject2.getString("item_type");
                        try {
                            if (string3.equals("text")) {
                                str4 = jSONObject2.getString("text");
                                str5 = string3;
                                str3 = "";
                            } else {
                                if (string3.equals("media")) {
                                    try {
                                        str5 = "video";
                                        str3 = jSONObject2.getJSONObject("media").getJSONArray("video_versions").getJSONObject(0).getString("url");
                                        str4 = "";
                                    } catch (Exception unused) {
                                        string = jSONObject2.getJSONObject("media").getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url");
                                        string3 = "image";
                                    }
                                } else if (string3.equals("animated_media")) {
                                    str3 = jSONObject2.getJSONObject("animated_media").getJSONObject("images").getJSONObject("fixed_height").getString("url");
                                    str4 = "";
                                    str5 = "animated_media";
                                    str6 = str9;
                                    TMMessageItemModel messageItemModel = new TMMessageItemModel(string2, str10, str5, str4, str3);
                                    arrayList.add(messageItemModel);
                                    Log.i(tag, gson.toJson(messageItemModel));
                                    i2++;
                                    str9 = str6;
                                } else if (string3.equals("voice_media")) {
                                    string = jSONObject2.getJSONObject("voice_media").getJSONObject("media").getJSONObject("audio").getString("audio_src");
                                } else {
                                    str6 = str9;
                                    if (string3.equals(str6)) {
                                        str5 = "text";
                                        str4 = "Media shared\nhttps://www.instagram.com/p/" + jSONObject2.getJSONObject(str6).getString("code");
                                        str3 = "";
                                    } else {
                                        str5 = string3;
                                        str4 = "";
                                        str3 = str4;
                                    }
                                    TMMessageItemModel messageItemModel2 = new TMMessageItemModel(string2, str10, str5, str4, str3);
                                    arrayList.add(messageItemModel2);
                                    Log.i(tag, gson.toJson(messageItemModel2));
                                    i2++;
                                    str9 = str6;
                                }
                                str3 = string;
                                str5 = string3;
                                str4 = "";
                                str6 = str9;
                                TMMessageItemModel messageItemModel22 = new TMMessageItemModel(string2, str10, str5, str4, str3);
                                arrayList.add(messageItemModel22);
                                Log.i(tag, gson.toJson(messageItemModel22));
                                i2++;
                                str9 = str6;
                            }
                            //Log.i(tag, gson.toJson(messageItemModel22));
                            i2++;
                            str9 = str6;
                        } catch (JSONException e3) {
                            e = e3;
                            e.printStackTrace();
                            return;
                        }
                        str6 = str9;
                        TMMessageItemModel messageItemModel222 = new TMMessageItemModel(string2, str10, str5, str4, str3);
                        arrayList.add(messageItemModel222);
                    }
                    EventBus.getDefault().post(new TMMessageModelList(arrayList, z, z2));
                } catch (JSONException e4) {
                    e = e4;
                }
            }
        }).start();
    }
}