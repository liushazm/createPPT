package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.util.StringUtil;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class XC extends Tag {

    private static final String pattern = "([^;；]+?)([;；]%\\d{1,3}%\\d{1,3})?([,，][SQXQJZ]{2})?";

    private String name;

    public XC(String tagStr) {
        setTagStr(tagStr);

        if (StringUtil.isNotEmpty(param)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(param);
            boolean matches = m.matches();
            if (matches) {
                name = m.group(1);
            }
        }
    }

}
