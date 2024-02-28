package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class SurahServiceTest {

    @Autowired
    private SurahService surahService;
    
    @Test
    void get() {
        Optional<SurahDetail> result = surahService.get(2);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Al-Baqara", result.get().getEnglishName());
    }

    @Test
    void getNotFound() {
        Optional<SurahDetail> response = surahService.get(1000);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void search() {
        Optional<Surah> result = surahService.search("Al-Fatiha");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1, result.get().getNumber());
    }

    @Test
    void searchNotFound() {
        Optional<Surah> result = surahService.search("surah");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void random() {
        SurahDetail result = surahService.random();
        Assertions.assertNotNull(result);
    }
}