package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import lombok.Data;

@Data
public class CS extends Tag {

    public CS(String tagStr) {
        setTagStr(tagStr);
    }

}
