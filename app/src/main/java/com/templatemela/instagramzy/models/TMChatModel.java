package com.templatemela.instagramzy.models;

public class TMChatModel {
    String dp;
    boolean isSeen;
    String lastMessage;
    String name;
    String threadId;
    long time;
    int unseenCount = 0;
    String username;

    public TMChatModel(String str, String str2, String str3, String str4, String str5, long j, boolean z) {
        name = str;
        username = str2;
        dp = str3;
        lastMessage = str4;
        threadId = str5;
        time = j;
        isSeen = z;
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

    public String getDp() {
        return dp;
    }

    public void setDp(String str) {
        dp = str;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String str) {
        lastMessage = str;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String str) {
        threadId = str;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long j) {
        time = j;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean z) {
        isSeen = z;
    }

    public int getUnseenCount() {
        return unseenCount;
    }

    public void setUnseenCount(int i) {
        unseenCount = i;
    }
}
