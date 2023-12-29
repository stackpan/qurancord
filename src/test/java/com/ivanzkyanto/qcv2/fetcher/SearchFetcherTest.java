package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.SearchEditionReference;
import com.ivanzkyanto.qcv2.model.SearchResult;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchFetcherTest {

    @Autowired
    private SearchFetcher searchFetcher;

    private final Logger log = LoggerFactory.getLogger(SurahFetcherTest.class);

    @Test
    void search() {
        ApiResponse<SearchResult> response = searchFetcher.search("Abraham", 37, new SearchEditionReference("en", "pickthall"));

        assertEquals(200, response.getCode());
        assertEquals("OK", response.getStatus());
        assertNotNull(response.getData());
        assertNotNull(response.getData().getCount());
        assertNotNull(response.getData().getMatches());

        log.info(response.getData().toString());
    }
}