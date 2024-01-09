package com.ivanzkyanto.qcv2.util;

import java.util.regex.Matcher;

public class StringUtils {

    public static String regexifySurahName(String surahName) {
        return surahName.replaceAll("-|\\s", Matcher.quoteReplacement("(-|\\s)?"))
                .replaceAll("'", "'?")
                .replaceAll("aa", "aa?");
    }

}
