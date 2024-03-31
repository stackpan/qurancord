package io.github.stackpan.qurancord.service;

import io.github.stackpan.qurancord.configuration.properties.StorageConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StorageServiceTest {

    @Autowired
    private StorageService storageService;

    @Autowired
    private StorageConfigurationProperties storageConfigurationProperties;

    @Test
    void writeImage() throws IOException {
        var filename = storageService.writeImage(generateImage(), "test.png");
        var file = new File("%s/%s".formatted(storageConfigurationProperties.path(), filename));
        assertTrue(file.exists());
    }

    @Test
    void getFileAsPath() {
        var path = storageService.getFileAsPath("test.png");
        assertNotNull(path);
    }

    @Test
    void clear() {
        storageService.clear();
        var path = Paths.get(storageConfigurationProperties.path());
        assertFalse(Files.exists(path));
    }

    private BufferedImage generateImage() {
        var image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
        var graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 400, 300);
        graphics.dispose();
        return image;
    }
}