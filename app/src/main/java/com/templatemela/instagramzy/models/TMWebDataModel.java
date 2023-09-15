package com.templatemela.instagramzy.models;

import java.util.ArrayList;
import java.util.List;

public class TMWebDataModel {
    String bio;
    String dp;
    TMProfileModel fb;
    TMProfileModel insta;
    TMProfileModel linkedin;
    List<TMLinkModel> links;
    String name;
    TMProfileModel phone;
    int theme;
    TMProfileModel twiter;
    String username;
    TMProfileModel whatsapp;
    TMProfileModel youtube;

    public TMWebDataModel(String str, String str2, String str3, String str4, int i, List<TMLinkModel> list, List<TMProfileModel> list2) {
        name = "";
        username = "";
        bio = "";
        dp = "";
        theme = 0;
        name = str;
        username = str2;
        bio = str3;
        dp = str4;
        theme = i;
        links = list;
    }

    public TMWebDataModel() {
        name = "";
        username = "";
        bio = "";
        dp = "";
        theme = 0;
        links = new ArrayList();
        fb = new TMProfileModel();
        insta = new TMProfileModel();
        linkedin = new TMProfileModel();
        phone = new TMProfileModel();
        whatsapp = new TMProfileModel();
        twiter = new TMProfileModel();
        youtube = new TMProfileModel();
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

    public String getBio() {
        return bio;
    }

    public void setBio(String str) {
        bio = str;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String str) {
        dp = str;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int i) {
        theme = i;
    }

    public List<TMLinkModel> getLinks() {
        return links;
    }

    public void setLinks(List<TMLinkModel> list) {
        links = list;
    }

    public TMProfileModel getFb() {
        return fb;
    }

    public void setFb(TMProfileModel profile) {
        fb = profile;
    }

    public TMProfileModel getInsta() {
        return insta;
    }

    public void setInsta(TMProfileModel profile) {
        insta = profile;
    }

    public TMProfileModel getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(TMProfileModel profile) {
        linkedin = profile;
    }

    public TMProfileModel getPhone() {
        return phone;
    }

    public void setPhone(TMProfileModel profile) {
        phone = profile;
    }

    public TMProfileModel getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(TMProfileModel profile) {
        whatsapp = profile;
    }

    public TMProfileModel getTwiter() {
        return twiter;
    }

    public void setTwiter(TMProfileModel profile) {
        twiter = profile;
    }

    public TMProfileModel getYoutube() {
        return youtube;
    }

    public void setYoutube(TMProfileModel profile) {
        youtube = profile;
    }
}
