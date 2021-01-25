package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.util.StringUtil;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class CD extends Tag {

    private static final String pattern = "([#%])(\\d)";

    private String name;
    private int length;

    public CD(String tagStr) {
        setTagStr(tagStr);

        if (StringUtil.isNotEmpty(param)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(param);
            boolean matches = m.matches();
            if (matches) {
                name = m.group(1);
                String lengthStr = m.group(2);
                length = Integer.parseInt(lengthStr);
            }
        }
    }

}
