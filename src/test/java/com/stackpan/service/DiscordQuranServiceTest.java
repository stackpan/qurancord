package com.stackpan.service;

import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.Map;

public class DiscordQuranServiceTest {

    private DiscordQuranService discordQuranService;

    @BeforeEach
    void setUp() {
        discordQuranService = new DiscordQuranService(
                HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .build());
    }

    @Test
    void testGetRandomSurah() {
        var result = discordQuranService.getRandomSurah();

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Surah.class, result);
    }

    @Test
    void testGetRandomAyah() {
        var result = discordQuranService.getRandomAyah();
        var surah = result.get("surah");
        var ayah = result.get("ayah");

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Map.class, result);
        Assertions.assertNotNull(surah);
        Assertions.assertInstanceOf(Surah.class, surah);
        Assertions.assertNotNull(ayah);
        Assertions.assertInstanceOf(Ayah.class, ayah);
    }

    @Test
    void testGetRandomAyahWithSurahName() {
        var result = discordQuranService.getRandomAyah("Yasin");
        var surah = result.get("surah");
        var ayah = result.get("ayah");

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Map.class, result);
        Assertions.assertNotNull(surah);
        Assertions.assertInstanceOf(Surah.class, surah);
        Assertions.assertEquals(36, ((Surah) surah).number());
        Assertions.assertNotNull(ayah);
        Assertions.assertInstanceOf(Ayah.class, ayah);
    }

    @Test
    void testGetRandomAyahWithSurahNumber() {
        var result = discordQuranService.getRandomAyah(36);
        var surah = result.get("surah");
        var ayah = result.get("ayah");

        System.out.println(ayah);
        System.out.println(ayah.getClass().getName());

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Map.class, result);
        Assertions.assertNotNull(surah);
        Assertions.assertInstanceOf(Surah.class, surah);
        Assertions.assertEquals("Yasin", ((Surah) surah).latinName());
        Assertions.assertNotNull(ayah);
        Assertions.assertInstanceOf(Ayah.class, ayah);
    }

    @Test
    void testSearchSurahWithSurahName() {
        var result = discordQuranService.searchSurah("Yasin");

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Surah.class, result);
        Assertions.assertEquals(36, result.number());
    }

    @Test
    void testSearchSurahWithSurahNumber() {
        var result = discordQuranService.searchSurah(36);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Surah.class, result);
        Assertions.assertEquals("Yasin", result.latinName());
    }

    @Test
    void testSearchAyahWithSurahName() {
        var result = discordQuranService.searchAyah("Yasin", 10);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Map.class, result);

        var surah = result.get("surah");
        var ayah = result.get("ayah");

        Assertions.assertNotNull(surah);
        Assertions.assertInstanceOf(Surah.class, surah);
        Assertions.assertEquals(36, ((Surah) surah).number());
        Assertions.assertNotNull(ayah);
        Assertions.assertInstanceOf(Ayah.class, ayah);
        Assertions.assertEquals(10, ((Ayah) ayah).number());
    }

    @Test
    void testSearchAyahWithSurahNumber() {
        var result = discordQuranService.searchAyah(36, 10);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Map.class, result);

        var surah = result.get("surah");
        var ayah = result.get("ayah");

        Assertions.assertNotNull(surah);
        Assertions.assertInstanceOf(Surah.class, surah);
        Assertions.assertEquals("Yasin", ((Surah) surah).latinName());
        Assertions.assertNotNull(ayah);
        Assertions.assertInstanceOf(Ayah.class, ayah);
        Assertions.assertEquals(10, ((Ayah) ayah).number());
    }
}
