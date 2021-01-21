package com.wenhuajiuzhou;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        // 按指定模式在字符串查找
        String content = "〖BT1〗第一单元达标测试卷〖HT〗";
        String pattern = "";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            String group = m.group();
            System.out.println(group.substring(5, group.length() - 4));
        }

    }

}
