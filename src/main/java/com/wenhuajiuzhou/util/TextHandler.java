package com.wenhuajiuzhou.util;

public class TextHandler {

    public static final String[] USELESS_TAGS = {
            "PJS", "YM", "XD", "BF", "KH",
            "ZK", "HJ", "JZ", "WB", "JY",
            "ST", "JP", "WTXT", "WTBZ"
    };

    public static String deleteUselessTags(String content) {
        //删除无用tag
        for (String tag : USELESS_TAGS) {
            String regex = "〖" + tag + ".*?〗";
            content = content.replaceAll(regex, "");
        }

        //删除自定义标签
        content = content.replaceAll("〖ZD.*?[\\(（]〗[\\s\\S]*?〖ZD[\\)）]〗", "");
        //删除边排
        content = content.replaceAll("〖BP[\\(（]〗[\\s\\S]*?〖BP[\\)）]〗", "");
        content = content.replaceAll("〖BW[\\(（].*?〗[\\s\\S]*?〖BW[\\)）]〗", "");
        //删除盒组括弧
        content = content.replaceAll("\uE008", "");
        content = content.replaceAll("\uE009", "");

        //列竖式不支持，替换成红色文字
        content = content.replaceAll("〖FK[\\(（]W〗[\\s\\S]*?〖\\d〗[\\s\\S]*?〖FK[\\)）]〗",
                "〖CS%65,0,0,0〗列竖式不支持，请手动替换〖CS〗");

        //替换空格
        content = content.replaceAll("〓", "  ");
        //替换DW
        content = content.replaceAll("〖DW.*?〗", "\t");
        //替换换行
        content = content.replaceAll("\uE003", "〖BR〗");
        content = content.replaceAll("\uE004", "〖BR〗");
        return content;
    }

}
