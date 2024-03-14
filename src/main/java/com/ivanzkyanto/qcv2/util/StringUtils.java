package com.ivanzkyanto.qcv2.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String regexifySurahName(String surahName) {
        return surahName.replaceAll("-|\\s", Matcher.quoteReplacement("(-|\\s)?"))
                .replaceAll("'", "'?")
                .replaceAll("aa", "aa?");
    }

    public static String getFileExtension(String filename) {
        String extension = "";

        var matcher = Pattern.compile("\\.(\\w+)$").matcher(filename);

        if (matcher.find()) {
            extension = matcher.group(1);
        }

        return extension;
    }

}
