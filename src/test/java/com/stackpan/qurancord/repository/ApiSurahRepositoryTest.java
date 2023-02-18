package com.stackpan.qurancord.repository;

import com.stackpan.qurancord.entity.Surah;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

public class ApiSurahRepositoryTest {

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    @Test
    void testGetAll() {
        SurahRepository surahRepository = new ApiSurahRepository(httpClient);

        var result = surahRepository.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(List.class, result);

        result.forEach(surah -> {
            Assertions.assertNotNull(surah);
            Assertions.assertInstanceOf(Surah.class, surah);
        });
    }

    @Test
    void testGetByNumber() {
        SurahRepository surahRepository = new ApiSurahRepository(httpClient);

        var result = surahRepository.getByNumber(5);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Surah.class, result);
    }

    @Test
    void testGetByLatinName() {
        SurahRepository surahRepository = new ApiSurahRepository(httpClient);

        var result = surahRepository.getByNumber(5);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Surah.class, result);

        Assertions.assertNotNull(surahRepository.getByLatinName("Al-Fatihah"));
        Assertions.assertNotNull(surahRepository.getByLatinName("Alfatihah"));
        Assertions.assertNotNull(surahRepository.getByLatinName("Al fatihah"));
        Assertions.assertNotNull(surahRepository.getByLatinName("ali imran"));
        Assertions.assertEquals(surahRepository.getByNumber(1), surahRepository.getByLatinName("Al-Fatihah"));
        Assertions.assertEquals(surahRepository.getByNumber(1), surahRepository.getByLatinName("Alfatihah"));
        Assertions.assertEquals(surahRepository.getByNumber(1), surahRepository.getByLatinName("Al fatihah"));
        Assertions.assertEquals(surahRepository.getByNumber(3), surahRepository.getByLatinName("ali imran"));
    }
}
