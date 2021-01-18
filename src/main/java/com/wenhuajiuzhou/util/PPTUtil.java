package com.wenhuajiuzhou.util;

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

    public static void add() {
        File fileTemp = new File("res/ppt/template.pptx");
        File fileDest = new File("res/ppt/1.pptx");

        XMLSlideShow ppt = null;
        try {
            // 通过输入流读取一个现有的PPT文件，生成PPT类
            ppt = new XMLSlideShow(new FileInputStream(fileTemp));

            Dimension size = ppt.getPageSize();
            System.out.println("width : " + size.getWidth()+", height :" + size.getHeight());

            //获取幻灯片主题列表：
            List<XSLFSlideMaster> slideMasters = ppt.getSlideMasters();
            //获取幻灯片的布局样式
            XSLFSlideLayout layout = slideMasters.get(0).getLayout("content");
            //通过布局样式创建幻灯片
            XSLFSlide slide = ppt.createSlide(layout);

            // 在幻灯片中插入一个文本框
            XSLFTextShape ts = slide.createTextBox();
            // 设置文本框的位置和文本框大小
            ts.setAnchor(new Rectangle(10, 80, 940, 380));
            // 设置文本框里面的文字
            XSLFTextParagraph paragraph = ts.addNewTextParagraph();
            XSLFTextRun bt1 = paragraph.addNewTextRun();
            bt1.setText("第一部分积累与运用（共30分）");
            bt1.setFontFamily("黑体");
            bt1.setFontSize(32d);
            paragraph.addLineBreak();

            XSLFTextRun bt2 = paragraph.addNewTextRun();
            bt2.setText("一、联系语境，在横线上规范地写出词语。（每空1分，共6分）");
            bt2.setFontFamily("黑体");
            bt2.setFontSize(32d);
            paragraph.addLineBreak();

            XSLFTextRun content = paragraph.addNewTextRun();
            content.setText("除夕之夜，家家户户［dēng huǒ tōng xiāo］〖ZZ(Z〗 〖=C1〗灯火通宵〖=C1F〗〖HTK〗 〖ZZ)〗、喜气洋洋。我们一家人各自忙碌着：大门外，爸爸负责贴［duì lián］\uE008\uE009〖ZZ(Z〗\uE008 〖=C1〗对联〖=C1F〗〖HTK〗 \uE009〖ZZ)〗；厨房里，奶奶忙着包［jiǎo zi］〖ZZ(Z〗 〖=C1〗饺子〖=C1F〗〖HTK〗 〖ZZ)〗，妈妈忙着炒菜，汤在锅里［fèi ténɡ］〖ZZ(Z〗 〖=C1〗沸腾〖=C1F〗〖HTK〗 〖ZZ)〗着；我和爷爷在餐厅准备碗筷；9岁的弟弟在院子里［rán fàng］〖ZZ(Z〗 〖=C1〗燃放〖=C1F〗〖HTK〗 〖ZZ)〗鞭炮，听到不［jiàn duàn］\uE008〖ZZ(Z〗 〖=C1〗间断〖=C1F〗〖HTK〗 〖ZZ)〗\uE009的鞭炮声，他开心地欢呼起来……处处充满了幸福和快乐。〖HT〗\uE003");
            content.setFontFamily("楷体");
            content.setFontSize(32d);

            // 将修改后的PPT文件回写到硬盘
            ppt.write(new FileOutputStream(fileDest));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ppt);
        }
    }

}
