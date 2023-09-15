package com.templatemela.instagramzy.models;

public class TMStoryModel {
    String dp;
    String name;
    String storyId;
    String userId;
    String username;

    public TMStoryModel(String str, String str2, String str3, String str4, String str5) {
        username = str;
        userId = str2;
        name = str3;
        dp = str4;
        storyId = str5;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String str) {
        username = str;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String str) {
        userId = str;
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

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String str) {
        storyId = str;
    }
}
