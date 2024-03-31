package com.ivanzkyanto.qcv2.util;

import com.ivanzkyanto.qcv2.configuration.FontRegister;
import com.ivanzkyanto.qcv2.fetcher.SurahFetcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@SpringBootTest
public class SurahImageGeneratorTest {

    @Autowired
    private SurahFetcher surahFetcher;

    @BeforeAll
    static void beforeAll() throws IOException, FontFormatException {
        FontRegister.register();
    }

    @Test
    void generate() throws IOException {
        var surah = surahFetcher.get(1);

        var image = SurahImageRendererKt.render(surah.getData().getName());
        ImageIO.write(image, "png", new File("storage/surah:1.png"));
    }
}
