package com.wenhuajiuzhou.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Page {

    private String pageStr;
    private List<Tag> tagList;

    public Page(String pageStr) {
        init(pageStr);
    }

    private void init(String pageStr) {
        this.pageStr = pageStr;
        tagList = parseTags();
    }

    //解析tag
    private List<Tag> parseTags() {
        Pattern p = Pattern.compile("〖.*?〗");
        Matcher m = p.matcher(pageStr);
        tagList = null;
        while (m.find()) {
            if (tagList == null) {
                tagList = new ArrayList<>();
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

}
