package com.templatemela.instagramzy.handeler;

import android.content.Context;

import com.templatemela.instagramzy.models.TMUserModel;
import org.json.JSONException;
import org.json.JSONObject;

public class TMPersonDetails {
    Context context;
    Functions functions;

    public TMPersonDetails(Context context2) {
        context = context2;
        functions = new Functions(context2);
    }

    public String getPersonId(String str) {
        String document = functions.page("https://www.instagram.com/" + str + "/?__a=1").toString();
        int indexOf = document.indexOf("\"id\"") + 6;
        return document.substring(indexOf, document.indexOf("\"", indexOf));
    }

    public String getProfilePicture(String str) {
        try {
            return new JSONObject(functions.page("https://i.instagram.com/api/v1/users/" + str + "/info/").text()).getJSONObject("user").getJSONObject("hd_profile_pic_url_info").getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public TMUserModel getPersonIdFromUrl(String str) {
        if (str.contains("?utm_med")) {
            str = str.substring(0, str.indexOf("?utm_med"));
            if (!str.endsWith("/")) {
                str = str + "/";
            }
        }
        String document = functions.page(str + "?__a=1").toString();
        int indexOf = document.indexOf("\"id\"") + 6;
        String substring = document.substring(indexOf, document.indexOf("\"", indexOf));
        int indexOf2 = document.indexOf("count", document.indexOf("edge_followed_by")) + 7;
        String substring2 = document.substring(indexOf2, document.indexOf("}", indexOf2));
        int indexOf3 = document.indexOf("count", document.indexOf("edge_follow", indexOf2)) + 7;
        String substring3 = document.substring(indexOf3, document.indexOf("}", indexOf3));
        int indexOf4 = document.indexOf("full_name", indexOf3) + 12;
        String substring4 = document.substring(indexOf4, document.indexOf("\"", indexOf4));
        int indexOf5 = document.indexOf("https", document.indexOf("profile_pic_url_hd", indexOf4) + 12);
        return new TMUserModel(substring, substring4, "username", document.substring(indexOf5, document.indexOf("\"", indexOf5)).replaceAll("amp;", ""), Integer.parseInt(substring2), Integer.parseInt(substring3), 0);
    }
}
