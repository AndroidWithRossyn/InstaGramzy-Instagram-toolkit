package com.templatemela.instagramzy.models;

public class TMCropSizeModels {
    String title = "";
    int x;
    int y;

    public TMCropSizeModels(int i, int i2) {
        x = i;
        y = i2;
    }

    public TMCropSizeModels(String str, int i, int i2) {
        x = i;
        y = i2;
        title = str;
    }

    public int getX() {
        return x;
    }

    public void setX(int i) {
        x = i;
    }

    public int getY() {
        return y;
    }

    public void setY(int i) {
        y = i;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String str) {
        title = str;
    }
}
