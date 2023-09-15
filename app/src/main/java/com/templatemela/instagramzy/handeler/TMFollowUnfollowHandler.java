package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.util.Log;

import com.google.common.net.HttpHeaders;
import com.templatemela.instagramzy.utils.Pref;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class TMFollowUnfollowHandler {
    Context context;
    String cookie;
    String csrf;
    Functions functions;
    HashMap<String, String> headers = new HashMap<>();
    Pref pref;
    String useragent;

    public TMFollowUnfollowHandler(Context context2) {
        context = context2;
        Functions functions2 = new Functions(context2);
        functions = functions2;
        cookie = functions2.getCookie();
        useragent = functions.getUserAgent();
        pref = new Pref(context2);
        try {
            csrf = functions.getCSRF();
        } catch (Exception unused) {
            csrf = "";
        }
    }

    private class Do implements Callable<Integer> {
        String id;
        int type;
        String username;

        public Do(String str, String str2, int i) {
            id = str;
            username = str2;
            type = i;
        }

        public Integer call() throws Exception {
           headers.put("Accept", "*/*");
           headers.put("accept-encoding", "gzip, deflate, br");
           headers.put("content-length", "0");
           headers.put("content-type", "application/x-www-form-urlencoded");
           headers.put("cookie",cookie);
           headers.put("origin", " https://www.instagram.com");
           headers.put(HttpHeaders.REFERER, "https://www.instagram.com/" + username + "/");
           headers.put("sec-ch-ua", "\"Chromium\";v=\"90\", \"Opera\";v=\"76\", \";Not A Brand\";v=\"99\"");
           headers.put("sec-ch-ua-mobile", "?0");
           headers.put("sec-fetch-dest", "empty");
           headers.put("sec-fetch-mode", "cors");
           headers.put("sec-fetch-site", HttpHeaders.ReferrerPolicyValues.SAME_ORIGIN);
           headers.put("User-Agent",useragent);
           headers.put("X-CSRFToken",csrf);
           headers.put("x-ig-app-id", "936619743392459");
           headers.put("x-instagram-ajax", "89ff327f9ee3");
            String str = "follow";
            try {
                if (type == 0) {
                    str = "unfollow";
                }
                String str2 = "https://www.instagram.com/web/friendships/" + id + "/" + str + "/";
                Log.i("url", str2);
                Connection.Response execute = Jsoup.connect(str2).userAgent(useragent).headers(headers).ignoreContentType(true).method(Connection.Method.POST).execute();
                Log.i("response_d", execute.body());
                if (execute.body().equals("{\"result\":\"requested\",\"status\":\"ok\"}")) {
                    return 1;
                }
                if (!execute.body().equals("{\"status\":\"ok\"}")) {
                    if (!execute.body().equals("{\"result\":\"following\",\"status\":\"ok\"}")) {
                        return 0;
                    }
                }
                return 2;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public int changeFriendship(String str, String str2, int i) {
        try {
            return ((Integer) Executors.newSingleThreadExecutor().submit(new Do(str, str2, i)).get()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
