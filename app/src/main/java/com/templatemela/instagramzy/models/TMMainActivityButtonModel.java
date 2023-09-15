package com.templatemela.instagramzy.models;

public class TMMainActivityButtonModel {
    int id;
    String listCount = "0";
    int picture;
    String text;

    public TMMainActivityButtonModel(String str, int i, int i2) {
        text = str;
        picture = i;
        id = i2;
    }

    public TMMainActivityButtonModel(String str, int i, int i2, String str2) {
        text = str;
        picture = i;
        id = i2;
        listCount = str2;
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
