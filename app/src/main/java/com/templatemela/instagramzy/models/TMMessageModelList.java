package com.templatemela.instagramzy.models;

import java.util.List;

public class TMMessageModelList {
    boolean hasOlder;
    List<TMMessageItemModel> list;
    boolean shouldAdd;

    public TMMessageModelList(List<TMMessageItemModel> list2, boolean z, boolean z2) {
        list = list2;
        shouldAdd = z;
        hasOlder = z2;
    }

    public List<TMMessageItemModel> getList() {
        return list;
    }

    public void setList(List<TMMessageItemModel> list2) {
        list = list2;
    }

    public boolean isShouldAdd() {
        return shouldAdd;
    }

    public void setShouldAdd(boolean z) {
        shouldAdd = z;
    }

    public boolean isHasOlder() {
        return hasOlder;
    }

    public void setHasOlder(boolean z) {
        hasOlder = z;
    }
}
