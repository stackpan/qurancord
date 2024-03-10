package com.ivanzkyanto.qcv2.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AyahImageGenerator {

    private static final Map<RenderingHints.Key, Object> RENDERING_HINTS = new HashMap<>();

    private static final int IMAGE_WIDTH = 1280;
    private static final int MARGIN = 18;
    private static final int FONT_SIZE = 60;
    private static final int LATIN_FONT_SIZE = (int) (FONT_SIZE * 0.5);
    private static final int SECTION_GAP = (int) (FONT_SIZE * 0.8);
    private static final float WRAPPING_WIDTH = (float) IMAGE_WIDTH - MARGIN * 2;

    private static final Font ARABIC_FONT = new Font("Lateef", Font.PLAIN, FONT_SIZE);
    private static final Font LATIN_FONT = new Font("Times New Roman", Font.PLAIN, LATIN_FONT_SIZE);

    static {
        initializeRenderingHints();
    }

    private static void initializeRenderingHints() {
        RENDERING_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        RENDERING_HINTS.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        RENDERING_HINTS.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        RENDERING_HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

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
        var arabicTextAttributedString = initializeArabicTextAttributedString(data);
        var translatedTextAttributedString = initializeTranslatedTextAttributedString(data);

        var arabicTextAttributedCharacterIterator = arabicTextAttributedString.getIterator();
        var translatedTextAttributedCharacterIterator = translatedTextAttributedString.getIterator();

        // Measuring image height
        var measurementImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        var measurementImageGraphics = measurementImage.createGraphics();
        measurementImageGraphics.setRenderingHints(RENDERING_HINTS);

        int imageHeight = MARGIN * 2 + SECTION_GAP * 2;

        // Measuring arabic text section height
        measurementImageGraphics.setFont(ARABIC_FONT);
        var arabicTextLineBreakMeasurer = new LineBreakMeasurer(arabicTextAttributedCharacterIterator, measurementImageGraphics.getFontRenderContext());
        arabicTextLineBreakMeasurer.setPosition(arabicTextAttributedCharacterIterator.getBeginIndex());
        imageHeight += measureSectionHeight(arabicTextLineBreakMeasurer, arabicTextAttributedCharacterIterator);

        // Measuring translated text section height
        measurementImageGraphics.setFont(LATIN_FONT);
        var translatedTextLineBreakMeasurer = new LineBreakMeasurer(translatedTextAttributedCharacterIterator, measurementImageGraphics.getFontRenderContext());
        translatedTextLineBreakMeasurer.setPosition(translatedTextAttributedCharacterIterator.getBeginIndex());
        imageHeight += measureSectionHeight(translatedTextLineBreakMeasurer, translatedTextAttributedCharacterIterator);

        measurementImageGraphics.dispose();

        // Drawing the actual image
        var image = new BufferedImage(IMAGE_WIDTH, imageHeight, BufferedImage.TYPE_INT_ARGB);

        var imageGraphics = image.createGraphics();
        imageGraphics.setRenderingHints(RENDERING_HINTS);
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        imageGraphics.setColor(Color.BLACK);

        float dy = MARGIN;

        // Drawing arabic text
        imageGraphics.setFont(ARABIC_FONT);
        arabicTextLineBreakMeasurer.setPosition(arabicTextAttributedCharacterIterator.getBeginIndex());
        dy = drawText(imageGraphics, dy, arabicTextLineBreakMeasurer, arabicTextAttributedCharacterIterator);

        // Drawing translated text
        imageGraphics.setFont(LATIN_FONT);
        translatedTextLineBreakMeasurer.setPosition(translatedTextAttributedCharacterIterator.getBeginIndex());
        dy += SECTION_GAP;
        dy = drawText(imageGraphics, dy, translatedTextLineBreakMeasurer, translatedTextAttributedCharacterIterator);

        // Drawing qs text
        var qsText = "[%s:%s]".formatted(data.surahName, data.ayahNumber);
        var qsLayout = new TextLayout(qsText, LATIN_FONT, imageGraphics.getFontRenderContext());
        qsLayout.draw(imageGraphics, (float) MARGIN, dy + SECTION_GAP);

        imageGraphics.dispose();

        try {
            var file = new File("storage/" + filename + ".png");
            ImageIO.write(image, "png", file);
            log.info("Image generated: %s".formatted(file.getPath()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static AttributedString initializeArabicTextAttributedString(Data data) {
        var attributedString = new AttributedString(data.arabicText);
        attributedString.addAttribute(TextAttribute.FONT, ARABIC_FONT);
        attributedString.addAttribute(TextAttribute.RUN_DIRECTION, TextAttribute.RUN_DIRECTION_RTL);
        return attributedString;
    }

    private static AttributedString initializeTranslatedTextAttributedString(Data data) {
        var attributedString = new AttributedString(data.translatedText);
        attributedString.addAttribute(TextAttribute.FONT, LATIN_FONT);
        return attributedString;
    }

    private static int measureSectionHeight(LineBreakMeasurer textLineBreakMeasurer, AttributedCharacterIterator textAttributedCharacterIterator) {
        int height = 0;

        while (textLineBreakMeasurer.getPosition() < textAttributedCharacterIterator.getEndIndex()) {
            var textLayout = textLineBreakMeasurer.nextLayout(WRAPPING_WIDTH);
            height += (int) (textLayout.getAscent() + textLayout.getDescent() + textLayout.getLeading());
        }

        return height;
    }

    private static float drawText(Graphics2D imageGraphics, float dy, LineBreakMeasurer textLineBreakMeasurer, AttributedCharacterIterator textAttributedCharacterIterator) {
        while (textLineBreakMeasurer.getPosition() < textAttributedCharacterIterator.getEndIndex()) {
            var textLayout = textLineBreakMeasurer.nextLayout(WRAPPING_WIDTH);

            dy += textLayout.getAscent();
            float dx = textLayout.isLeftToRight() ? MARGIN : (WRAPPING_WIDTH - textLayout.getAdvance()) + MARGIN;

            textLayout.draw(imageGraphics, dx, dy);
            dy += textLayout.getDescent() + textLayout.getLeading();
        }

        return dy;
    }
}
