package com.templatemela.instagramzy.models;

public class TMStoryResModel {
    int type;
    String url;

    public TMStoryResModel(int i, String str) {
        type = i;
        url = str;
    }

    public int getType() {
        return type;
    }

    public void setType(int i) {
        type = i;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String str) {
        url = str;
    }
}
