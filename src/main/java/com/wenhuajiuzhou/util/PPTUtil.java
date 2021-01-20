package com.wenhuajiuzhou.util;

import com.wenhuajiuzhou.model.Line;
import com.wenhuajiuzhou.model.Tag;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PPTUtil {

    private static final String FONT_COMMON = "宋体";
    private static final String FONT_HEI_TI = "黑体";
    private static final String FONT_KAI_TI = "楷体";

    private XMLSlideShow ppt = null;
    private XSLFSlideLayout layout;

    private String curFont = FONT_COMMON;

    private static PPTUtil instance;

    private PPTUtil() {
    }

    public static PPTUtil getInstance() {
        if (instance == null) {
            instance = new PPTUtil();
        }
        return instance;
    }

    //生成PPT文件
    public void generatePPT(int index, String pptStr) {
        //获取文件名
        int startIndex = pptStr.indexOf("〖BT1〗");
        int endIndex = pptStr.indexOf("〖HT〗", startIndex);
        String fileName = pptStr.substring(startIndex + 5, endIndex);
//        System.out.println(fileName);

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
                break;
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

    //生成一页PPT
    private void generatePage(String pageStr) {
        pageStr = pageStr.trim();
//        System.out.println(pageStr);
//        System.out.println("------------------------------------------------------------");

        //创建一页PPT
        XSLFSlide slide = ppt.createSlide(layout);
        ppt.setSlideOrder(slide, ppt.getSlides().size() - 2);

        // 在幻灯片中插入一个文本框
        XSLFTextShape ts = slide.createTextBox();
        // 设置文本框的位置和文本框大小
        ts.setAnchor(new Rectangle(10, 90, 940, 380));

        String[] lines = pageStr.split("\r\n");
        for (String line : lines) {
            addLineText(ts, line);
//            break;
        }

        // 设置文本框里面的文字

    }

    private void addLineText(XSLFTextShape ts, String lineStr) {
        lineStr = lineStr.trim();
        if (lineStr.equals("")) {
            return;
        }

        Line line = new Line(lineStr);


        List<Tag> tagList = line.getTagList();
        if (tagList == null || tagList.size() == 0) {
            addParagraph(ts, lineStr);
            return;
        }

        Tag tag1 = tagList.get(0);
        if (tag1.getStart() == 0) {

        }

    }



    private XSLFTextParagraph addParagraph(XSLFTextShape ts) {
        XSLFTextParagraph p = ts.addNewTextParagraph();
        return p;
    }

    private XSLFTextParagraph addParagraph(XSLFTextShape ts, String line) {
        XSLFTextParagraph p = ts.addNewTextParagraph();
        addTextRun(p, line);
        return p;
    }

    private void addTextRun(XSLFTextParagraph p, String line) {
        XSLFTextRun r = p.addNewTextRun();
        r.setText(line);
        r.setFontSize(28d);
        r.setFontFamily(curFont, FontGroup.EAST_ASIAN);
    }

}
