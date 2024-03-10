package com.ivanzkyanto.qcv2.util;

import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.model.AyahReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AyahImageGeneratorTest {

    @Autowired
    private AyahFetcher ayahFetcher;

    @Test
    void generate() {
        var arabic = ayahFetcher.get(new AyahReference(1, 1), "quran-uthmani");
        var translate = ayahFetcher.get(new AyahReference(1, 1), "en.asad");

        AyahImageGenerator.init()
                .setArabicText(arabic.getData().getText())
                .setTranslatedText(translate.getData().getText())
                .setSurahName(arabic.getData().getSurah().getEnglishName())
                .setAyahNumber(arabic.getData().getNumberInSurah())
                .generate("surah:%d_ayah:%d_edition:%s_translate:%s".formatted(1, 1, "quran-uthmani", "en.asad"));
    }
}
