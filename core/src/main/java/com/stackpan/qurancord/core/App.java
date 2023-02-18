package com.stackpan.qurancord.core;

import java.io.InputStream;
import java.util.Optional;

public class App {

    public static final String BOT_TOKEN = System.getenv("BOT_TOKEN");

    public static final String CACHE_RESOURCE_PATH = System.getProperty("user.dir") + "/.res";

    public InputStream getResourceStream(String fileName) {
        return Optional.ofNullable(getClass().getClassLoader().getResourceAsStream(fileName))
                .orElseThrow(() -> new IllegalArgumentException("File Not Found"));
    }

}
