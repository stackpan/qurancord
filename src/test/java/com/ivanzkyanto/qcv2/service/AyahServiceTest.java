package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.Ayah;
import com.ivanzkyanto.qcv2.model.SearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AyahServiceTest {

    @Autowired
    private AyahService ayahService;

    @Test
    void get() {
        Ayah result = ayahService.get(1, 1);
        assertEquals("In the name of God, The Most Gracious, The Dispenser of Grace:", result.getText());
    }

    @Test
    void search() {
        SearchResult result = ayahService.search("Abraham");
        assertFalse(result.getMatches().isEmpty());
    }

    @Test
    void random() {
        Ayah result = ayahService.random();
        assertNotNull(result);
    }

    @Test
    void randomSpecificSurah() throws SurahNotFoundException {
        Ayah result = ayahService.random(1);
        assertNotNull(result);
    }
}