package com.templatemela.instagramzy.models;

public class TMProfileModel {
    int clicks;
    String link;

    public TMProfileModel(String str, int i) {
        link = str;
        clicks = i;
    }

    public TMProfileModel() {
        link = "";
        clicks = 0;
    }

    public String getLink() {
        return link;
    }

    public TMProfileModel setLink(String str) {
        link = str;
        return this;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int i) {
        clicks = i;
    }
}
