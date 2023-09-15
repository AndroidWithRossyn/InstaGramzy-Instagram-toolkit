package com.templatemela.instagramzy.models;

public class TMBottomItemModel {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getIconDisable() {
        return iconDisable;
    }

    public void setIconDisable(int iconDisable) {
        this.iconDisable = iconDisable;
    }

    int id ;


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    int icon;

    public int getIconEnable() {
        return iconEnable;
    }

    public void setIconEnable(int iconEnable) {
        this.iconEnable = iconEnable;
    }

    int iconEnable;
    int iconDisable;

    public TMBottomItemModel(int id, int icon, int iconEnable, int iconDisable) {
        this.id = id;
        this.icon= icon;
        this.iconEnable = iconEnable;
        this.iconDisable = iconDisable;
    }



}
