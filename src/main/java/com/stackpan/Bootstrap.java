package com.stackpan;

import com.stackpan.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bootstrap {

    public static void boot() {
        try {
            final Path RESOURCE_PATH = Paths.get(App.RESOURCE_PATH);

            // clear resources if exist
            if (Files.exists(RESOURCE_PATH)) FileUtil.clearResources();

            // create resource directory
            Files.createDirectories(RESOURCE_PATH);
        } catch (NoSuchFileException ignored) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
