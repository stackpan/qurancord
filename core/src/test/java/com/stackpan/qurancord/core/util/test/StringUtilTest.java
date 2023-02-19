package com.stackpan.qurancord.core.util.test;

import com.stackpan.qurancord.core.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {
    @Test
    void testFormatSurahNamePattern() {
        Assertions.assertEquals("Al(-|\\s)?Fatihah", StringUtil.formatSurahNameToRegex("Al-Fatihah"));
        Assertions.assertEquals("Ali(-|\\s)?'?Imran", StringUtil.formatSurahNameToRegex("Ali 'Imran"));
        Assertions.assertEquals("Al(-|\\s)?'?Ankabut", StringUtil.formatSurahNameToRegex("Al-'Ankabut"));
        Assertions.assertEquals("Ali(-|\\s)?'?Imran", StringUtil.formatSurahNameToRegex("Ali 'Imran"));
        Assertions.assertEquals("Asy(-|\\s)?Syu'?ara'?", StringUtil.formatSurahNameToRegex("Asy-Syu'ara'"));
    }

    @Test
    void testMatchSurahName() {
        String[] regexes = {"Al(-|\\s)?Fatihah", "Ali(-|\\s)?'?Imran", "Asy(-|\\s)?Syu'?ara'?"};

        Assertions.assertTrue(StringUtil.matchSurahName(regexes[0], "alfatihah"));
        Assertions.assertTrue(StringUtil.matchSurahName(regexes[0], "al-fatihah"));
        Assertions.assertTrue(StringUtil.matchSurahName(regexes[0], "al fatihah"));
        Assertions.assertTrue(StringUtil.matchSurahName(regexes[0], "Al Fatihah"));
        Assertions.assertTrue(StringUtil.matchSurahName(regexes[1], "Aliimran"));
        Assertions.assertTrue(StringUtil.matchSurahName(regexes[1], "Ali imran"));
        Assertions.assertTrue(StringUtil.matchSurahName(regexes[2], "asy syuara"));
        Assertions.assertFalse(StringUtil.matchSurahName(regexes[0], "ali imran"));
        Assertions.assertFalse(StringUtil.matchSurahName(regexes[1], "ali imron"));
        Assertions.assertFalse(StringUtil.matchSurahName(regexes[2], "al bayyinah"));
    }

    @Test
    void testIsNumeric() {
        String[] searches = {"34", "Alfatihah", "yasin234"};

        Assertions.assertTrue(StringUtil.isNumeric(searches[0]));
        Assertions.assertFalse(StringUtil.isNumeric(searches[1]));
        Assertions.assertFalse(StringUtil.isNumeric(searches[2]));
    }
}
