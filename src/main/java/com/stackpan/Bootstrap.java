package com.stackpan;

import com.stackpan.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bootstrap {

    public static void checkEnv() {
        if (App.BOT_TOKEN == null) {
            throw new IllegalArgumentException("Bot token cannot be null. Set BOT_TOKEN in the environment variable");
        }
    }

    public static void boot() {
        final Path RESOURCE_PATH = Paths.get(App.CACHE_RESOURCE_PATH);

        // clear cache if exist
        if (Files.exists(RESOURCE_PATH)) FileUtil.clearCacheResources();

        // create resource directory
        try {
            Files.createDirectories(RESOURCE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
