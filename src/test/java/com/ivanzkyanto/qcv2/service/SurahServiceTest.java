package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SurahServiceTest {

    @Autowired
    private SurahService surahService;
    
    @Test
    void get() {
        SurahDetail result = surahService.get(2);
        assertEquals("Al-Baqara", result.getEnglishName());
    }

    @Test
    void getNotFound() {
        assertThrows(SurahNotFoundException.class, () -> surahService.get(1000));
    }

    @Test
    void search() {
        Optional<Surah> result = surahService.search("Al-Fatiha");
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getNumber());
    }

    @Test
    void searchNotFound() {
        Optional<Surah> result = surahService.search("surah");
        assertTrue(result.isEmpty());
    }

    @Test
    void random() {
        SurahDetail result = surahService.random();
        assertNotNull(result);
    }
}