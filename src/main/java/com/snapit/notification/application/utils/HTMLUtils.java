package com.snapit.notification.application.utils;

import com.snapit.notification.application.utils.exception.HTMLToStringException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HTMLUtils {

    private HTMLUtils() {
    }

    public static String readHTMLToString(File file) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            throw new HTMLToStringException("Failed to read HTML file as string");
        }
        return contentBuilder.toString();
    }

}
