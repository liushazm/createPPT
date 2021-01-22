package com.wenhuajiuzhou.util;

import com.wenhuajiuzhou.model.Line;
import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.tag.BT;
import com.wenhuajiuzhou.tag.HT;
import com.wenhuajiuzhou.tag.ZZ;
import org.apache.poi.common.usermodel.fonts.FontGroup;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PPTUtil {

    private static final String FONT_SONG_TI = "宋体";
    private static final String FONT_HEI_TI = "黑体";
    private static final String FONT_KAI_TI = "楷体";
    private static final String FONT_FANG_SONG = "仿宋";
    private static final String FONT_TIMES = "Times New Roman";
    private static final Color COLOR_BLACK = Color.BLACK;
    private static final Color COLOR_RED = Color.RED;

    private XSLFSlideLayout mLayout;
    private XMLSlideShow mPPT = null;
    private XSLFSlide mSlide;
    private XSLFTextShape mTs;
    private XSLFTextParagraph mPrg;
    private Line mLine;
    private List<Tag> mTagList;
    private int mIndex;
    private Tag mTag;
    private String mText;
    private boolean isUnderline;

    private String mFont = FONT_SONG_TI;
    private Color mColor = COLOR_BLACK;

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
        System.out.println(pptStr);
        //获取文件名
        String fileName = getFileName(pptStr);
//        System.out.println(fileName);

        File fileTemp = new File("res/ppt/template.pptx");
        File fileDest = new File("res/ppt/" + index + "." + fileName + ".pptx");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileTemp);
            mPPT = new XMLSlideShow(fis);

            //获取幻灯片模板
            mLayout = mPPT.getSlideMasters().get(0).getLayout("content");
            //按每个PAGE拆分字符串
            String[] pageStrArray = pptStr.split("〖PAGE〗");
            for (String pageStr : pageStrArray) {
                generatePage(pageStr);
//                break;
            }

            // 将修改后的PPT文件回写到硬盘
            mPPT.write(new FileOutputStream(fileDest));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(mPPT);
        }
    }

    private String getFileName(String pptStr) {
        int start = pptStr.indexOf("〖BT1〗");
        int end = pptStr.indexOf("〖HT〗", start);
        return pptStr.substring(start + 5, end);
    }

    //生成一页PPT
    private void generatePage(String pageStr) {
//        System.out.println(pageStr);
        pageStr = pageStr.trim();

        //创建一页PPT
        mSlide = mPPT.createSlide(mLayout);
        mPPT.setSlideOrder(mSlide, mPPT.getSlides().size() - 2);

        // 在幻灯片中插入一个文本框
        mTs = mSlide.createTextBox();
        // 设置文本框的位置和文本框大小
        mTs.setAnchor(new Rectangle(10, 90, 940, 380));

        String[] lines = pageStr.split("\n");
        for (String line : lines) {
            addLineText(line);
//            System.out.println("---------------------------------");
        }

    }

    private void addLineText(String lineStr) {
        lineStr = lineStr.trim();
        if (lineStr.equals("")) {
            return;
        }

        if (lineStr.startsWith("〖BT1〗")) {
            return;
        }

        mLine = new Line(lineStr);
        mTagList = mLine.getTagList();
        if (!mLine.isHasContent()) {
            //没有需要显示的内容，即完全无内容或只有标签
            if (mTagList.size() != 0) {
                handleTags();
            }
        } else {
            //有需要显示的内容
            addParagraph();
            mPrg.setTextAlign(TextParagraph.TextAlign.LEFT);
            if (mTagList == null || mTagList.size() == 0) {
                //无标签
                addTextRun(lineStr);
            } else {
                //有标签
                int start = mTagList.get(0).getStart();
                //line开始部分不是标签
                if (start != 0) {
                    addTextRun(lineStr.substring(0, start));
                }
                handleTags();
            }
        }
    }

    private void handleTags() {
//        System.out.println(mLine.getLineStr());
        for (mIndex = 0; mIndex < mTagList.size(); mIndex++) {
            mTag = mTagList.get(mIndex);
            if (mIndex != mTagList.size() - 1) {
                int start = mTag.getEnd();
                int end = mTagList.get(mIndex + 1).getStart();
                mText = mLine.getLineStr().substring(start, end);
            } else {
                int start = mTag.getEnd();
                mText = mLine.getLineStr().substring(start);
            }
            handleTag();
        }

    }

    private void handleTag() {
        switch (mTag.getType()) {
            case "BT":
                handleTagBT();
                break;
            case "HT":
                handleTagHT();
                break;
            case "ZZ":
                handleTagZZ();
                break;
            case "C1":
                mColor = COLOR_RED;
                mFont = FONT_KAI_TI;
                addTextRun(mText);
                break;
            case "C1F":
                mColor = COLOR_BLACK;
                mFont = FONT_SONG_TI;
                addTextRun(mText);
                break;
        }
    }

    private void handleTagZZ() {
        ZZ zz = new ZZ(mTag.getTagStr());
        if (!zz.isSingle()) {
            isUnderline = mTag.isStartTag();
            addTextRun(mText);
        } else {
            int number = zz.getNumber();
            addTextRun(number, mText);
        }
    }

    private void handleTagHT() {
        HT ht = new HT(mTag.getTagStr());
        String param = ht.getParam();
        String size = ht.getSize();
        String typeface = ht.getTypeface();
        if (StringUtil.isEmpty(param)) {
            mFont = FONT_SONG_TI;
        } else if (StringUtil.isNotEmpty(typeface)) {
            switch (typeface) {
                case "":
                    mFont = FONT_SONG_TI;
                    break;
                case "H":
                    mFont = FONT_HEI_TI;
                    break;
                case "K":
                    mFont = FONT_KAI_TI;
                    break;
                case "F":
                    mFont = FONT_FANG_SONG;
                    break;
            }
        }
        addTextRun(mText);
    }

    private void handleTagBT() {
        BT bt = new BT(mTag.getTagStr());
        int level = bt.getLevel();
        switch (level) {
            case 1:
                break;
            case 2:
                mPrg.setTextAlign(TextParagraph.TextAlign.CENTER);
                mFont = FONT_HEI_TI;
                addTextRun(mText);
                break;
            case 3:
            case 4:
            case 5:
                mFont = FONT_HEI_TI;
                addTextRun(mText);
                break;
        }
        mFont = FONT_SONG_TI;
    }


    private void addParagraph() {
        if (mPrg == null) {
            mPrg = mTs.getTextParagraphs().get(0);
        } else {
            mPrg = mTs.addNewTextParagraph();
        }
    }

    private void addTextRun(String text) {
        if (StringUtil.isEmpty(text)) {
            return;
        }
        XSLFTextRun r = mPrg.addNewTextRun();
        r.setText(text);
        r.setFontSize(28d);
        r.setFontFamily(mFont, FontGroup.EAST_ASIAN);
        r.setFontFamily(FONT_TIMES, FontGroup.LATIN);
        r.setFontColor(mColor);
        r.setUnderlined(isUnderline);
    }

    private void addTextRun(int zzNumber, String text) {
        isUnderline = true;
        if (text.length() <= zzNumber) {
            addTextRun(text);
            isUnderline = false;
        } else {
            addTextRun(text.substring(0, zzNumber));

            isUnderline = false;
            addTextRun(text.substring(zzNumber));
        }
    }

    private void addLineBreak() {
        mPrg.addLineBreak();
    }

}
