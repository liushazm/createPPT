package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.util.StringUtil;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class KG extends Tag {

    private static final String pattern = "-?(\\d*?[\\*\\d]+?|[\\d\\.]+mm)";

    public KG(String tagStr) {
        setTagStr(tagStr);

        if (StringUtil.isNotEmpty(param)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(param);
            boolean matches = m.matches();
            if (matches) {
                String str = m.group(1);
            }
        }
    }

}
