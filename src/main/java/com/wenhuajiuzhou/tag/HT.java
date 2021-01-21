package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.util.StringUtil;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class HT extends Tag {

    private static final String pattern = "([\\d\"]*)([HKF])?";

    private String size;
    private String typeface;

    public HT(String tagStr) {
        setTagStr(tagStr);

        if (StringUtil.isNotEmpty(param)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(param);
            boolean matches = m.matches();
            if (matches) {
                size = m.group(1);
                typeface = m.group(2);
            }
        }
    }

}
