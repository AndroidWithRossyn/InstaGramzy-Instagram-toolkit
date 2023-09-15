package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;

import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.models.TMUserModel;

import org.json.JSONObject;

public class TMUserDetails {
    Context context;
    Functions functions;
    Pref pref;

    public TMUserDetails(Context context2) {
        context = context2;
        functions = new Functions(context2);
        pref = new Pref(context2);
    }

    public String getUserName() {
        try {
            TMUserModel user = getUser(true);
            pref.saveUserName(user.getUsername());
            return user.getName();
        } catch (Exception unused) {
            return "null";
        }
    }

    public TMUserModel getUser(Boolean bool) {
        int i;
        int i2;
        String str;
        String str2;
        String str3;
        int i3;
        try {
            if (bool.booleanValue()) {
                String userId = functions.getUserId();
                try {
                    String text = functions.page("https://i.instagram.com/api/v1/users/" + userId + "/info/").text();
                    Log.i("user_data__", text);
                    JSONObject jSONObject = new JSONObject(text).getJSONObject("user");
                    str3 = jSONObject.getString("full_name");
                    str2 = jSONObject.getString("username");
                    str = jSONObject.getString("profile_pic_url");
                    int i4 = jSONObject.getInt("following_count");
                    i = jSONObject.getInt("follower_count");
                    int i5 = jSONObject.getInt("media_count");
                    i3 = i4;
                    i2 = i5;
                } catch (Exception e) {
                    e.printStackTrace();
                    str3 = "Guest";
                    str2 = "guest";
                    str = pref.getNullDp();
                    i3 = 0;
                    i2 = 0;
                    i = 0;
                }
                String str4 = str;
                String str5 = str3;
                String str6 = str4;
                String str7 = str2;
                String str8 = str5;
                int i6 = i2;
                int i7 = i;
                String str9 = "postCount";
                String str10 = userId;
                try {
                    TMUserModel user = new TMUserModel(userId, str5, str7, str6, i3, i7, i6);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("id", str10);
                    jSONObject2.put("name", str8);
                    jSONObject2.put("username", str7);
                    jSONObject2.put("dp", str6);
                    jSONObject2.put("followers", i7);
                    jSONObject2.put("following", i3);
                    jSONObject2.put(str9, i6);
                    try {
                        pref.saveUserDetails(jSONObject2.toString());
                        return user;
                    } catch (Exception unused) {
                        return null;
                    }
                } catch (Exception unused2) {
                    return null;
                }
            } else {
                String str11 = "postCount";
                String savedUserDetails = pref.getSavedUserDetails();
                if (savedUserDetails.equals("null")) {
                    return getUser(true);
                }
                JSONObject jSONObject3 = new JSONObject(savedUserDetails);
                return new TMUserModel(jSONObject3.getString("id"), jSONObject3.getString("name"), jSONObject3.getString("username"), jSONObject3.getString("dp"), jSONObject3.getInt("following"), jSONObject3.getInt("followers"), jSONObject3.getInt(str11));
            }
        } catch (Exception unused3) {
            return null;
        }
    }

    public boolean isLoggedIn() {
        return pref.isLoggedIn();
    }

    public void logout() {
        CookieManager.getInstance().removeAllCookie();
        pref.onLogout();
    }

    public void login() {
        pref.onLogin();
    }

    public Boolean isFollowerOrFollowingChanged(int i, int i2) {
        getUser(false);
        TMUserModel user = getUser(true);
        if ((user.getFollowers() + "").equals(i + "")) {
            if ((user.getFollowing() + "").equals(i2 + "")) {
                return false;
            }
        }
        return true;
    }
}
