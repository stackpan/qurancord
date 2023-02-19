package com.stackpan.qurancord.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String formatDiscord(String value) {
        return value.replaceAll("<i>|</i>", "_")
                .replaceAll("<br>", "\n");
    }

    public static String formatSurahNameToRegex(String latinName) {
        return latinName
                .replaceAll("-|\\s", Matcher.quoteReplacement("(-|\\s)?"))
                .replaceAll("'", "'?");
    }

    public static boolean matchSurahName(String regex, String search) {
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(search).find();
    }

    public static boolean isNumeric(String search) {
        return Pattern.compile("^[0-9]*$").matcher(search).matches();
    }

}
