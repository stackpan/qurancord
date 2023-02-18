package com.stackpan.qurancord.util.test;

import com.stackpan.qurancord.App;
import com.stackpan.qurancord.Bootstrap;
import com.stackpan.qurancord.entity.Ayah;
import com.stackpan.qurancord.entity.Surah;
import com.stackpan.qurancord.service.DiscordQuranService;
import com.stackpan.qurancord.util.FileUtil;
import com.stackpan.qurancord.util.ImageGenerator;
import org.junit.jupiter.api.*;

import java.io.File;
import java.net.http.HttpClient;
import java.util.Objects;

public class ImageGeneratorTest {

    @BeforeAll
    static void beforeAll() {
        Bootstrap.boot();
    }

    @AfterAll
    static void afterAll() {
        FileUtil.clearCacheResources();
    }

    @Test
    void testGenerateAyah() {

        DiscordQuranService discordQuranService = new DiscordQuranService(
                HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .build());

        var result = discordQuranService.getRandomAyah();
        var surah = (Surah) result.get("surah");
        var ayah = (Ayah) result.get("ayah");

        ImageGenerator.generateAyah(surah, ayah);

        Assertions.assertNotEquals(0, Objects.requireNonNull(new File(App.CACHE_RESOURCE_PATH).listFiles()).length);
    }
}
