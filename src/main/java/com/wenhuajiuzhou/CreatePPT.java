package com.wenhuajiuzhou;

import com.wenhuajiuzhou.util.FileUtil;
import com.wenhuajiuzhou.util.PPTUtil;
import org.apache.poi.util.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatePPT {

    public static void main(String[] args) {
        handleText();
    }

    private static void handleText() {
        String content = FileUtil.readFileContent("res/fbd/1.fbd");
        while (content.contains("\r\n\r\n")) {
            content = content.replace("\r\n\r\n", "\r\n");
        }

        String[] split = content.split("〖BT1〗");
        System.out.println(split.length);

    }

    public static void regex() {
        String fbdPath = "res/fbd/1.fbd";
        String content = FileUtil.readFileContent(fbdPath);

        Pattern p = Pattern.compile("〖[^〖〗]*〗");
        Matcher m = p.matcher(content);
        while (m.find()) {
            System.out.println(m.group());
            System.out.print("start:" + m.start());
            System.out.println(" end:" + m.end());
        }
    }

}
