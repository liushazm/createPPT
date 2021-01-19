package com.wenhuajiuzhou.util;

import org.apache.poi.util.IOUtils;

import java.io.*;

public class FileUtil {

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                if (!tempStr.trim().equals("\r\n")) {
                    builder.append(tempStr);
                    builder.append("\r\n");
                }
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return builder.toString();
    }

    public void writeStr2File(String content, String path) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

}
