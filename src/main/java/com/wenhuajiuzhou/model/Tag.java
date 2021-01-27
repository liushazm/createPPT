package com.wenhuajiuzhou.model;

import lombok.Data;

@Data
public class Tag {

    //完整字符串，包括〖〗
    private String tagStr;
    //是否是divider
    private boolean isDivider;
    //标签，如BT、JZ、BG
    private String type;
    //在line中的start
    private int start;
    //在line中的end
    private int end;
    //是否自定义标签
    private boolean isCustom;
    //是否单标签
    private boolean isSingle;
    //是否双标签中的开始标签
    private boolean isStartTag;
    //参数
    protected String param;

    public void setTagStr(String tagStr) {
        this.tagStr = tagStr;
        init();
    }

    protected void init() {
//        System.out.println("init tagStr = " + tagStr);
        if ("〖〗".equals(tagStr)) {
            isDivider = true;
            return;
        }

        setCustom("=".equals(tagStr.substring(1, 2)));

        if (tagStr.contains("(") || tagStr.contains(")")
                || tagStr.contains("（") || tagStr.contains("）")) {
            setSingle(false);
            setStartTag(tagStr.contains("(") || tagStr.contains("（"));
        } else {
            setSingle(true);
        }

        if (isCustom) {
            type = tagStr.substring(2, tagStr.length() - 1);
        } else {
            type = tagStr.substring(1, 3);
            if (isSingle) {
                param = tagStr.substring(3, tagStr.length() - 1);
            } else {
                param = tagStr.substring(4, tagStr.length() - 1);
            }
        }
    }

}
