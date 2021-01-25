package com.wenhuajiuzhou;

import com.wenhuajiuzhou.util.FileUtil;
import com.wenhuajiuzhou.util.PPTUtil;
import com.wenhuajiuzhou.util.TextHandler;

public class CreatePPT {

    public static void main(String[] args) {
        generatePPTs();
    }

    public static void generatePPTs() {
        String path = "res/fbd/1.fbd";
        String content = FileUtil.readFileContent(path);
        //处理字符串
        content = TextHandler.deleteUselessTags(content);
//        System.out.println(content);

        //按每个PPT拆分字符串
        String[] pptStrArray = content.split("〖LM〗");
        for (int i = 0; i < pptStrArray.length; i++) {
            String pptStr = pptStrArray[i];
            PPTUtil.getInstance().generatePPT(i + 1, pptStr);
            System.out.println("generatePPT : " + (i + 1));
        }

    }

}
