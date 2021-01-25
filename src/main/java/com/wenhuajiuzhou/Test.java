package com.wenhuajiuzhou;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.tag.BG;
import com.wenhuajiuzhou.util.FileUtil;
import com.wenhuajiuzhou.util.StringUtil;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String pattern = "([^;；]+?)([;；]%\\d{1,3}%\\d{1,3})?([,，][SQXQJZ]{2})?";
        String s = "1-21.tif";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(s);
        boolean matches = m.matches();
        if (matches) {
            String all = m.group(0);
            String group1 = m.group(1);
            String group2 = m.group(2);
            String group3 = m.group(3);

            System.out.println(all);
            System.out.println(group1);
            System.out.println(group2);
            System.out.println(group3);
        }
    }


}
