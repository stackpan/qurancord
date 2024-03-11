package com.ivanzkyanto.qcv2.util;

import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.model.AyahReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@SpringBootTest
public class AyahImageGeneratorTest {

    @Autowired
    private AyahFetcher ayahFetcher;

    @Test
    void generate() throws IOException {
        var arabic = ayahFetcher.get(new AyahReference(1, 1), "quran-uthmani");
        var translate = ayahFetcher.get(new AyahReference(1, 1), "en.asad");

        var image = AyahImageRendererKt.render(
                arabic.getData().getText(),
                translate.getData().getText(),
                arabic.getData().getSurah().getEnglishName(),
                arabic.getData().getNumberInSurah()
        );

        var fileName = "surah:%d_ayah:%d_edition:%s_translate:%s".formatted(1, 1, "quran-uthmani", "en.asad");
        ImageIO.write(image, "png", new File("storage/" + fileName + ".png"));
    }
}
