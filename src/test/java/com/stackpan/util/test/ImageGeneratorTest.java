package com.stackpan.util.test;

import com.stackpan.entity.Ayah;
import com.stackpan.service.DiscordQuranService;
import com.stackpan.util.ImageGenerator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

public class ImageGeneratorTest {

    @Disabled("Disabled until the feature is completed")
    @Test
    void testSurahToImage() {
        DiscordQuranService discordQuranService = new DiscordQuranService(
                HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .build());

        var result = discordQuranService.searchAyah(2, 17);
        var ayah = (Ayah) result.get("ayah");

        ImageGenerator.ayahToImage(ayah);
    }
}
