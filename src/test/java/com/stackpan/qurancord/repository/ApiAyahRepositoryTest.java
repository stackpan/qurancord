package com.stackpan.qurancord.repository;

import com.stackpan.qurancord.entity.Ayah;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

public class ApiAyahRepositoryTest {

    private AyahRepository ayahRepository;

    @BeforeEach
    void setUp() {
        ayahRepository = new ApiAyahRepository(HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build());
    }

    @Test
    void testGetAllBySurah() {
        var result = ayahRepository.getAllBySurah(1);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(List.class, result);

        result.forEach(ayah -> {
            Assertions.assertNotNull(ayah);
            Assertions.assertInstanceOf(Ayah.class, ayah);
        });
    }

    @Test
    void testGetBySurah() {
        var result = ayahRepository.getBySurah(1, 2);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Ayah.class, result);
    }
}
