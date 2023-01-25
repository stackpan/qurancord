package com.stackpan.util.test;

import com.google.gson.JsonElement;
import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
import com.stackpan.util.Deserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class DeserializerTest {

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    @Test
    void testParseGetSurah() {
        String json = httpClient.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create("https://equran.id/api/surat"))
                        .timeout(Duration.ofMinutes(1))
                        .GET()
                        .build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        var result = Deserializer.parseGetSurah(json);

        Assertions.assertInstanceOf(List.class, result);
        result.forEach(jsonElement -> Assertions.assertInstanceOf(JsonElement.class, jsonElement));
    }

    @Test
    void testParseElementSurah() {
        String json = httpClient.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create("https://equran.id/api/surat"))
                        .timeout(Duration.ofMinutes(1))
                        .GET()
                        .build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        var elementList = Deserializer.parseGetSurah(json);
        elementList.forEach(jsonElement -> Assertions.assertInstanceOf(Surah.class, Deserializer.parseElementSurah(jsonElement)));
    }

    @Test
    void testParseGetAllAyahBySurah() {
        String json = httpClient.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create("https://equran.id/api/surat/1"))
                        .timeout(Duration.ofMinutes(1))
                        .GET()
                        .build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        var result = Deserializer.parseGetAllAyahBySurah(json);

        Assertions.assertInstanceOf(List.class, result);
        result.forEach(jsonElement -> Assertions.assertInstanceOf(JsonElement.class, jsonElement));
    }

    @Test
    void testParseElementAyah() {
        String json = httpClient.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create("https://equran.id/api/surat/1"))
                        .timeout(Duration.ofMinutes(1))
                        .GET()
                        .build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        var elementList = Deserializer.parseGetAllAyahBySurah(json);
        elementList.forEach(jsonElement -> Assertions.assertInstanceOf(Ayah.class, Deserializer.parseElementAyah(jsonElement)));
    }
}
