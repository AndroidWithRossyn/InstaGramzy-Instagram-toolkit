package com.templatemela.instagramzy.models;

public class TMMessageItemModel {
    String id;
    String text;
    String type;
    String url;
    String userid;

    public TMMessageItemModel(String str, String str2, String str3, String str4, String str5) {
        id = str;
        type = str3;
        text = str4;
        url = str5;
        userid = str2;
    }

    public String getId() {
        return id;
    }

    public void setId(String str) {
        id = str;
    }

    public String getType() {
        return type;
    }

    public void setType(String str) {
        type = str;
    }

    public String getText() {
        return text;
    }

    public void setText(String str) {
        text = str;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String str) {
        url = str;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String str) {
        userid = str;
    }
}
