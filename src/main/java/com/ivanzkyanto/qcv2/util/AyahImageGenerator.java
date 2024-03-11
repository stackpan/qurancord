package com.ivanzkyanto.qcv2.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@Slf4j
public class AyahImageGenerator {

    public static class Data {
        private String arabicText;
        private String translatedText;
        private String surahName;
        private Integer ayahNumber;

        public Data setArabicText(String arabicText) {
            this.arabicText = arabicText;
            return this;
        }

        public Data setTranslatedText(String translatedText) {
            this.translatedText = translatedText;
            return this;
        }

        public Data setSurahName(String surahName) {
            this.surahName = surahName;
            return this;
        }

        public Data setAyahNumber(Integer ayahNumber) {
            this.ayahNumber = ayahNumber;
            return this;
        }

        public void generate(String filename) {
            AyahImageGenerator.generate(this, filename);
        }
    }

    public static Data init() {
        return new Data();
    }

    private static void generate(Data data, String filename) {
        var image = AyahImageRendererKt.render(data.arabicText, data.translatedText, data.surahName, data.ayahNumber);

        try {
            var file = new File("storage/" + filename + ".png");
            ImageIO.write(image, "png", file);
            log.info("Image generated: %s".formatted(file.getPath()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
