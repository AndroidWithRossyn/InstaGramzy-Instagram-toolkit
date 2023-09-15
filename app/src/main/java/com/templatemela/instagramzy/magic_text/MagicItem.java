package com.templatemela.instagramzy.magic_text;


public class MagicItem {

    
    public String titleText;

    
    public String previewText = "Preview text";

    public MagicItem(String str) {
        titleText = str;
    }

    
    public String getTitleText() {
        return titleText;
    }

    
    public String getPreview() {
        return previewText;
    }

    
    public void setPreview(String str) {
        previewText = str;
    }
}
