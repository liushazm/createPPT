package com.wenhuajiuzhou.util;

public class TextHandler {

    public static final String[] USELESS_TAGS = {
            "PJS", "YM", "XD", "BF", "KH",
            "ZK", "HJ", "JZ", "KG", "WB", "DW",
            "ST", "JP", "WTXT", "WTBZ"
    };

    public static String deleteUselessTags(String content) {
        //删除（临时）--start
//        content = content.replaceAll("〖FK\\(.*〗[\\s\\S]*?〖FK\\)〗", "");
//        content = content.replaceAll("^.*?时间\\：90分钟.*?$", "");
        //删除（临时）--end

        //删除无用tag
        for (String tag : USELESS_TAGS) {
            String regex = "〖" + tag + ".*?〗";
            content = content.replaceAll(regex, "");
        }

        //删除自定义标签
        content = content.replaceAll("〖ZD.*?\\(〗[\\s\\S]*?〖ZD\\)〗", "");
        //删除边排
        content = content.replaceAll("〖BP\\(〗[\\s\\S]*?〖BP\\)〗", "");
        content = content.replaceAll("〖BW\\(.*?〗[\\s\\S]*?〖BW\\)〗", "");
        //删除盒组括弧
        content = content.replaceAll("\uE008", "");
        content = content.replaceAll("\uE009", "");

        //替换空格
        content = content.replaceAll("〓", " ");
        //替换换行
        content = content.replaceAll("\uE003", "\n");
        content = content.replaceAll("\uE004", "\n");
        return content;
    }

}
