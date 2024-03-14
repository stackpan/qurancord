package com.ivanzkyanto.qcv2.service;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    String writeImage(RenderedImage image, String filename) throws IOException;

    Path getFileAsPath(String path);

    void clear();

}
