package com.wenhuajiuzhou.util;

public class TextHandler {

    public static final String[] USELESS_TAGS = {
            "PJS", "YM", "XD", "BF", "KH",
            "ZK", "HJ", "JZ", "KG", "WB", "DW",
            "ST", "JP", "WTXT", "WTBZ"
    };

    public static String deleteUselessTags(String content) {
        //替换空格
        content = content.replaceAll("〓", " ");

        //删除换行
        content = content.replaceAll("\uE003", "");

        //删除无用tag
        for (String tag : USELESS_TAGS) {
            String regex = "〖" + tag + "[^〖〗]*〗";
            content = content.replaceAll(regex, "");
        }

        //替换多余空行
        while (content.contains("\r\n\r\n")) {
            content = content.replaceAll("\r\n\r\n", "\r\n");
        }
        return content;
    }

}
