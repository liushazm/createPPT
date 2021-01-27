package com.wenhuajiuzhou.util;

import com.wenhuajiuzhou.model.Page;
import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.tag.*;
import org.apache.poi.common.usermodel.fonts.FontGroup;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

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
    private Dimension mPageSize;
    private XSLFSlide mSlide;
    private XSLFTextShape mTs;
    private XSLFTextParagraph mPrg;
    private Page mPage;
    private List<Tag> mTagList;
    private int mIndex;
    private Tag mTag;
    private String mText;
    private boolean isUnderline;
    private XSLFTable mTable;
    private XSLFTableRow mRow;
    private boolean isCreatingTable;
    private StringBuilder mCellText;

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
//        System.out.println(pptStr);
        //获取文件名
        String fileName = getFileName(pptStr);
        System.out.println("fileName = " + fileName);

        File fileTemp = new File("res/ppt/template_yuwen.pptx");
        File fileDest = new File("res/ppt/" + index + "." + fileName + ".pptx");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileTemp);
            mPPT = new XMLSlideShow(fis);
            mPageSize = mPPT.getPageSize();


            //获取幻灯片模板
            mLayout = mPPT.getSlideMasters().get(0).getLayout("content");
            //按每个PAGE拆分字符串
            String[] pageStrArray = pptStr.split("〖PAGE〗");
            for (String pageStr : pageStrArray) {
                generatePage(pageStr);
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
        String titleTag = "〖BT1#〗";
        int start = pptStr.indexOf(titleTag);
        int end = pptStr.indexOf("〖BR〗", start);
        return pptStr.substring(start + titleTag.length(), end);
    }

    //生成一页PPT
    private void generatePage(String pageStr) {
//        System.out.println(pageStr);

        //创建一页PPT
        mSlide = mPPT.createSlide(mLayout);
        mPPT.setSlideOrder(mSlide, mPPT.getSlides().size() - 2);

        // 在幻灯片中插入一个文本框
        mTs = mSlide.createTextBox();
        mTs.setAnchor(new Rectangle(10, 90, 940, 380));
        mPrg = null;

        addParagraph();

        mPage = new Page(pageStr);
        mTagList = mPage.getTagList();
        //无标签
        if (mTagList == null || mTagList.size() == 0) {
            addTextRun(pageStr);
            return;
        }
        //起始位置不是标签
        int start = mTagList.get(0).getStart();
        if (start != 0) {
            addTextRun(pageStr.substring(0, start));
        }
        //依次处理标签
        handleTags();
    }

    private void handleTags() {
//        System.out.println(mLine.getLineStr());
        for (mIndex = 0; mIndex < mTagList.size(); mIndex++) {
            mTag = mTagList.get(mIndex);
            if (mIndex != mTagList.size() - 1) {
                int start = mTag.getEnd();
                int end = mTagList.get(mIndex + 1).getStart();
                mText = mPage.getPageStr().substring(start, end);
            } else {
                int start = mTag.getEnd();
                mText = mPage.getPageStr().substring(start);
            }
            handleTag();
        }
    }

    private void handleTag() {
        if (mTag.isDivider()) {
            handleTagDivider();
        } else {
            switch (mTag.getType()) {
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
                case "KG":
                    handleTagKG();
                    break;
                case "BT":
                    handleTagBT();
                    break;
                case "HT":
                    handleTagHT();
                    break;
                case "ZZ":
                    handleTagZZ();
                    break;
                case "BR":
                    addParagraph();
                    addTextRun(mText);
                    break;
                case "BG":
                    handleTagBG();
                    break;
                case "BH":
                    handleTagBH();
                    break;
                case "XC":
                    handleTagXC();
                    break;
                case "TP":
                    handleTagTP();
                    break;
                case "CD":
                    handleTagCD();
                    break;
                case "CS":
                    handleTagCS();
                    break;
                case "CX":
                    handleTagCX();
                    break;
                case "FK":
                    handleTagFK();
                    break;
                default:
                    System.out.println(mTag.getType() + " tag 没有处理。");
                    break;
            }
        }
    }

    private void handleTagKG() {
        KG kg = new KG(mTag.getTagStr());
        addTextRun("  ");
        addTextRun(mText);
    }

    private void handleTagFK() {
        FK fk = new FK(mTag.getTagStr());
        isUnderline = fk.isStartTag();
        addTextRun(mText);
    }

    private void handleTagCX() {
        addTextRun(mText);
    }

    private void handleTagCS() {
        CS cs = new CS(mTag.getTagStr());
        if (StringUtil.isNotEmpty(cs.getParam())) {
            mColor = COLOR_RED;
        } else {
            mColor = COLOR_BLACK;
        }
        addTextRun(mText);
    }

    private void handleTagCD() {
        CD cd = new CD(mTag.getTagStr());
        int length = cd.getLength();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("  ");
        }
        isUnderline = true;
        addTextRun(builder.toString());
        isUnderline = false;
        addTextRun(mText);
    }

    private void handleTagTP() {
        TP tp = new TP(mTag.getTagStr());
        addPicture(tp.getName(), false);
    }

    private void handleTagXC() {
        XC xc = new XC(mTag.getTagStr());
        addPicture(xc.getName());
    }

    private void handleTagDivider() {
        addTableCell();
        initCellText();
    }

    private void initCellText() {
        mCellText = new StringBuilder();
        addTextRun(mText);
    }

    private void addTableCell() {
        XSLFTableCell cell = mRow.addCell();
        String text = mCellText.toString();
        XSLFTextParagraph p = cell.addNewTextParagraph();
        p.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun r = p.addNewTextRun();
        r.setText(text);
        r.setFontSize(28d);
        r.setFontFamily(mFont, FontGroup.EAST_ASIAN);
        r.setFontFamily(FONT_TIMES, FontGroup.LATIN);
        r.setFontColor(mColor);
        r.setUnderlined(isUnderline);

        // 设置单元格边框的粗细
        cell.setBorderWidth(TableCell.BorderEdge.bottom, 1);
        cell.setBorderWidth(TableCell.BorderEdge.left, 1);
        cell.setBorderWidth(TableCell.BorderEdge.right, 1);
        cell.setBorderWidth(TableCell.BorderEdge.top, 1);
        // 设置单元格边框的颜色
        cell.setBorderColor(TableCell.BorderEdge.bottom, Color.BLACK);
        cell.setBorderColor(TableCell.BorderEdge.left, Color.BLACK);
        cell.setBorderColor(TableCell.BorderEdge.right, Color.BLACK);
        cell.setBorderColor(TableCell.BorderEdge.top, Color.BLACK);
    }

    private void handleTagBH() {
        if (mCellText != null) {
            addTableCell();
        }
        mRow = mTable.addRow();
        initCellText();
        addLineBreak();
    }

    private void handleTagBG() {
        if (mTag.isStartTag()) {
            isCreatingTable = true;
            mCellText = null;
            mTable = mSlide.createTable();
            mTable.setAnchor(new Rectangle(10, 130, 500, 200));
        } else {
            addTableCell();
            isCreatingTable = false;
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
        String typeface = ht.getTypeface();
        if (StringUtil.isEmpty(param)) {
            mFont = FONT_SONG_TI;
        } else if (StringUtil.isNotEmpty(typeface)) {
            switch (typeface) {
                case "H":
                    mFont = FONT_HEI_TI;
                    break;
                case "K":
                    mFont = FONT_KAI_TI;
                    break;
                case "F":
                    mFont = FONT_FANG_SONG;
                    break;
                default:
                    mFont = FONT_SONG_TI;
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
//        System.out.println("text = " + text);
        if (isCreatingTable) {
            if (!StringUtil.isEmpty(text)) {
                mCellText.append(text.trim());
            }
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

    private void addPicture(String img) {
        addPicture(img, true);
    }

    private void addPicture(String img, boolean isXC) {
        File file = new File("res/image/" + img);
        if (file.exists()) {
            try {
                IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
                BufferedImage image = ImageIO.read(file);
                int width = image.getWidth() / 8;
                int height = image.getHeight() / 8;
                int x = isXC ? 50 : (int) (mPageSize.getWidth() - width - 100);
                int y = (int) mTs.getTextHeight() + 90;

                // 将图片添加到PPT中
                XSLFPictureData pd = mPPT.addPicture(file, PictureData.PictureType.TIFF);
                // 将图片放到指定的幻灯片中
                XSLFPictureShape pic = mSlide.createPicture(pd);
                // 设置图片框的放置的位置和大小
                pic.setAnchor(new Rectangle(x, y, width, height));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("image file : " + img + " 不存在。");
        }
    }

}
