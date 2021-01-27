package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import com.wenhuajiuzhou.util.StringUtil;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class HL extends Tag {

    private static final String pattern = "(\\d*)([ZFDSQ=L。])?";

    public HL(String tagStr) {
        setTagStr(tagStr);
    }

}
