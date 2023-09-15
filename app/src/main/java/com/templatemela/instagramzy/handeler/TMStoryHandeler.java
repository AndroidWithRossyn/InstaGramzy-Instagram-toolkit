package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.util.Log;

import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.models.TMStoryModel;
import com.templatemela.instagramzy.models.TMStoryResModel;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMStoryHandeler {
    Context context;
    Functions functions;
    String i;
    Pref pref;
    StoryInterface storyInterface;

    public interface StoryInterface {
        void onStoryGot(List<TMStoryModel> list);
    }

    public TMStoryHandeler(Context context2) {
        context = context2;
        functions = new Functions(context2);
        pref = new Pref(context2);
    }

    public void getAllStories(Boolean bool, StoryInterface storyInterface2) {
        storyInterface = storyInterface2;
        try {
            if (bool.booleanValue()) {
                getStories("", 0,storyInterface2);
            } else {
                storyInterface2.onStoryGot(pref.getSavedStories());
            }
        } catch (Exception unused) {
        }
    }

        public void getStories(final String str1, final int i2,StoryInterface storyInterface2) {
        new Thread(new Runnable() {
           @Override
            public void run() {
                String str = null;
                try {
                    if (i2 == 0) {
                        str = functions.page("https://i.instagram.com/api/v1/feed/reels_tray/").text();
                    } else if (i2 == 1) {
                        str = functions.page("https://i.instagram.com/api/v1/highlights/" + str1 + "/highlights_tray/").text();
                    } else {
                        Functions functions2 = functions;
                        str = functions2.page("https://i.instagram.com/api/v1/feed/user/" + str1 + "/story/").text();
                    }
                    JSONArray jSONArray = new JSONObject(str).getJSONArray("tray");
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        functions.printBigData("other", jSONObject.toString());
                        try {
                            String string = jSONObject.getString("id");
                            JSONObject jSONObject2 = jSONObject.getJSONObject("user");
                            String string2 = jSONObject2.getString("full_name");
                            String string3 = jSONObject2.getString("username");
                            String string4 = jSONObject2.getString("profile_pic_url");
                            String string5 = jSONObject2.getString("pk");
                            if (i2 == 1) {
                                string5 = jSONObject.getString("title");
                                string4 = jSONObject.getJSONObject("cover_media").getJSONObject("cropped_image_version").getString("url");
                            }
                            Log.i("tag__", jSONObject.toString());
                            arrayList.add(new TMStoryModel(string3, string5, string2, string4, string));
                            Log.i("reels", jSONObject.toString());
                        } catch (Exception unused) {
                        }
                    }
                    if (i2 == 0) {
                        pref.saveStoriesList(arrayList);
                    }
                    Log.i("tag__", arrayList.size() + "");
                    storyInterface2.onStoryGot(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public List<TMStoryModel> getHighLight(final String str1, final int i2) {

        String str = null;
                try {
                    if (i2 == 0) {
                        str = functions.page("https://i.instagram.com/api/v1/feed/reels_tray/").text();
                    } else if (i2 == 1) {
                        str = functions.page("https://i.instagram.com/api/v1/highlights/" + str1 + "/highlights_tray/").text();
                    } else {
                        Functions functions2 = functions;
                        str = functions2.page("https://i.instagram.com/api/v1/feed/user/" + str1 + "/story/").text();
                    }
                    JSONArray jSONArray = new JSONObject(str).getJSONArray("tray");
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        functions.printBigData("other", jSONObject.toString());
                        try {
                            String string = jSONObject.getString("id");
                            JSONObject jSONObject2 = jSONObject.getJSONObject("user");
                            String string2 = jSONObject2.getString("full_name");
                            String string3 = jSONObject2.getString("username");
                            String string4 = jSONObject2.getString("profile_pic_url");
                            String string5 = jSONObject2.getString("pk");
                            if (i2 == 1) {
                                string5 = jSONObject.getString("title");
                                string4 = jSONObject.getJSONObject("cover_media").getJSONObject("cropped_image_version").getString("url");
                            }
                            Log.i("tag__", jSONObject.toString());
                            arrayList.add(new TMStoryModel(string3, string5, string2, string4, string));
                            Log.i("reels", jSONObject.toString());
                        } catch (Exception unused) {
                        }
                    }
                    if (i2 == 0) {
                        pref.saveStoriesList(arrayList);
                    }
                    Log.i("tag__", arrayList.size() + "");
                    return arrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
             return null;
    }

    public void getStoriesDetails(final String str1, final int i2) {
        new Thread(new Runnable() {

            public void run() {
                String str=null;
                JSONObject jSONObject;
                TMStoryResModel storyResModel;
                if (i2 == 0) {
                    str = "https://i.instagram.com/api/v1/feed/reels_media/?reel_ids=" + str1;
                } else {
                    str = "https://i.instagram.com/api/v1/feed/user/" + str1 + "/story/";
                }
                String text = functions.page(str).text();
                ArrayList arrayList = new ArrayList();
                try {
                    JSONObject jSONObject2 = new JSONObject(text);
                    Log.i("reels--", str);
                    functions.printBigData("reels--", jSONObject2.toString());
                    if (i2 == 0) {
                        JSONObject jSONObject3 = jSONObject2.getJSONObject("reels");
                        i = str1;
                        jSONObject = jSONObject3.getJSONObject(i);
                    } else {
                        jSONObject = jSONObject2.getJSONObject("reel");
                    }
                    JSONArray jSONArray = jSONObject.getJSONArray("items");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject4 = jSONArray.getJSONObject(i);
                        try {
                            storyResModel = new TMStoryResModel(1, jSONObject4.getJSONArray("video_versions").getJSONObject(0).getString("url"));
                        } catch (Exception unused) {
                            storyResModel = new TMStoryResModel(0, jSONObject4.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url"));
                        }
                        arrayList.add(storyResModel);
                        Log.i("reels " + i, storyResModel.getType() + " " + storyResModel.getUrl());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(arrayList);
            }
        }).start();
    }}
