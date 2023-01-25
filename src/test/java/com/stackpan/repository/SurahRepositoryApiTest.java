package com.stackpan.repository;

import com.stackpan.entity.Surah;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

public class SurahRepositoryApiTest {

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    @Test
    void testGetAll() {
        SurahRepository surahRepository = new SurahRepositoryApi(httpClient);

        var result = surahRepository.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(List.class, result);

        result.forEach(surah -> {
            Assertions.assertNotNull(surah);
            Assertions.assertInstanceOf(Surah.class, surah);
        });
    }

    @Test
    void testGet() {
        SurahRepository surahRepository = new SurahRepositoryApi(httpClient);

        var result = surahRepository.get(5);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Surah.class, result);
    }
}
