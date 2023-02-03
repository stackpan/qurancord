package com.stackpan.util;

public class StringUtil {

    public static String formatDiscord(String value) {
        return value.replaceAll("<i>|</i>", "_")
                .replaceAll("<br>", "\n");
    }

//    public static boolean matchSurah(String wanted, String search) {
//
//        Pattern pattern = Pattern.compile("", Pattern.CASE_INSENSITIVE);
//    }


}
