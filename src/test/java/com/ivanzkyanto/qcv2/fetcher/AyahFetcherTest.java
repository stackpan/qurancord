package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.exception.AyahNotFoundException;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.model.AyahReference;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AyahFetcherTest {

    @Autowired
    private AyahFetcher ayahFetcher;

    private final Logger log = LoggerFactory.getLogger(AyahFetcherTest.class);

    @Test
    void get() {
        ApiResponse<AyahDetail> response = ayahFetcher.get(new AyahReference(114, 1));

        assertEquals(200, response.getCode());
        assertEquals("OK", response.getStatus());
        assertNotNull(response.getData());
        assertNotNull(response.getData().getSurah());
        assertNotNull(response.getData().getEdition());

        log.info(response.getData().toString());
    }

    @Test
    void getMultiEditions() {
        var response = ayahFetcher.get(new AyahReference(114, 1), "quran-simple", "id.indonesian");

        assertEquals(200, response.getCode());
        assertEquals("OK", response.getStatus());
        assertNotNull(response.getData());
        assertTrue(
                Arrays.stream(response.getData()).anyMatch(
                        ayahDetail -> ayahDetail.getEdition().getIdentifier().equals("quran-simple")
                )
        );
        assertTrue(
                Arrays.stream(response.getData()).anyMatch(
                        ayahDetail -> ayahDetail.getEdition().getIdentifier().equals("id.indonesian")
                )
        );
    }

    @Test
    void getNotFound() {
        assertThrows(AyahNotFoundException.class, () -> ayahFetcher.get(new AyahReference(1, 200)));
    }
}