package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SurahFetcherTest {

    @Autowired
    private SurahFetcher surahFetcher;

    private final Logger log = LoggerFactory.getLogger(SurahFetcherTest.class);

    @Test
    void getAll() {
        ApiResponse<List<Surah>> response = surahFetcher.getAll();

        assertEquals(200, response.getCode());
        assertEquals("OK", response.getStatus());
        assertNotNull(response.getData());

        log.info(response.getData().toString());
    }

    @Test
    void get() throws SurahNotFoundException {
        ApiResponse<SurahDetail> response = surahFetcher.get(7);

        assertEquals(200, response.getCode());
        assertEquals("OK", response.getStatus());
        assertNotNull(response.getData());
        assertNotNull(response.getData().getAyahs());
        assertNotNull(response.getData().getEdition());

        log.info(response.getData().toString());
    }

    @Test
    void getNotFound() {
        assertThrows(SurahNotFoundException.class, () -> surahFetcher.get(1000));
    }
}