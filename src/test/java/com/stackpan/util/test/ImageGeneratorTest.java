package com.stackpan.util.test;

import com.stackpan.App;
import com.stackpan.Bootstrap;
import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
import com.stackpan.service.DiscordQuranService;
import com.stackpan.util.FileUtil;
import com.stackpan.util.ImageGenerator;
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
        FileUtil.clearResources();
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

        Assertions.assertNotEquals(0, Objects.requireNonNull(new File(App.RESOURCE_PATH).listFiles()).length);
    }
}
