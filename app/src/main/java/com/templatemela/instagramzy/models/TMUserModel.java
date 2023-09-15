package com.templatemela.instagramzy.models;

public class TMUserModel {
    private String dp;
    private int followers;
    private int following;
    private String id;
    private String name;
    private int postCount;
    private String username;

    public TMUserModel(String str, String str2, String str3, String str4, int i, int i2, int i3) {
        id = str;
        name = str2;
        username = str3;
        dp = str4;
        following = i;
        followers = i2;
        postCount = i3;
    }

    public String getId() {
        return id;
    }

    public void setId(String str) {
        id = str;
    }

    public String getName() {
        return name;
    }

    public void setName(String str) {
        name = str;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String str) {
        username = str;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String str) {
        dp = str;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int i) {
        following = i;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int i) {
        followers = i;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int i) {
        postCount = i;
    }
}
