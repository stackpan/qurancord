package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SurahServiceTest {

    @Autowired
    private SurahService surahService;
    
    @Test
    void get() {
        SurahDetail result = surahService.get(2);
        Assertions.assertEquals("Al-Baqara", result.getEnglishName());
    }

    @Test
    void search() {
        Surah result = surahService.search("Al-Fatiha");
        Assertions.assertEquals(1, result.getNumber());
    }

    @Test
    void random() {
        SurahDetail result = surahService.random();
        Assertions.assertNotNull(result);
    }
}