package com.stackpan.qurancord.service;

import com.stackpan.qurancord.entity.Ayah;
import com.stackpan.qurancord.entity.Surah;
import com.stackpan.qurancord.exception.AyahNotFoundException;
import com.stackpan.qurancord.exception.SurahNotFoundException;
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
    void testGetRandomAyahWithSurahNameNotFound() {
        Assertions.assertThrows(SurahNotFoundException.class, () -> discordQuranService.getRandomAyah("Not Found"));
    }

    @Test
    void testGetRandomAyahWithSurahNumber() {
        var result = discordQuranService.getRandomAyah(36);
        var surah = result.get("surah");
        var ayah = result.get("ayah");

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Map.class, result);
        Assertions.assertNotNull(surah);
        Assertions.assertInstanceOf(Surah.class, surah);
        Assertions.assertEquals("Yasin", ((Surah) surah).latinName());
        Assertions.assertNotNull(ayah);
        Assertions.assertInstanceOf(Ayah.class, ayah);
    }

    @Test
    void testGetRandomAyahWithSurahNumberNotFound() {
        Assertions.assertThrows(SurahNotFoundException.class, () -> discordQuranService.getRandomAyah(150));

    }

    @Test
    void testSearchSurahWithSurahName() {
        var result = discordQuranService.searchSurah("Yasin");

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Surah.class, result);
        Assertions.assertEquals(36, result.number());
    }

    @Test
    void testSearchSurahWithSurahNameNotFound() {
        Assertions.assertThrows(SurahNotFoundException.class, () -> discordQuranService.searchSurah("Not Found"));
    }

    @Test
    void testSearchSurahWithSurahNumber() {
        var result = discordQuranService.searchSurah(36);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Surah.class, result);
        Assertions.assertEquals("Yasin", result.latinName());
    }

    @Test
    void testSearchSurahWithSurahNumberNotFound() {
        Assertions.assertThrows(SurahNotFoundException.class, () -> discordQuranService.searchSurah(150));
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
    void testSearchAyahWithSurahNameNotFound() {
        Assertions.assertThrows(SurahNotFoundException.class, () -> discordQuranService.searchAyah("Not Found", 10));
        Assertions.assertThrows(AyahNotFoundException.class, () -> discordQuranService.searchAyah("Yasin", 999));
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

    @Test
    void testSearchAyahWithSurahNumberNotFound() {
        Assertions.assertThrows(SurahNotFoundException.class, () -> discordQuranService.searchAyah(999, 10));
        Assertions.assertThrows(AyahNotFoundException.class, () -> discordQuranService.searchAyah(36, 999));

    }
}
