package com.templatemela.instagramzy.models;

public class TMPostModel {
    int commentCount;
    String id;
    int likeCount;
    String location;
    String pictureUrl;
    String postUrl;
    int type;
    int views;

    public TMPostModel(String str, String str2, String str3, String str4, int i, int i2, int i3, int i4) {
        id = str;
        location = str2;
        postUrl = str3;
        pictureUrl = str4;
        likeCount = i;
        commentCount = i2;
        type = i3;
        views = i4;
    }

    public String getId() {
        return id;
    }

    public void setId(String str) {
        id = str;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String str) {
        location = str;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String str) {
        postUrl = str;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String str) {
        pictureUrl = str;
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

    public int getType() {
        return type;
    }

    public void setType(int i) {
        type = i;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int i) {
        views = i;
    }
}
