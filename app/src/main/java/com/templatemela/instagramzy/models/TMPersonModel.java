package com.templatemela.instagramzy.models;

public class TMPersonModel {
    private String dp;
    private String id;
    private Boolean isFollowedByMe;
    private Boolean isFollowing;
    private Boolean isVerified;
    private String name;
    private long time;
    private String username;

    public TMPersonModel(String str, String str2, String str3, String str4, Boolean bool, Boolean bool2, Boolean bool3, long j) {
        name = str;
        id = str2;
        username = str3;
        dp = str4;
        isFollowing = bool;
        isFollowedByMe = bool2;
        isVerified = bool3;
        time = j;
    }

    public String getName() {
        return name;
    }

    public void setName(String str) {
        name = str;
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

    public String getDp() {
        return dp;
    }

    public void setDp(String str) {
        dp = str;
    }

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setFollowing(Boolean bool) {
        isFollowing = bool;
    }

    public Boolean getFollowedByMe() {
        return isFollowedByMe;
    }

    public void setFollowedByMe(Boolean bool) {
        isFollowedByMe = bool;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean bool) {
        isVerified = bool;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long j) {
        time = j;
    }
}
