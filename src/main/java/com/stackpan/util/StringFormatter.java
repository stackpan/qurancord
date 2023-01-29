package com.stackpan.util;

public class StringFormatter {

    public static String formatDiscord(String value) {
        return value.replaceAll("<i>|</i>", "_")
                .replaceAll("<br>", "\n");
    }

}
