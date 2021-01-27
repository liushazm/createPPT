package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.util.StringUtil;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class BT extends Tag {

    private static final String pattern = "(\\d)(#)?";

    private int level;

    public BT(String tagStr) {
        setTagStr(tagStr);

        if (StringUtil.isNotEmpty(param)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(param);
            boolean matches = m.matches();
            if (matches) {
                String levelStr = m.group(1);
                if (StringUtil.isNotEmpty(levelStr)) {
                    level = Integer.parseInt(levelStr);
                }
            }
        }
    }

}
