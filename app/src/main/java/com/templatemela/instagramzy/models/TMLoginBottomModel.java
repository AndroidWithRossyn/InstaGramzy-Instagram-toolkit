package com.templatemela.instagramzy.models;

public class TMLoginBottomModel {
    String des;
    int image;
    String title;

    public TMLoginBottomModel(int i, String str, String str2) {
        image = i;
        title = str;
        des = str2;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int i) {
        image = i;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String str) {
        title = str;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String str) {
        des = str;
    }
}
