package com.templatemela.instagramzy.models;

import java.util.ArrayList;
import java.util.List;

public class TMLinksModel {
    List<TMLinkModel> links;

    public TMLinksModel(List<TMLinkModel> list) {
        links = list;
    }

    public TMLinksModel() {
        links = new ArrayList();
    }

    public List<TMLinkModel> getLinks() {
        return links;
    }

    public void setLinks(List<TMLinkModel> list) {
        links = list;
    }
}
