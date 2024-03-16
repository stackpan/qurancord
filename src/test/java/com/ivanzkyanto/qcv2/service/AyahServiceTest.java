package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.exception.AyahNotFoundException;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.Ayah;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.model.AyahDetailWithTranslate;
import com.ivanzkyanto.qcv2.model.SearchResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "quran.edition.translate=en.asad")
class AyahServiceTest {

    @Autowired
    private AyahService ayahService;

    @Test
    void get() {
        AyahDetail result = ayahService.get(1, 1);
        assertEquals("In the name of God, The Most Gracious, The Dispenser of Grace:", result.getText());
    }

    @Test
    void getNotFound() {
        assertThrows(AyahNotFoundException.class, () -> ayahService.get(1, 100));
    }

    @Test
    void search() {
        Optional<SearchResult> result = ayahService.search("Abraham");
        assertTrue(result.isPresent());
        assertFalse(result.get().getMatches().isEmpty());
    }

    @Test
    void searchNotFound() {
        Optional<SearchResult> result = ayahService.search("notfound");
        assertTrue(result.isEmpty());
    }

    @Test
    void random() {
        Ayah result = ayahService.random();
        assertNotNull(result);
    }

    @Test
    void randomSpecificSurah() {
        Ayah result = ayahService.random(1);
        assertNotNull(result);
    }

    @Test
    void randomSpecificSurahNotFound() {
        assertThrows(SurahNotFoundException.class, () -> ayahService.random(1000));
    }

    @Test
    void getWithTranslate() {
        AyahDetailWithTranslate ayah = ayahService.getWithTranslate(2, 255);
        assertNotNull(ayah);
        assertNotNull(ayah.getTranslate());
    }

    @Test
    void getWithTranslateSpecified() {
        AyahDetailWithTranslate ayah = ayahService.getWithTranslate(2, 255, "id.muntakhab");
        assertNotNull(ayah);
        assertNotNull(ayah.getTranslate());
    }

    @Test
    void getWithTranslateNotFound() {
        assertThrows(AyahNotFoundException.class, () -> ayahService.getWithTranslate(2, 1000));
    }

    @Test
    @Disabled
    void randomWithTranslate() {
//        AyahDetailWithTranslate ayah = ayahService.randomWithTranslate();
//        assertNotNull(ayah);
//        assertNotNull(ayah.getTranslate());
    }

    @Test
    @Disabled
    void randomWithTranslateSpecified() {
//        AyahDetailWithTranslate ayah = ayahService.randomWithTranslate("id.muntakhab");
//        assertNotNull(ayah);
//        assertNotNull(ayah.getTranslate());
    }

    @Test
    @Disabled
    void randomWithTranslateSpecificSurah() {
    }

    @Test
    @Disabled
    void randomWithTranslateSpecificSurahSpecifiedEdition() {

    }
}