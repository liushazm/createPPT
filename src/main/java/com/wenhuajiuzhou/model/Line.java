package com.wenhuajiuzhou.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Line {

    private String lineStr;
    private List<Tag> tagList;
    //如果都是tag，就是没有内容
    private boolean hasContent;
    //是否有tag
    private boolean hasTag;

    public Line(String lineStr) {
        init(lineStr);
    }

    private void init(String lineStr) {
        this.lineStr = lineStr;
        tagList = parseTags();
        initHasContent();
    }

    public void setLineStr(String lineStr) {
        init(lineStr);
    }

    //解析tag
    private List<Tag> parseTags() {
        Pattern p = Pattern.compile("〖.*?〗");
        Matcher m = p.matcher(lineStr);
        tagList = null;
        hasTag = false;
//        System.out.println("length : " + lineStr.length());
        while (m.find()) {
            if (tagList == null) {
                tagList = new ArrayList<>();
                hasTag = true;
            }
            String group = m.group();
            int start = m.start();
            int end = m.end();
//            System.out.println(group + " start : " + start + ", end : " + end);
            Tag tag = new Tag();
            tag.setTagStr(group);
            tag.setStart(start);
            tag.setEnd(end);
            tagList.add(tag);
        }
        return tagList;
    }

    private void initHasContent() {
        if (lineStr.equals("")) {
            hasContent = false;
            return;
        }

        if (!hasTag) {
            hasContent = true;
            return;
        }

        if (tagList.get(0).getStart() != 0
                || tagList.get(tagList.size() - 1).getEnd() != lineStr.length()) {
            hasContent = true;
            return;
        }

        for (int i = 1; i < tagList.size(); i++) {
            if (tagList.get(i).getStart() - tagList.get(i - 1).getEnd() > 1) {
                hasContent = true;
                return;
            }
        }
        hasContent = false;
    }

}
