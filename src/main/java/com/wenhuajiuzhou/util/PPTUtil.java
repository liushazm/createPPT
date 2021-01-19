package com.wenhuajiuzhou.util;

import org.apache.poi.common.usermodel.fonts.FontGroup;
import org.apache.poi.common.usermodel.fonts.FontInfo;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraphProperties;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class PPTUtil {

    private static PPTUtil instance;

    private XMLSlideShow ppt = null;
    private XSLFSlideLayout layout;

    private PPTUtil() {
    }
    public static PPTUtil getInstance() {
        if (instance == null) {
            instance = new PPTUtil();
        }
        return instance;
    }


    public void generatePPT(int index, String pptStr) {
        //获取文件名
        int startIndex = pptStr.indexOf("〖BT1〗");
        int endIndex = pptStr.indexOf("〖HT〗", startIndex);
        String fileName = pptStr.substring(startIndex + 5, endIndex);
        System.out.println(fileName);

        File fileTemp = new File("res/ppt/template.pptx");
        File fileDest = new File("res/ppt/" + index + "." + fileName + ".pptx");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileTemp);
            ppt = new XMLSlideShow(fis);
            //获取幻灯片模板
            layout = ppt.getSlideMasters().get(0).getLayout("content");
            //按每个PAGE拆分字符串
            String[] pageStrArray = pptStr.split("〖PAGE〗");
            for (String pageStr : pageStrArray) {
                generatePage(pageStr);
//                break;
            }

            // 将修改后的PPT文件回写到硬盘
            ppt.write(new FileOutputStream(fileDest));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(ppt);
        }
    }

    private void generatePage(String pageStr) {
        pageStr = pageStr.trim();
        System.out.println(pageStr);
        System.out.println("------------------------------------------------------------");

        pageStr = pageStr.replaceAll("\r\n", "\n");

        //创建一页PPT
        XSLFSlide slide = ppt.createSlide(layout);
        // 在幻灯片中插入一个文本框
        XSLFTextShape ts = slide.createTextBox();
        // 设置文本框的位置和文本框大小
        ts.setAnchor(new Rectangle(10, 90, 940, 380));
        // 设置文本框里面的文字
        XSLFTextParagraph p1 = ts.addNewTextParagraph();
        XSLFTextRun r1 = p1.addNewTextRun();
        r1.setText(pageStr);
        r1.setFontSize(20d);
        r1.setFontFamily("楷体", FontGroup.EAST_ASIAN);

    }

}
