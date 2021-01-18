package com.wenhuajiuzhou.util;

public class TextHandler {

    public static final String[] USELESS_TAGS = {
            "PJS", "YM", "XD", "BF", "KH",
            "ZK", "HJ", "JZ", "KG", "WB", "DW",
            "WTXT", "WTBZ"
    };

    public static String deleteUselessTags(String content) {
        //替换空格
        content = content.replaceAll("〓", " ");

        for (String tag : USELESS_TAGS) {
            String regex = "〖" + tag + "[^〖〗]*〗";
            content = content.replaceAll(regex, "");
        }
        return content;
    }

}
