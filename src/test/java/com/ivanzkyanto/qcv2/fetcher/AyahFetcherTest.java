package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.model.AyahReference;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}