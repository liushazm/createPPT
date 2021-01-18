package com.wenhuajiuzhou.util;

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

    public static void add() {
        File fileTemp = new File("res/ppt/template.pptx");
        File fileDest = new File("res/ppt/1.pptx");

        XMLSlideShow ppt = null;
        try {
            // 通过输入流读取一个现有的PPT文件，生成PPT类
            ppt = new XMLSlideShow(new FileInputStream(fileTemp));
            //获取幻灯片主题列表：
            List<XSLFSlideMaster> slideMasters = ppt.getSlideMasters();
            //获取幻灯片的布局样式
            XSLFSlideLayout layout = slideMasters.get(0).getLayout("content");
            //通过布局样式创建幻灯片
            XSLFSlide slide = ppt.createSlide(layout);

            // 在现有的PPT文件后面新建一个空白幻灯片
//            XSLFSlide slide1 = ppt.createSlide();

            // 将修改后的PPT文件回写到硬盘
            ppt.write(new FileOutputStream(fileDest));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ppt);
        }
    }

    public static void createPPT(String path) throws IOException {
        // 创建ppt:
        XMLSlideShow ppt = new XMLSlideShow();

        //设置幻灯片的大小：
        Dimension pageSize = ppt.getPageSize();
        pageSize.setSize(1440, 900);
        ppt.setPageSize(pageSize);

        //获取幻灯片主题列表：
        List<XSLFSlideMaster> slideMasters = ppt.getSlideMasters();
        //获取幻灯片的布局样式
        XSLFSlideLayout layout = slideMasters.get(0).getLayout(SlideLayout.TITLE_AND_CONTENT);
        //通过布局样式创建幻灯片
        XSLFSlide slide = ppt.createSlide(layout);
        // 创建一张无样式的幻灯片
//        XSLFSlide slide = ppt.createSlide();

        //通过当前幻灯片的布局找到第一个空白区：
        XSLFTextShape placeholder = slide.getPlaceholder(0);
        XSLFTextRun title = placeholder.setText("成都智互联科技有限公司");
        XSLFTextShape content = slide.getPlaceholder(1);
        //   投影片中现有的文字
        content.clearText();
        content.setText("图片区");

        // reading an image
        File image = new File("res/image/logo.jpg");
        //获取图片信息：
        BufferedImage img = ImageIO.read(image);
        // converting it into a byte array
        byte[] picture = IOUtils.toByteArray(new FileInputStream(image));

        // adding the image to the presentation
        XSLFPictureData idx = ppt.addPicture(picture, PictureData.PictureType.PNG);

        // creating a slide with given picture on it
        XSLFPictureShape pic = slide.createPicture(idx);
        //设置当前图片在ppt中的位置，以及图片的宽高
        pic.setAnchor(new Rectangle(10, 200, 200, 200));
        // creating a file object
        File file = new File(path);
        FileOutputStream out = new FileOutputStream(file);
        // saving the changes to a file
        ppt.write(out);
        System.out.println("image added successfully");
        out.close();
    }

}
