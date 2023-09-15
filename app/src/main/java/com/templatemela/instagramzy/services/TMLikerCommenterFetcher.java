package com.templatemela.instagramzy.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.utils.TMInteractionHelper;
import com.templatemela.instagramzy.models.TMPersonModel;
import com.templatemela.instagramzy.models.TMPostModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.handeler.TMFollowerListHadeler;
import com.templatemela.instagramzy.handeler.TMPostHandeler;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMLikerCommenterFetcher extends Service {
    String finishIndex = null;
    List<TMPersonModel> flist;
    TMFollowerListHadeler followerListHadeler;
    Functions functions;
    Handler handler;
    TMInteractionHelper interactionHelper;
    boolean isFollowerListFetchComplete = false;
    List<TMPostModel> newPostList;
    JSONArray oldLikeCommentList;
    JSONArray oldLikeCommentListCount;
    TMPostHandeler postHandeler;
    int preIndex = 0;
    Pref pref;
    int serverCallCount = 0;
    final String t = "LikeCommentService";
    String tag = "LikerCommentService";
    int temp = 0;
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    
    @Override
    public int onStartCommand(Intent intent, int i, int i2) {
        postHandeler = new TMPostHandeler(getApplicationContext());
        pref = new Pref(getApplicationContext());
        functions = new Functions(getApplicationContext());
        followerListHadeler = new TMFollowerListHadeler(getApplicationContext());
        oldLikeCommentList = pref.getSavedLikeCommentList();
        handler = new Handler(getMainLooper());
        interactionHelper = new TMInteractionHelper(getApplicationContext());
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                    Log.i("LikeCommentService", "sleeping...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                followerListHadeler.fetchList(false);
                while (true) {
                    if (flist != null && isFollowerListFetchComplete) {
                        break;
                    }
                    try {
                        Thread.sleep(200);
                        Log.i("LikeCommentService", "sleeping1..." + isFollowerListFetchComplete);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    public void run() {
                        postHandeler.getPosts(true);
                    }
                });
                while (true) {
                    if (newPostList == null) {
                        try {
                            Thread.sleep(200);
                            Log.i("LikeCommentService", "sleeping2...");
                        } catch (InterruptedException e3) {
                            e3.printStackTrace();
                        }
                    } else {
                        getp();
                        return;
                    }
                }
            }
        }).start();
        return Service.START_STICKY_COMPATIBILITY;
    }

/*    void getp() {

        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(newPostList.size());
        stringBuilder1.append("");
        Log.i(tag, stringBuilder1.toString());
        JSONArray jSONArray = new JSONArray();
        for (byte b = 0; b < newPostList.size(); b++) {
            TMPostModel postModel = newPostList.get(b);
            String str5 = postModel.getId();
            String str6 = postModel.getPostUrl();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("https://i.instagram.com/api/v1/media/");
            stringBuilder3.append(str5);
            stringBuilder3.append("/likers/");
            String str4 = stringBuilder3.toString();
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("https://i.instagram.com/api/v1/media/");
            stringBuilder3.append(str5);
            stringBuilder3.append("/comments/");
            String str7 = stringBuilder3.toString();
            temp = serverCallCount;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("id", str5);
            } catch (JSONException jSONException) {
                jSONException.printStackTrace();
            }
            try {
                JSONObject jSONObject1 = new JSONObject();
                JSONObject jSONObject2 = getPreSavedObj(str5);
                if (jSONObject2 == null || isPostLikeCountChanged(str5, jSONObject2)) {
                    Log.i(tag, str6);
                    Log.i(tag, str4);
                    String str = functions.page(str4).text();
                    JSONObject jSONObject3 = new JSONObject(str);
                        
                    jSONObject1 = jSONObject3;
                    try {
                        jSONObject.put("like_count", jSONObject1.getInt("user_count"));
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        try {
                            jSONObject.put("like_count", 0);
                        } catch (JSONException jSONException1) {
                            jSONException1.printStackTrace();
                        }
                    }
                    try {
                        JSONArray jSONArray1 = jSONObject1.getJSONArray("users");
                        String str8 = tag;
                        StringBuilder stringBuilder = new StringBuilder();
                       
                        stringBuilder.append("new_array_length: ");
                        stringBuilder.append(jSONArray1.length());
                        Log.i(str8, stringBuilder.toString());
                        jSONObject.put("likers", jSONArray1);
                    } catch (Exception exception) {
                        try {
                            JSONArray jSONArray1 = new JSONArray();
                           
                            jSONObject.put("likers", jSONArray1);
                        } catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                        }
                    }
                    serverCallCount++;
                }

                else {
                    String str = tag;
                    StringBuilder stringBuilder = new StringBuilder();
                   
                    stringBuilder.append(str5);
                    stringBuilder.append(" like already saved");
                    Log.i(str, stringBuilder.toString());
                    try {
                        jSONObject.put("like_count", getLikeCount(jSONObject2));
                        jSONObject.put("likers", getLikers(jSONObject2));
                    } catch (Exception exception) {}
                }
                if (jSONObject2 == null || isPostCommentCountChanged(str5, jSONObject2)) {
                    Log.i(tag, str6);
                    Log.i(tag, str7);
                    str5 = functions.page(str7).text();
                    try {
                        JSONObject jSONObject3 = new JSONObject(str5);

                        jSONObject1 = jSONObject3;
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                    try {
                        jSONObject.put("comment_count", jSONObject1.getInt("comment_count"));
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                    try {
                        JSONArray jSONArray2 = jSONObject1.getJSONArray("comments");
                        JSONArray jSONArray1 = new JSONArray();
                       
                        for (byte b1 = 0; b1 < jSONArray2.length(); b1++)
                            jSONArray1.put(jSONArray2.getJSONObject(b1).getJSONObject("user"));
                        jSONObject.put("commenters", jSONArray1);
                    } catch (Exception exception) {
                        try {
                            JSONArray jSONArray1 = new JSONArray();
                           
                            jSONObject.put("commenters", jSONArray1);
                        } catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                        }
                    }
                    serverCallCount++;
                    Thread.sleep(1000L);
                } else {
                    String str = tag;
                    StringBuilder stringBuilder = new StringBuilder();
                   
                    stringBuilder.append(str5);
                    stringBuilder.append(" comment already saved");
                    Log.i(str, stringBuilder.toString());
                    try {
                        jSONObject.put("comment_count", getCommentCount(jSONObject2));
                        jSONObject.put("commenters", getCommenters(jSONObject2));
                    } catch (Exception exception) {}
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jSONArray.put(jSONObject);
            if (serverCallCount > temp)
                interactionHelper.saveWhoRemoveLikeOrComment(oldLikeCommentList, jSONObject);
            String str3 = tag;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("loading post number ");
            stringBuilder4.append(b);
            Log.i(str3, stringBuilder4.toString());
            pref.saveLikeCommentList(jSONArray);
            EventBus eventBus = EventBus.getDefault();
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Analysing post no : ");
            stringBuilder4.append(b);
            eventBus.post(new TMStrModel(stringBuilder4.toString(), 2));
            List list = interactionHelper.getLikeCommentList(jSONArray);
            EventBus.getDefault().post(list);
            if (serverCallCount > 50)
                break;
        }
        EventBus.getDefault().post(new TMStrModel("All post's data is fetched", 2));
        pref.setIsChained(Boolean.valueOf(false));
        pref.saveLikeCommentList(jSONArray);

        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("LikeCommentListUpdateTime");
        stringBuilder1.append(functions.getUserId());
        String str2 = stringBuilder1.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(System.currentTimeMillis());
        stringBuilder2.append("");
        pref.write(str2, stringBuilder2.toString());
        handler.post((Runnable)new Object());
    }*/




    void getp() {
        String str1 = this.tag;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(this.newPostList.size());
        stringBuilder1.append("");
        Log.i(str1, stringBuilder1.toString());
        JSONArray jSONArray = new JSONArray();
        for (byte b = 0; b < this.newPostList.size(); b++) {
            TMPostModel postModel = this.newPostList.get(b);
            String str5 = postModel.getId();
            String str6 = postModel.getPostUrl();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("https://i.instagram.com/api/v1/media/");
            stringBuilder3.append(str5);
            stringBuilder3.append("/likers/");
            String str4 = stringBuilder3.toString();
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("https://i.instagram.com/api/v1/media/");
            stringBuilder3.append(str5);
            stringBuilder3.append("/comments/");
            String str7 = stringBuilder3.toString();
            this.temp = this.serverCallCount;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("id", str5);
            } catch (JSONException jSONException) {
                jSONException.printStackTrace();
            }
            try {
                JSONObject jSONObject1 = new JSONObject();

                JSONObject jSONObject2 = getPreSavedObj(str5);
                if (jSONObject2 == null || isPostLikeCountChanged(str5, jSONObject2)) {
                    Log.i(this.tag, str6);
                    Log.i(this.tag, str4);
                    String str = this.functions.page(str4).text();
                    try {
                        JSONObject jSONObject3 = new JSONObject(str);

                        jSONObject1 = jSONObject3;
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                    try {
                        jSONObject.put("like_count", jSONObject1.getInt("user_count"));
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        try {
                            jSONObject.put("like_count", 0);
                        } catch (JSONException jSONException1) {
                            jSONException1.printStackTrace();
                        }
                    }
                    try {
                        JSONArray jSONArray1 = jSONObject1.getJSONArray("users");
                        String str8 = this.tag;
                        StringBuilder stringBuilder = new StringBuilder();

                        stringBuilder.append("new_array_length: ");
                        stringBuilder.append(jSONArray1.length());
                        Log.i(str8, stringBuilder.toString());
                        jSONObject.put("likers", jSONArray1);
                    } catch (Exception exception) {
                        try {
                            JSONArray jSONArray1 = new JSONArray();

                            jSONObject.put("likers", jSONArray1);
                        } catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                        }
                    }
                    this.serverCallCount++;
                } else {
                    String str = this.tag;
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(str5);
                    stringBuilder.append(" like already saved");
                    Log.i(str, stringBuilder.toString());
                    try {
                        jSONObject.put("like_count", getLikeCount(jSONObject2));
                        jSONObject.put("likers", getLikers(jSONObject2));
                    } catch (Exception exception) {}
                }
                if (jSONObject2 == null || isPostCommentCountChanged(str5, jSONObject2)) {
                    Log.i(this.tag, str6);
                    Log.i(this.tag, str7);
                    str5 = this.functions.page(str7).text();
                    JSONObject jSONObject3 = new JSONObject(str5);
                    jSONObject1 = jSONObject3;
                    try {
                        jSONObject.put("comment_count", jSONObject1.getInt("comment_count"));
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                    try {
                        JSONArray jSONArray2 = jSONObject1.getJSONArray("comments");
                        JSONArray jSONArray1 = new JSONArray();

                        for (byte b1 = 0; b1 < jSONArray2.length(); b1++)
                            jSONArray1.put(jSONArray2.getJSONObject(b1).getJSONObject("user"));
                        jSONObject.put("commenters", jSONArray1);
                    } catch (Exception exception) {
                        try {
                            JSONArray jSONArray1 = new JSONArray();

                            jSONObject.put("commenters", jSONArray1);
                        } catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                        }
                    }
                    this.serverCallCount++;
                    Thread.sleep(1000L);
                } else {
                    String str = this.tag;
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(str5);
                    stringBuilder.append(" comment already saved");
                    Log.i(str, stringBuilder.toString());
                    try {
                        jSONObject.put("comment_count", getCommentCount(jSONObject2));
                        jSONObject.put("commenters", getCommenters(jSONObject2));
                    } catch (Exception exception) {}
                }
            } catch (InterruptedException | JSONException interruptedException) {
                interruptedException.printStackTrace();
            }
            jSONArray.put(jSONObject);
            if (this.serverCallCount > this.temp)
                this.interactionHelper.saveWhoRemoveLikeOrComment(this.oldLikeCommentList, jSONObject);
            String str3 = this.tag;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("loading post number ");
            stringBuilder4.append(b);
            Log.i(str3, stringBuilder4.toString());
            this.pref.saveLikeCommentList(jSONArray);
            EventBus eventBus = EventBus.getDefault();
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Analysing post no : ");
            stringBuilder4.append(b);
            eventBus.post(new TMStrModel(stringBuilder4.toString(), 2));
            List list = this.interactionHelper.getLikeCommentList(jSONArray);
            EventBus.getDefault().post(list);
            if (this.serverCallCount > 50)
                break;
        }
        EventBus.getDefault().post(new TMStrModel("All post's data is fetched", 2));
        this.pref.setIsChained(Boolean.valueOf(false));
        this.pref.saveLikeCommentList(jSONArray);
        Pref pref = this.pref;
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("LikeCommentListUpdateTime");
        stringBuilder1.append(this.functions.getUserId());
        String str2 = stringBuilder1.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(System.currentTimeMillis());
        stringBuilder2.append("");
        pref.write(str2, stringBuilder2.toString());

    }





    public JSONObject getPreSavedObj(String str) {

        int i = preIndex;
        while (i < oldLikeCommentList.length()) {
            try {
                if (str.equals(oldLikeCommentList.getJSONObject(i).getString("id"))) {
                    preIndex = i;
                    return oldLikeCommentList.getJSONObject(i);
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i(tag, "pre saved obj is null");
        return null;
    }

    
    public int getLikeCount(JSONObject jSONObject) {
        try {
            return jSONObject.getInt("like_count");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    
    public int getCommentCount(JSONObject jSONObject) {
        try {
            return jSONObject.getInt("comment_count");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    
    public boolean isPostLikeCountChanged(String str, JSONObject jSONObject) {
        int likeCount = getLikeCount(jSONObject);
        int i = 0;
        for (int i2 = 0; i2 < newPostList.size(); i2++) {
            if (str.equals(newPostList.get(i2).getId())) {
                i = newPostList.get(i2).getLikeCount();
            }
        }
        String str2 = tag;
        Log.i(str2, "compatre" + i + " " + likeCount);
        if (pref.getSavedLikeCommentList().length() != 0 && i == likeCount) {
            return false;
        }
        return true;
    }

    
    public boolean isPostCommentCountChanged(String str, JSONObject jSONObject) {
        int commentCount = getCommentCount(jSONObject);
        int i = 0;
        for (int i2 = 0; i2 < newPostList.size(); i2++) {
            if (str.equals(newPostList.get(i2).getId())) {
                i = newPostList.get(i2).getCommentCount();
            }
        }
        String str2 = tag;
        Log.i(str2, "compatre" + i + " " + commentCount);
        if (pref.getSavedLikeCommentList().length() != 0 && i == commentCount) {
            return false;
        }
        return true;
    }

    
    public JSONArray getLikers(JSONObject jSONObject) {
        try {
            return jSONObject.getJSONArray("likers");
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    
    public JSONArray getCommenters(JSONObject jSONObject) {
        try {
            return jSONObject.getJSONArray("commenters");
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteListGener(List<TMPostModel> list) {

        Log.i("LikeCommentService", "onCompleteListGener...");
        try {
            list.get(0).getCommentCount();
            newPostList = list;
            String str = tag;
            Log.i(str, list.size() + "list_size");
        } catch (Exception unused) {
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteList(List<TMPersonModel> list) {
        Log.i("LikeCommentService", "onCompleteList...");
        try {
            list.get(0).getFollowedByMe();
            flist = list;
        } catch (Exception unused) {
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteFollowerList(TMStrModel str) {
        if (str.getData().equals("finish") && str.getType() == 0) {
            isFollowerListFetchComplete = true;
        }
        if (str.getData().equals("finish") && str.getType() == 1) {
            finishIndex = str.getData();
        }
    }

    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
