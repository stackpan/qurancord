package io.github.stackpan.qurancord.util;

import java.util.ArrayList;
import java.util.List;
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

    public static List<String> smartSplit(String value) {
        List<String> substrings = new ArrayList<>();
        String[] words = value.split("\\s+");

        StringBuilder currentSubstringBuilder = new StringBuilder();
        for (String word: words) {
            if (currentSubstringBuilder.length() + word.length() + 1 <= 1024) {
                if (!currentSubstringBuilder.isEmpty()) {
                    currentSubstringBuilder.append(" ");
                }
                currentSubstringBuilder.append(word);
            } else {
                substrings.add(currentSubstringBuilder.toString());
                currentSubstringBuilder = new StringBuilder(word);
            }
        }

        substrings.add(currentSubstringBuilder.toString());
        return substrings;
    }

    public static String sanitize(String input) {
        return input.replaceAll("[/?&#]", "");
    }

}
