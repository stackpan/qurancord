package io.github.stackpan.qurancord.service.impl;

import io.github.stackpan.qurancord.configuration.properties.StorageConfigurationProperties;
import io.github.stackpan.qurancord.service.StorageService;
import io.github.stackpan.qurancord.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final StorageConfigurationProperties storageConfigurationProperties;

    public StorageServiceImpl(StorageConfigurationProperties storageConfigurationProperties) {
        this.storageConfigurationProperties = storageConfigurationProperties;
        init();
    }

    private void init() {
        var storagePath = Paths.get(storageConfigurationProperties.path());

        if (Files.exists(storagePath)) {
            clear();
        }

        try {
            Files.createDirectories(storagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Storage directory created with folder name \"%s\"".formatted(storagePath.getFileName()));
    }

    @Override
    public void clear() {
        var storagePath = Paths.get(storageConfigurationProperties.path());

        try (var pathStream = Files.walk(storagePath)) {
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
        log.info("Storage cleared");
    }

    @Override
    public String writeImage(RenderedImage image, String filename) throws IOException {
        var extension = StringUtils.getFileExtension(filename);
        var file = new File("%s/%s".formatted(storageConfigurationProperties.path(), filename));

        ImageIO.write(image, extension, file);
        return file.getName();
    }

    @Override
    public Path getFileAsPath(String path) {
        return Paths.get("%s/%s".formatted(storageConfigurationProperties.path(), path));
    }
}
