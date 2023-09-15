package com.templatemela.instagramzy.models;

public class TMGridItem {
    int GridCount;
    String GridImagePath;
    Boolean is_share = false;

    public Boolean getIs_share() {
        return is_share;
    }

    public void setIs_share(Boolean bool) {
        is_share = bool;
    }

    public int getGridCount() {
        return GridCount;
    }

    public void setGridCount(int i) {
        GridCount = i;
    }

    public String getGridImagePath() {
        return GridImagePath;
    }

    public boolean isChecked() {
        return is_share.booleanValue();
    }

    public void setChecked(boolean z) {
        is_share = Boolean.valueOf(z);
    }

    public void setGridImagePath(String str) {
        GridImagePath = str;
    }

    public TMGridItem(int i, String str, Boolean bool) {
        GridCount = i;
        GridImagePath = str;
        is_share = bool;
    }
}
