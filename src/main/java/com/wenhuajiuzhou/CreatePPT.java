package com.wenhuajiuzhou;

import com.wenhuajiuzhou.util.FileUtil;
import com.wenhuajiuzhou.util.PPTUtil;
import com.wenhuajiuzhou.util.TextHandler;
import org.apache.poi.util.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatePPT {

    public static void main(String[] args) {
        PPTUtil.add();
    }

    public static void regex() {
        String fbdPath = "res/fbd/1.fbd";
        String content = FileUtil.readFileContent(fbdPath);

        String s = TextHandler.deleteUselessTags(content);
        System.out.println(s);

//        Pattern p = Pattern.compile("〖[^〖〗]*〗");
//        Pattern p = Pattern.compile("〖JZ[^〖〗]*〗");
//        Matcher m = p.matcher(content);
//        while (m.find()) {
//            System.out.println(m.group());
//            System.out.print("start:" + m.start());
//            System.out.println(" end:" + m.end());
//        }
    }

}
