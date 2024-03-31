package io.github.stackpan.qurancord.fetcher;

import io.github.stackpan.qurancord.exception.SurahNotFoundException;
import io.github.stackpan.qurancord.model.ApiResponse;
import io.github.stackpan.qurancord.model.Surah;
import io.github.stackpan.qurancord.model.SurahDetail;
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