package com.stackpan.util;

import com.stackpan.App;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileUtil {
    public static void clearResources() {
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
}
