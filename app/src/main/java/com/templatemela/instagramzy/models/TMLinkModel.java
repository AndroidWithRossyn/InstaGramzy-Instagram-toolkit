package com.templatemela.instagramzy.models;

public class TMLinkModel {
    int clicks;
    String des;
    int id;
    String thum;
    String title;
    String url;

    public TMLinkModel(int i, String str, String str2, String str3, String str4, int i2) {
        title = str;
        des = str2;
        url = str3;
        thum = str4;
        clicks = i2;
        id = i;
    }

    public TMLinkModel(int i) {
        title = "";
        des = "";
        url = "";
        thum = "";
        clicks = 0;
        id = i;
    }

    public TMLinkModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String str) {
        title = str;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String str) {
        des = str;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String str) {
        url = str;
    }

    public String getThum() {
        return thum;
    }

    public void setThum(String str) {
        thum = str;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int i) {
        clicks = i;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        id = i;
    }
}
