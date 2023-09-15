package com.templatemela.instagramzy.utils;

import android.content.Context;
import android.util.Log;

import com.templatemela.instagramzy.models.TMPostModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMLikedOrCommentedPostsHelper {
    Context context;
    List<TMPostModel> postModelList;
    List<TMPostModel> posts;
    Pref pref;

    public TMLikedOrCommentedPostsHelper(Context context2) {
        context = context2;
        Pref pref2 = new Pref(context2);
        pref = pref2;
        postModelList = pref2.getAllSavedPost();
    }

    public List<TMPostModel> getPosts(String str, int i) {
        if (i == 0) {
            return getLikedPosts(str);
        }
        if (i == 1) {
            return getCommentedPosts(str);
        }
        return getDeletedLikedCommentedPosts(str, i);
    }

    
    public List<TMPostModel> getLikedPosts(String str) {
        posts = new ArrayList();
        JSONArray savedLikeCommentList = pref.getSavedLikeCommentList();
        for (int i = 0; i < savedLikeCommentList.length(); i++) {
            try {
                JSONObject jSONObject = savedLikeCommentList.getJSONObject(i);
                JSONArray jSONArray = jSONObject.getJSONArray("likers");
                int i2 = 0;
                while (true) {
                    if (i2 >= jSONArray.length()) {
                        break;
                    } else if (jSONArray.getJSONObject(i2).getString("pk").equals(str)) {
                        String string = jSONObject.getString("id");
                        Iterator<TMPostModel> it = postModelList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            TMPostModel next = it.next();
                            if (next.getId().equals(string)) {
                                posts.add(next);
                                break;
                            }
                        }
                    } else {
                        i2++;
                    }
                }
                jSONArray.toString().contains(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    
    public List<TMPostModel> getCommentedPosts(String str) {
        posts = new ArrayList();
        JSONArray savedLikeCommentList = pref.getSavedLikeCommentList();
        for (int i = 0; i < savedLikeCommentList.length(); i++) {
            try {
                JSONObject jSONObject = savedLikeCommentList.getJSONObject(i);
                if (jSONObject.getJSONArray("commenters").toString().contains(str)) {
                    String string = jSONObject.getString("id");
                    Iterator<TMPostModel> it = postModelList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        TMPostModel next = it.next();
                        if (next.getId().equals(string)) {
                            posts.add(next);
                            break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    
    public List<TMPostModel> getDeletedLikedCommentedPosts(String str, int i) {
        posts = new ArrayList();
        JSONArray whoDeletedLikesOrComments = pref.getWhoDeletedLikesOrComments(i - 2);
        Log.i("InteractionHelper", whoDeletedLikesOrComments.toString());
        for (int i2 = 0; i2 < whoDeletedLikesOrComments.length(); i2++) {
            try {
                JSONObject jSONObject = whoDeletedLikesOrComments.getJSONObject(i2);
                if (jSONObject.getString("id").equals(str)) {
                    JSONArray jSONArray = jSONObject.getJSONArray("posts");
                    Log.i("InteractionHelper", jSONArray.toString());
                    for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                        String string = jSONArray.getJSONObject(i3).getString("postId");
                        Iterator<TMPostModel> it = postModelList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            TMPostModel next = it.next();
                            if (next.getId().equals(string)) {
                                posts.add(next);
                                break;
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }
}
