package com.templatemela.instagramzy.models;

public class TMFollowAnalysisModel {
    int id;
    String listCount = "0";
    int picture;
    String text;

    public boolean isBadge() {
        return isBadge;
    }

    public void setBadge(boolean badge) {
        isBadge = badge;
    }

    public int getBadgeColor() {
        return badgeColor;
    }

    public void setBadgeColor(int badgeColor) {
        this.badgeColor = badgeColor;
    }

    boolean isBadge;
    int badgeColor;

    public TMFollowAnalysisModel(String str, int i, int i2) {
        text = str;
        picture = i;
        id = i2;
    }

    public TMFollowAnalysisModel(String str, int i, int i2, String str2, int badgeColor, boolean isBadge) {
        text = str;
        picture = i;
        id = i2;
        listCount = str2;
        this.badgeColor= badgeColor;
        this.isBadge=isBadge;
    }

    public String getText() {
        return text;
    }

    public void setText(String str) {
        text = str;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int i) {
        picture = i;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        id = i;
    }

    public String getListCount() {
        return listCount;
    }

    public void setListCount(String str) {
        listCount = str;
    }
}
