package com.wenhuajiuzhou.util;

public class StringUtil {

    public static boolean isTrimEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    public static boolean isTrimNotEmpty(String str){
        return !isTrimEmpty(str);
    }

    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

}
