package com.stackpan.util.test;

import com.stackpan.util.AyahBuilder;
import com.stackpan.util.StringFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AyahBuilderTest {
    @Test
    void testThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> AyahBuilder.createBuilder().build());
    }

    @Test
    void apaDah() {
        System.out.println(StringFormatter.formatDiscord("Surat ini terdiri atas 30 ayat, termasuk golongan surat-surat Makkiyah, diturunkan sesudah Ath Thuur.<br> Nama Al Mulk diambil dari kata Al Mulk yang terdapat pada ayat pertama surat ini yang artinya kerajaan atau kekuasaan. Dinamai pula surat ini dengan At Tabaarak (Maha Suci)."));
    }
}
