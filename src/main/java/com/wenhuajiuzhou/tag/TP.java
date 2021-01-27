package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.util.StringUtil;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class TP extends Tag {

    private static final String pattern = "([^;；]+)([;；][SXZY]\\d+)*?([,，][SXZY])?";

    private String name;

    public TP(String tagStr) {
        setTagStr(tagStr);

        System.out.println("TP param = " + param);
        if (StringUtil.isTrimNotEmpty(param)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(param);
            boolean matches = m.matches();
            System.out.println("matches = " + matches);
            if (matches) {
                name = m.group(1);
                System.out.println("TP name = " + name);
                String g2 = m.group(2);
                System.out.println("g2 = " + g2);
                String g3 = m.group(3);
                System.out.println("g3 = " + g3);
            }
        }
    }

}
