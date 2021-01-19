package com.wenhuajiuzhou;

import com.wenhuajiuzhou.util.FileUtil;
import com.wenhuajiuzhou.util.PPTUtil;
import com.wenhuajiuzhou.util.TextHandler;

public class CreatePPT {

    public static void main(String[] args) {
        generatePPTs();
    }

    public static void generatePPTs() {
        String fbdPath = "res/fbd/1.fbd";
        String content = FileUtil.readFileContent(fbdPath);

        content = TextHandler.deleteUselessTags(content);
//        System.out.println(content);

        //按每个PPT拆分字符串
        String[] pptStrArray = content.split("〖PPT〗");
        for (int i = 0; i < pptStrArray.length; i++) {
            String pptStr = pptStrArray[i];
            PPTUtil.getInstance().generatePPT(i, pptStr);
            break;
        }

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
