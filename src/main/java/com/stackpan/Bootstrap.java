package com.stackpan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Bootstrap {

    public static void boot() {
        // create resource directory
        try {
            Files.createDirectories(Paths.get(App.RESOURCE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
