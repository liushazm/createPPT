package com.wenhuajiuzhou.tag;

import com.wenhuajiuzhou.model.Tag;
import lombok.Data;

@Data
public class BT extends Tag {

    private int level;

    public BT(String tagStr) {
        setTagStr(tagStr);
        level = Integer.parseInt(param);
    }

}
