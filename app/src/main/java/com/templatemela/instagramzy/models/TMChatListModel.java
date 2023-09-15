package com.templatemela.instagramzy.models;

import java.util.List;

public class TMChatListModel {
    String cursor;
    List<TMChatModel> list;
    boolean shouldAdd;

    public TMChatListModel(List<TMChatModel> list2, String str, boolean z) {
        list = list2;
        cursor = str;
        shouldAdd = z;
    }

    public List<TMChatModel> getList() {
        return list;
    }

    public void setList(List<TMChatModel> list2) {
        list = list2;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String str) {
        cursor = str;
    }

    public boolean isShouldAdd() {
        return shouldAdd;
    }

    public void setShouldAdd(boolean z) {
        shouldAdd = z;
    }
}
