package com.wenhuajiuzhou;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.tag.BG;
import com.wenhuajiuzhou.util.FileUtil;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) {

//        createPPT();

    }

    private static void createPPT() {
        File file = new File("res/ppt/test.pptx");
        FileOutputStream fos = null;

        // 创建一个空白PPT
        XMLSlideShow ppt = new XMLSlideShow();
        // 在空白的PPT中创建一个空白的幻灯片
        XSLFSlide slide = ppt.createSlide();

        // 在幻灯片中插入一个表格
        XSLFTable table = slide.createTable();
        // 设置表格的位置和表格大小
        table.setAnchor(new Rectangle(50, 100, 100, 100));
        for (int i = 0; i < 5; i++) {
            // 在表格中添加一行
            XSLFTableRow row = table.addRow();
            for (int j = 0; j < 5; j++) {
                addCell(row, String.valueOf(i) + j);
            }
        }

        try {
            fos = new FileOutputStream(file);
            // 对新建的PPT保存到硬盘里
            ppt.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(ppt);
        }
    }

    private static void addCell(XSLFTableRow row, String text) {
        // 在行中添加一个单元格
        XSLFTableCell cell = row.addCell();
        // 设置单元格中的内容和样式
        XSLFTextParagraph p = cell.addNewTextParagraph();
        p.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun tr1 = p.addNewTextRun();
        tr1.setFontColor(Color.RED);
        tr1.setText(text);

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

}
