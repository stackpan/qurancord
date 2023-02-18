package com.stackpan.qurancord.core.util;

import com.stackpan.qurancord.core.App;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileUtil {
    public static void clearCacheResources() {
        try (var pathStream = Files.walk(Paths.get(App.CACHE_RESOURCE_PATH))) {
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
}
