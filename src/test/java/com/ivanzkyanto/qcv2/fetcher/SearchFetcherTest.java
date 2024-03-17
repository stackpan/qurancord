package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.SearchEditionReference;
import com.ivanzkyanto.qcv2.model.SearchResult;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchFetcherTest {

    @Autowired
    private SearchFetcher searchFetcher;

    private final Logger log = LoggerFactory.getLogger(SurahFetcherTest.class);

    @Test
    void search() {
        Optional<ApiResponse<SearchResult>> response = searchFetcher.search("Abraham", 37, new SearchEditionReference("en", "pickthall"));

        assertTrue(response.isPresent());
        assertEquals(200, response.get().getCode());
        assertEquals("OK", response.get().getStatus());
        assertNotNull(response.get().getData());
        assertNotNull(response.get().getData().getCount());
        assertNotNull(response.get().getData().getMatches());

        log.info(response.get().getData().toString());
    }

    @Test
    void searchNoContent() {
        Optional<ApiResponse<SearchResult>> response = searchFetcher.search("notfound", 37, new SearchEditionReference("en", "pickthall"));

        assertTrue(response.isEmpty());
    }
}