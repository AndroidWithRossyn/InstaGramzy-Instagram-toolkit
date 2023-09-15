package com.templatemela.instagramzy.models;

public class TMLikerCommenterModel {
    int commentCount;
    String dp;
    String id;
    int likeCount;
    String name;
    String username;

    public TMLikerCommenterModel(String str, String str2, String str3, String str4, int i, int i2) {
        id = str;
        username = str2;
        name = str3;
        dp = str4;
        likeCount = i;
        commentCount = i2;
    }

    public String getId() {
        return id;
    }

    public void setId(String str) {
        id = str;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String str) {
        username = str;
    }

    public String getName() {
        return name;
    }

    public void setName(String str) {
        name = str;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String str) {
        dp = str;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int i) {
        likeCount = i;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int i) {
        commentCount = i;
    }
}
