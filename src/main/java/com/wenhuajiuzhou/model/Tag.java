package com.wenhuajiuzhou.model;

import lombok.Data;

@Data
public class Tag {

    private String whole;
    private String tag;
    private int start;
    private int end;
    private boolean isCustom;

    public void setWhole(String whole) {
        this.whole = whole;

        setCustom("=".equals(whole.substring(1, 2)));

        String tag;
        if (isCustom) {
            tag = whole.substring(2, whole.length() - 1);
        } else {
            tag = whole.substring(1, 3);
        }
        setTag(tag);
    }

}
