package com.stackpan.util.test;

import com.stackpan.App;
import com.stackpan.Bootstrap;
import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
import com.stackpan.service.DiscordQuranService;
import com.stackpan.util.ImageGenerator;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;

public class ImageGeneratorTest {

    @BeforeAll
    static void beforeAll() {
        Bootstrap.boot();
    }

    @AfterAll
    static void afterAll() {
        try (var pathStream = Files.walk(Paths.get(App.RESOURCE_PATH))) {
            pathStream.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
