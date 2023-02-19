package com.stackpan.qurancord.core;

import com.stackpan.qurancord.core.service.QuranService;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Optional;

public class App {

    public static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    public static final String CACHE_RESOURCE_PATH = System.getProperty("user.dir") + "/.res";

    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofMinutes(1))
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    private QuranService quranService;

    public void setQuranService(QuranService quranService) {
        this.quranService = quranService;
    }

    public QuranService getQuranService() {
        return quranService;
    }

    public static InputStream getResourceStream(String fileName) {
        return Optional.ofNullable(App.class.getClassLoader().getResourceAsStream(fileName))
                .orElseThrow(() -> new IllegalArgumentException("File Not Found"));
    }
}
