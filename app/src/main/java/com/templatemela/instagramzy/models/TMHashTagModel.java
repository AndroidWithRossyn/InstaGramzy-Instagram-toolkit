package com.templatemela.instagramzy.models;

public class TMHashTagModel {
    String name;
    int postCount;

    public TMHashTagModel(String str, int i) {
        name = str;
        postCount = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String str) {
        name = str;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int i) {
        postCount = i;
    }
}
