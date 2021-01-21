package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.util.StringUtil;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ZZ extends Tag {

    private static final String pattern = "(\\d*)([ZFDSQ=L。])?";

    private int number;
    private String symbol;

    public ZZ(String tagStr) {
        setTagStr(tagStr);

        if (StringUtil.isNotEmpty(param)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(param);
            boolean matches = m.matches();
            if (matches) {
                String nStr = m.group(1);
                if (StringUtil.isNotEmpty(nStr)) {
                    number = Integer.parseInt(nStr);
                }
                symbol = m.group(2);
            }
        }
    }

}
