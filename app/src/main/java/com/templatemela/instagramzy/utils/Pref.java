package com.templatemela.instagramzy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.models.TMChatModel;
import com.templatemela.instagramzy.models.TMPersonModel;
import com.templatemela.instagramzy.models.TMPostModel;
import com.templatemela.instagramzy.models.TMStoryModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pref {
    Context context;
    Functions functions;
    Gson gson;

    public String getNullDp() {
        return "https://e7.pngegg.com/pngimages/980/886/png-clipart-male-portrait-avatar-computer-icons-icon-design-avatar-flat-face-icon-people-head.png";
    }

    public boolean isPremium() {
        return true;
    }

    public Pref(Context context2) {
        this.context = context2;
        try {
            this.functions = new Functions(context2);
            this.gson = new GsonBuilder().create();
        } catch (Exception unused) {
        }
    }

    public void write(String str, String str2) {
        Context context2 = this.context;
        SharedPreferences.Editor edit = context2.getSharedPreferences(context2.getPackageName(), 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public String read(String str) {
        Context context2 = this.context;
        return context2.getSharedPreferences(context2.getPackageName(), 0).getString(str, "null");
    }


    public void cleanPrefrence(){

    }

    public void saveUserName(String str) {
        write("username" + this.functions.getUserId(), str);
    }

    public String getSavedUserName() {
        return read("username" + this.functions.getUserId());
    }

    public void saveUserDetails(String str) {
        write("userdetails" + this.functions.getUserId(), str);
    }

    public void updateCounts(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(getSavedUserDetails());
            jSONObject.put("following", str2);
            jSONObject.put("followers", str);
            saveUserDetails(jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSavedUserDetails() {
        return read("userdetails" + this.functions.getUserId());
    }

    public void saveMainList(List<TMPersonModel> list) {
        JSONArray jSONArray = new JSONArray();
        Log.i("savedata", list.size() + "");
        for (TMPersonModel next : list) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("name", next.getName());
                jSONObject.put("username", next.getUsername());
                jSONObject.put("id", next.getId());
                jSONObject.put("isFollowing", next.getFollowing());
                jSONObject.put("isFollowedByMe", next.getFollowedByMe());
                jSONObject.put("isVerified", next.getVerified());
                jSONObject.put("dp", next.getDp());
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("savedata", jSONArray.length() + "");
        Log.i("savedata", jSONArray.toString() + "");
        write("mainList" + this.functions.getUserId(), jSONArray.toString());
        write("mainListUpdateTime" + this.functions.getUserId(), System.currentTimeMillis() + "");
    }

    public int getFollowerListUpdateTimeDifferance() {
        long j;
        try {
            j = Long.valueOf(read("mainListUpdateTime" + this.functions.getUserId())).longValue();
        } catch (Exception unused) {
            j = 0;
        }
        return (int) (((double) (System.currentTimeMillis() - j)) / 60000.0d);
    }

    public long getFollowerListUpdateTime() {
        try {
            return Long.valueOf(read("mainListUpdateTime" + this.functions.getUserId())).longValue();
        } catch (Exception unused) {
            return 0;
        }
    }

    public List<TMPersonModel> getMainList() {
        JSONArray jSONArray = new JSONArray();
        ArrayList arrayList = new ArrayList();
        Log.i("json_arrzy", jSONArray.toString());
        try {
            JSONArray jSONArray2 = new JSONArray(read("mainList" + this.functions.getUserId()));
            for (int i = 0; i < jSONArray2.length(); i++) {
                JSONObject jSONObject = jSONArray2.getJSONObject(i);
                arrayList.add(new TMPersonModel(jSONObject.getString("name"), jSONObject.getString("id"), jSONObject.getString("username"), jSONObject.getString("dp"), Boolean.valueOf(jSONObject.getBoolean("isFollowing")), Boolean.valueOf(jSONObject.getBoolean("isFollowedByMe")), Boolean.valueOf(jSONObject.getBoolean("isVerified")), 0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public void saveSentRequestData(List<String> list) {
        JSONArray jSONArray = new JSONArray();
        for (String next : list) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("u", next);
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        write("sentRequest" + this.functions.getUserId(), jSONArray.toString());
    }

    public List<String> getSentRequestList() {
        JSONArray jSONArray = new JSONArray();
        ArrayList arrayList = new ArrayList();
        try {
            jSONArray = new JSONArray(read("sentRequest" + this.functions.getUserId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                arrayList.add(jSONArray.getJSONObject(i).getString("u"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return arrayList;
    }

    public void removeFromSentRequestList(String str) {
        JSONArray jSONArray = new JSONArray();
        try {
            jSONArray = new JSONArray(read("sentRequest" + this.functions.getUserId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (true) {
            if (i >= jSONArray.length()) {
                break;
            }
            try {
                if (jSONArray.getJSONObject(i).getString("u").equals(str)) {
                    jSONArray.remove(i);
                    break;
                }
                i++;
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        write("sentRequest" + this.functions.getUserId(), jSONArray.toString());
    }

    public void saveRecentlyFollowUnFollowedList(List<TMPersonModel> list, int i) {
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();

        Log.i("savedata", list.size() + "");
        for (TMPersonModel next : list) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("name", next.getName());
                jSONObject.put("username", next.getUsername());
                jSONObject.put("id", next.getId());
                jSONObject.put("isFollowing", next.getFollowing());
                jSONObject.put("isFollowedByMe", next.getFollowedByMe());
                jSONObject.put("isVerified", next.getVerified());
                jSONObject.put("dp", next.getDp());
                jSONObject.put("time", next.getTime());
                jSONArray2.put(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (i == 0) {
            write("wrufList" + this.functions.getUserId(), null);
            write("wrufList" + this.functions.getUserId(), jSONArray2.toString());
            return;
        }
        write("wrfList" + this.functions.getUserId(), null);
        write("wrfList" + this.functions.getUserId(), jSONArray2.toString());
    }

    public List<TMPersonModel> getRecentFollowUnfollowList(int i) {
        JSONArray jSONArray =new JSONArray();;
        JSONArray jSONArray2 = new JSONArray();
        ArrayList arrayList = new ArrayList();
        Log.i("json_arrzy", jSONArray2.toString());
        if (i == 0) {
            try {
                jSONArray = new JSONArray(read("wrufList" + this.functions.getUserId()));
                for (int length = jSONArray.length() - 1; length >= 0; length--) {
                    JSONObject jSONObject = jSONArray.getJSONObject(length);
                    arrayList.add(new TMPersonModel(jSONObject.getString("name"), jSONObject.getString("id"), jSONObject.getString("username"), jSONObject.getString("dp"), Boolean.valueOf(jSONObject.getBoolean("isFollowing")), Boolean.valueOf(jSONObject.getBoolean("isFollowedByMe")), Boolean.valueOf(jSONObject.getBoolean("isVerified")), jSONObject.getLong("time")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                jSONArray = new JSONArray(read("wrfList" + this.functions.getUserId()));
                for (int length = jSONArray.length() - 1; length >= 0; length--) {
                    JSONObject jSONObject = jSONArray.getJSONObject(length);
                    arrayList.add(new TMPersonModel(jSONObject.getString("name"), jSONObject.getString("id"), jSONObject.getString("username"), jSONObject.getString("dp"), Boolean.valueOf(jSONObject.getBoolean("isFollowing")), Boolean.valueOf(jSONObject.getBoolean("isFollowedByMe")), Boolean.valueOf(jSONObject.getBoolean("isVerified")), jSONObject.getLong("time")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return arrayList;
    }

    public void savePosts(List<TMPostModel> list) {
        JSONArray jSONArray = new JSONArray();
        for (TMPostModel next : list) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("id", next.getId());
                jSONObject.put("location", next.getLocation());
                jSONObject.put("postUrl", next.getPostUrl());
                jSONObject.put("pictureUrl", next.getPictureUrl());
                jSONObject.put("likeCount", next.getLikeCount());
                jSONObject.put("commentCount", next.getCommentCount());
                jSONObject.put("type", next.getType());
                jSONObject.put("views", next.getViews());
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        write("postlist" + this.functions.getUserId(), jSONArray.toString());
        write("postListUpdateTime" + this.functions.getUserId(), System.currentTimeMillis() + "");
    }

    public int getPostListUpdateTimeDifferance() {
        long j;
        try {
            j = Long.valueOf(read("postListUpdateTime" + this.functions.getUserId())).longValue();
        } catch (Exception unused) {
            j = 0;
        }
        return (int) (((double) (System.currentTimeMillis() - j)) / 60000.0d);
    }

    public List<TMPostModel> getAllSavedPost() {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArray = new JSONArray();
        try {
            jSONArray = new JSONArray(read("postlist" + this.functions.getUserId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                Log.i("saved_data", jSONObject.toString());
                arrayList.add(new TMPostModel(jSONObject.getString("id"), jSONObject.getString("location"), jSONObject.getString("postUrl"), jSONObject.getString("pictureUrl"), jSONObject.getInt("likeCount"), jSONObject.getInt("commentCount"), jSONObject.getInt("type"), jSONObject.getInt("views")));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return arrayList;
    }

    public void saveLikeCommentList(JSONArray jSONArray) {
        write("likeCommentList" + this.functions.getUserId(), jSONArray.toString());
    }

    public JSONArray getSavedLikeCommentList() {
        try {
            return new JSONArray(read("likeCommentList" + this.functions.getUserId()));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public int getLikeCommentListUpdateTimeDiffInMints() {
        long j;
        try {
            j = Long.valueOf(read("LikeCommentListUpdateTime" + this.functions.getUserId())).longValue();
        } catch (Exception unused) {
            j = 0;
        }
        return (int) (((double) (System.currentTimeMillis() - j)) / 60000.0d);
    }

    public long getLikeCommentListUpdateTime() {
        try {
            return Long.valueOf(read("LikeCommentListUpdateTime" + this.functions.getUserId())).longValue();
        } catch (Exception unused) {
            return 0;
        }
    }

    public void saveWhoDeleteLikes(String str, String str2, String str3, String str4, String str5) {
        String str6;
        String str7;
        String str8;
        Pref pref;
        String str9 = str;
        String str10 = str2;
        String str11 = str3;
        String str12 = str4;
        String str13 = str5;
        JSONArray whoDeletedLikesOrComments = getWhoDeletedLikesOrComments(0);
        int i = 0;
        while (true) {
            if (i >= whoDeletedLikesOrComments.length()) {
                str6 = "whoremoveLikes";
                str7 = "postId";
                str8 = "dp";
                pref = this;
                break;
            }
            try {
                JSONObject jSONObject = whoDeletedLikesOrComments.getJSONObject(i);
                String str14 = "whoremoveLikes";
                if (jSONObject.getString("id").equals(str9)) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("id", str9);
                    jSONObject2.put("username", str10);
                    jSONObject2.put("name", str11);
                    jSONObject2.put("dp", str12);
                    JSONArray jSONArray = jSONObject.getJSONArray("posts");
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("postId", str13);
                    str7 = "postId";
                    str8 = "dp";
                    jSONObject3.put("time", System.currentTimeMillis());
                    jSONArray.put(jSONObject3);
                    jSONObject2.put("posts", jSONArray);
                    whoDeletedLikesOrComments.put(i, jSONObject2);
                    StringBuilder sb = new StringBuilder();
                    String str15 = str14;
                    sb.append(str15);
                    str6 = str15;
                    pref = this;
                    sb.append(pref.functions.getUserId());
                    pref.write(sb.toString(), whoDeletedLikesOrComments.toString());
                    Log.i(str6, whoDeletedLikesOrComments.toString());
                    break;
                } else {
                    i++;
                    str12 = str4;
                }
            } catch (JSONException e2) {

                e2.printStackTrace();
                i++;
                str12 = str4;
            }
        }
        if (i == whoDeletedLikesOrComments.length()) {
            JSONObject jSONObject4 = new JSONObject();
            try {
                jSONObject4.put("id", str9);
                jSONObject4.put("username", str10);
                jSONObject4.put("name", str11);
                jSONObject4.put(str8, str4);
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put(str7, str13);
                jSONObject5.put("time", System.currentTimeMillis());
                JSONArray jSONArray2 = new JSONArray();
                jSONArray2.put(jSONObject5);
                jSONObject4.put("posts", jSONArray2);
                whoDeletedLikesOrComments.put(jSONObject4);
                Log.i(str6, whoDeletedLikesOrComments.toString());
                pref.write(str6 + pref.functions.getUserId(), whoDeletedLikesOrComments.toString());
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
    }

    public JSONArray getWhoDeletedLikesOrComments(int i) {
        JSONArray jSONArray=new JSONArray();;
        try {

            if (i == 0) {
                jSONArray = new JSONArray(read("whoremoveLikes" + this.functions.getUserId()));
            } else {
                jSONArray = new JSONArray(read("whoremoveComments" + this.functions.getUserId()));
            }
            JSONArray jSONArray2 = new JSONArray();
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                jSONArray2.put(jSONArray.getJSONObject(i2));
            }
            return jSONArray2;
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public void saveWhoDeleteComments(String str, String str2, String str3, String str4, String str5) {
        Pref pref;
        String str6;
        String str7;
        String str8 = str;
        String str9 = str2;
        String str10 = str3;
        String str11 = str4;
        String str12 = str5;
        JSONArray whoDeletedLikesOrComments = getWhoDeletedLikesOrComments(1);
        int i = 0;
        while (true) {
            if (i >= whoDeletedLikesOrComments.length()) {
                pref = this;
                str6 = "id";
                str7 = "whoremoveComments";
                break;
            }
            try {
                JSONObject jSONObject = whoDeletedLikesOrComments.getJSONObject(i);
                String str13 = "whoremoveComments";
                if (jSONObject.getString("id").equals(str8)) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("id", str8);
                    jSONObject2.put("username", str9);
                    jSONObject2.put("name", str10);
                    jSONObject2.put("dp", str11);
                    JSONArray jSONArray = jSONObject.getJSONArray("posts");
                    String str14 = "id";
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("postId", str12);
                    jSONObject3.put("time", System.currentTimeMillis());
                    jSONArray.put(jSONObject3);
                    jSONObject2.put("posts", jSONArray);
                    whoDeletedLikesOrComments.put(i, jSONObject2);
                    StringBuilder sb = new StringBuilder();
                    str7 = str13;
                    sb.append(str7);
                    pref = this;
                    str6 = str14;
                    sb.append(pref.functions.getUserId());
                    pref.write(sb.toString(), whoDeletedLikesOrComments.toString());
                    Log.i(str7, whoDeletedLikesOrComments.toString());
                    break;
                } else {
                    pref = this;
                    i++;
                    str12 = str5;
                    Pref pref2 = pref;
                    str11 = str4;
                }
            } catch (JSONException e2) {

                pref = this;
                e2.printStackTrace();
                i++;
                str12 = str5;
                Pref pref22 = pref;
                str11 = str4;
            }
        }
        if (i == whoDeletedLikesOrComments.length()) {
            JSONObject jSONObject4 = new JSONObject();
            try {
                jSONObject4.put(str6, str8);
                jSONObject4.put("username", str9);
                jSONObject4.put("name", str10);
                jSONObject4.put("dp", str4);
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("postId", str5);
                jSONObject5.put("time", System.currentTimeMillis());
                JSONArray jSONArray2 = new JSONArray();
                jSONArray2.put(jSONObject5);
                jSONObject4.put("posts", jSONArray2);
                whoDeletedLikesOrComments.put(jSONObject4);
                Log.i("whoDeletedComments", whoDeletedLikesOrComments.toString());
                pref.write(str7 + pref.functions.getUserId(), whoDeletedLikesOrComments.toString());
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
    }

    public void saveStoriesList(List<TMStoryModel> list) {
        JSONArray jSONArray = new JSONArray();
        for (TMStoryModel next : list) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("storyId", next.getStoryId());
                jSONObject.put("name", next.getName());
                jSONObject.put("username", next.getUsername());
                jSONObject.put("id", next.getUserId());
                jSONObject.put("dp", next.getDp());
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            write("story" + this.functions.getUserId(), jSONArray.toString());
        }
    }

    public List<TMStoryModel> getSavedStories() {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(read("story" + this.functions.getUserId()));
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                arrayList.add(new TMStoryModel(jSONObject.getString("username"), jSONObject.getString("name"), jSONObject.getString("id"), jSONObject.getString("dp"), jSONObject.getString("storyId")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public void saveDownloadId(int i) {
        JSONArray jSONArray;
        try {
            jSONArray = new JSONArray(read("downloads"));
        } catch (JSONException e) {
            e.printStackTrace();
            jSONArray = new JSONArray();
        }
        jSONArray.put(i);
        write("downloads", jSONArray.toString());
    }

    public List<Integer> getDownloadIds() {
        JSONArray jSONArray;
        ArrayList arrayList = new ArrayList();
        try {
            jSONArray = new JSONArray(read("downloads"));
        } catch (JSONException e) {
            e.printStackTrace();
            jSONArray = new JSONArray();
        }
        for (int length = jSONArray.length() - 1; length >= 0; length--) {
            try {
                arrayList.add(Integer.valueOf(jSONArray.getInt(length)));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return arrayList;
    }

    public void addToBookMark(String str) {
        JSONArray jSONArray = new JSONArray();
        try {
            jSONArray = new JSONArray(read("bookmarks"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jSONArray.put(str);
        write("bookmarks", jSONArray.toString());
    }

    public JSONArray getBookMarks() {
        try {
            return new JSONArray(read("bookmarks"));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public boolean isInBookMarks(String str) {
        return read("bookmarks").contains(str);
    }

    public JSONArray removeFromBookMark(String str) {
        try {
            JSONArray jSONArray = new JSONArray(read("bookmarks"));
            int i = 0;
            while (true) {
                if (i >= jSONArray.length()) {
                    break;
                } else if (jSONArray.getString(i).contains(str)) {
                    jSONArray.remove(i);
                    break;
                } else {
                    i++;
                }
            }
            write("bookmarks", jSONArray.toString());
            return jSONArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public void addToHashTagCollection(String str) {
        JSONArray hashTagCollection = getHashTagCollection();
        hashTagCollection.put(str);
        write("hashTagCollection", hashTagCollection.toString());
    }

    public JSONArray getHashTagCollection() {
        try {
            return new JSONArray(read("hashTagCollection"));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public void removeFromHashtagCollection(String str) {
        JSONArray hashTagCollection = getHashTagCollection();
        int i = 0;
        while (true) {
            if (i >= hashTagCollection.length()) {
                break;
            }
            try {
                if (hashTagCollection.getString(i).trim().equals(str.trim())) {
                    hashTagCollection.remove(i);
                    break;
                }
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        write("hashTagCollection", hashTagCollection.toString());
    }

    public Boolean isChained() {
        try {
            return Boolean.valueOf(read("isChained"));
        } catch (Exception unused) {
            return false;
        }
    }

    public void setIsChained(Boolean bool) {
        write("isChained", bool + "");
    }

    public void writePermissionState(String str, int i) {
        write("request" + str, i + "");
        Log.i("requst_code", "request" + str);
    }

    public int getPermissionState(String str) {
        try {
            String read = read("request" + str);
            Log.i("requst_code", "request" + read);
            return Integer.parseInt(read);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean isFTE() {
        try {
            if (System.currentTimeMillis() - Long.parseLong(read("install_time")) > 259200000) {
                return true;
            }
            return false;
        } catch (Exception unused) {
        }
        return false;
    }

    public void setInstallT(long j) {
        write("install_time", j + "");
    }

    public void setPremium(boolean z) {
        write("premium", z + "");
    }

    public boolean isEverPurchased() {
        try {
            return Boolean.parseBoolean(read("everPurchased"));
        } catch (Exception unused) {
            return false;
        }
    }

    public void setEverPurchased(boolean z) {
        write("everPurchased", z + "");
    }

    public void saveLinksData(JSONObject jSONObject) {
        write("saveLinksData", jSONObject.toString());
    }

    public JSONObject getLinksData() {
        try {
            return new JSONObject(read("saveLinksData"));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public void saveUnseenCount(HashMap<String, Integer> hashMap) {
        String json = this.gson.toJson((Object) hashMap);
        if (!json.equals("null")) {
            write("unseencount" + this.functions.getUserId(), json);
            Log.i("NotificationReceiver", "onsave" + json);
        }
    }

    public HashMap<String, Integer> getUnseenCount() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        try {
            Gson gson2 = this.gson;
            hashMap = (HashMap) gson2.fromJson(read("unseencount" + this.functions.getUserId()), HashMap.class);
        } catch (Exception unused) {
        }
        return hashMap == null ? new HashMap<>() : hashMap;
    }

    public void saveChatList(List<TMChatModel> list) {
        write("chatlist" + this.functions.getUserId(), this.gson.toJson((Object) list));
    }

    public List<TMChatModel> getChatList() {
        List<TMChatModel> list = (List) this.gson.fromJson(read("chatlist" + this.functions.getUserId()), List.class);
        Log.i("pref__", this.gson.toJson((Object) list) + "  dd");
        return list;
    }

    public int getLCount() {
        try {
            return Integer.parseInt(read("l_count"));
        } catch (Exception unused) {
            return 0;
        }
    }

    public void setLCount(int i) {
        write("l_count", i + "");
    }

    public void setRated() {
        write("rated", "true");
    }

    public boolean isRated() {
        try {
            return Boolean.parseBoolean(read("rated"));
        } catch (Exception unused) {
            return false;
        }
    }

    public void onLogin() {
        write("login_status", "true");
    }

    public void onLogout() {
        write("login_status", "false");
    }

    public boolean isLoggedIn() {
        try {
            return Boolean.parseBoolean(read("login_status"));
        } catch (Exception unused) {
            return false;
        }
    }
}
