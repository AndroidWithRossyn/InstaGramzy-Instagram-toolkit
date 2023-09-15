package com.templatemela.instagramzy.models;

public class TMStrModel {
    String data;
    int type;

    public TMStrModel(String str, int i) {
        data = str;
        type = i;
    }

    public String getData() {
        return data;
    }

    public void setData(String str) {
        data = str;
    }

    public int getType() {
        return type;
    }

    public void setType(int i) {
        type = i;
    }
}
