package com.templatemela.instagramzy.models;

public class TMChatNotificationModel {
    String msg;
    String name;
    String username;

    public TMChatNotificationModel(String str, String str2, String str3) {
        name = str;
        username = str2;
        msg = str3;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String str) {
        msg = str;
    }
}
