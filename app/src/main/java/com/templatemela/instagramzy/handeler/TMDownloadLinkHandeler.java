package com.templatemela.instagramzy.handeler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

public class TMDownloadLinkHandeler {
    Context context;
    Functions functions;
    String tag = "downloader";
    Handler handler = new Handler(Looper.getMainLooper());

    public TMDownloadLinkHandeler(Context context) {
        this.context = context;
        this.functions = new Functions(context);
    }

    public void generateLinks(final String str) {
        new Thread(new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                TMDownloadLinkHandeler.this.g(str);
            }
        }).start();
    }

    public void g(String str) {
        String str2;
        String trim = str.trim();
        if (trim.contains("/?utm_medium=copy_link")) {
            trim = trim.replace("?utm_medium=copy_link", "");
        }
        if (trim.contains("?utm_medium=share_sheet")) {
            trim = trim.replace("?utm_medium=share_sheet", "");
        }
        String str3 = trim.endsWith("/") ? trim + "?__a=1" : trim + "/?__a=1";
        Log.i(this.tag, str3);
        try {
            String text = this.functions.page(str3).text();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject(text).getJSONArray("items").getJSONObject(0);
            try {
                str2 = jSONObject.getJSONObject("caption").getString("text").replaceAll("\n", "").replaceAll(" ", "_");
            } catch (Exception unused) {
                str2 = new Random().nextInt() + "";
            }
            if (!jSONObject.isNull("carousel_media")) {
                JSONArray jSONArray2 = jSONObject.getJSONArray("carousel_media");
                for (int i = 0; i < jSONArray2.length(); i++) {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
                    if (!jSONObject2.isNull("video_versions")) {
                        jSONArray.put(parseVideoData(jSONObject2, str2));
                    } else {
                        jSONArray.put(parseImageData(jSONObject2, str2));
                    }
                }
            } else if (!jSONObject.isNull("video_versions")) {
                jSONArray.put(parseVideoData(jSONObject, str2));
            } else {
                jSONArray.put(parseImageData(jSONObject, str2));
            }
            EventBus.getDefault().post(jSONArray);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(this.tag, e.getMessage());
            this.handler.post(new Runnable() {
                @Override // java.lang.Runnable
                public void run() {
                    if (new TMUserDetails(TMDownloadLinkHandeler.this.context).isLoggedIn()) {
                        if (e.getMessage().contains("Value Google of type java.lang.String cannot")) {
                            Toast.makeText(TMDownloadLinkHandeler.this.context, "This is a private post. You are logged in with a different account.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TMDownloadLinkHandeler.this.context, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                        ((Activity) TMDownloadLinkHandeler.this.context).finish();
                        return;
                    }
                    Toast.makeText(TMDownloadLinkHandeler.this.context, "Login and try again", Toast.LENGTH_LONG).show();
                    ((Activity) TMDownloadLinkHandeler.this.context).finish();
                }
            });
        }
    }

    JSONObject parseImageData(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            JSONArray jSONArray = jSONObject.getJSONObject("image_versions2").getJSONArray("candidates");
            String string = jSONArray.getJSONObject(0).getString("url");
            jSONObject2.put("thum", jSONArray.getJSONObject(jSONArray.length() - 1).getString("url"));
            jSONObject2.put("link", string);
            jSONObject2.put("type", 0);
            if (str.length() > 18) {
                jSONObject2.put("name", str.substring(0, 18) + ".jpg");
            } else {
                jSONObject2.put("name", str + ".jpg");
            }
            jSONObject2.put("size", getFileSize(string));
            return jSONObject2;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    JSONObject parseVideoData(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            JSONArray jSONArray = jSONObject.getJSONArray("video_versions");
            String string = jSONArray.getJSONObject(0).getString("url");
            jSONObject2.put("thum", jSONObject.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(jSONArray.length() - 1).getString("url"));
            jSONObject2.put("link", string);
            jSONObject2.put("duration", this.functions.getFormatedTime((int) jSONObject.getDouble("video_duration")));
            jSONObject2.put("type", 1);
            if (str.length() > 18) {
                jSONObject2.put("name", str.substring(0, 18) + ".mp4");
            } else {
                jSONObject2.put("name", str + ".mp4");
            }
            jSONObject2.put("size", getFileSize(string));
            return jSONObject2;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    JSONObject parseImageData1(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            JSONArray jSONArray = jSONObject.getJSONArray("display_resources");
            String string = jSONArray.getJSONObject(jSONArray.length() - 1).getString("src");
            jSONObject2.put("link", string);
            jSONObject2.put("type", 0);
            jSONObject2.put("thum", jSONObject.getString("display_url"));
            jSONObject2.put("size", getFileSize(string));
            return jSONObject2;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    JSONObject parseVideoData1(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            String string = jSONObject.getString("video_url");
            jSONObject2.put("link", string);
            jSONObject2.put("type", 1);
            jSONObject2.put("thum", jSONObject.getString("display_url"));
            jSONObject2.put("size", getFileSize(string));
            return jSONObject2;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    String getFileSize(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.connect();
            return this.functions.humanReadableByteCountBin(httpURLConnection.getContentLength());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}