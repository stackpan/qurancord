package com.stackpan.qurancord.core.util;

import com.stackpan.qurancord.core.App;
import com.stackpan.qurancord.core.entity.Ayah;
import com.stackpan.qurancord.core.entity.Surah;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
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

public class ImageGenerator {

    private final static Map<RenderingHints.Key, Object> hints = new HashMap<>();

    private static final int IMAGE_WIDTH = 1280;
    private static final int MARGIN = 18;
    private static final int FONT_SIZE = 60;
    private static final int LATIN_FONT_SIZE = (int) (FONT_SIZE * 0.5);
    private static final int SECTION_GAP = (int) (FONT_SIZE * 0.8);
    private static final float WRAPPING_WIDTH = (float) IMAGE_WIDTH - MARGIN * 2;

    private static final Font arabicFont;
    private static final Font latinFont;

    static {
        hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        hints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        try {
            var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, App.getResourceStream("Lateef-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        arabicFont = new Font("Lateef", Font.PLAIN, FONT_SIZE);
        latinFont = new Font("Times New Roman", Font.PLAIN, LATIN_FONT_SIZE);
    }

    public static void generateAyah(Surah surah, Ayah ayah) {
        String arabicText = ayah.arabicText();
        String translateText = ayah.bahasaTranslate();
        String surahName = "[" + surah.latinName() + ":" + ayah.number() + "]";

        AttributedString arAs = new AttributedString(arabicText);
        arAs.addAttribute(TextAttribute.FONT, arabicFont);
        arAs.addAttribute(TextAttribute.RUN_DIRECTION, TextAttribute.RUN_DIRECTION_RTL);

        AttributedString tlAs = new AttributedString(translateText);
        tlAs.addAttribute(TextAttribute.FONT, latinFont);

        AttributedCharacterIterator arIterator = arAs.getIterator();
        AttributedCharacterIterator tlIterator = tlAs.getIterator();

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        g.setFont(arabicFont);
        g.setRenderingHints(hints);

        FontRenderContext frc = g.getFontRenderContext();

        LineBreakMeasurer arMeasurer = new LineBreakMeasurer(arIterator, frc);
        arMeasurer.setPosition(arIterator.getBeginIndex());

        LineBreakMeasurer tlMeasurer = new LineBreakMeasurer(tlIterator, frc);
        tlMeasurer.setPosition(tlIterator.getBeginIndex());

        // measuring image height
        int imageHeight = MARGIN * 2 + SECTION_GAP * 2;
        while (arMeasurer.getPosition() < arIterator.getEndIndex()) {
            TextLayout layout = arMeasurer.nextLayout(WRAPPING_WIDTH);
            imageHeight += layout.getAscent() + layout.getDescent() + layout.getLeading();
        }

        g.setFont(latinFont);

        while (tlMeasurer.getPosition() < tlIterator.getEndIndex()) {
            TextLayout layout = tlMeasurer.nextLayout(WRAPPING_WIDTH);
            imageHeight += layout.getAscent() + layout.getDescent() + layout.getLeading();
        }

        g.dispose();

        // render the actual image
        image = new BufferedImage(IMAGE_WIDTH, imageHeight, BufferedImage.TYPE_INT_ARGB);

        g = image.createGraphics();
        g.setFont(arabicFont);
        g.setRenderingHints(hints);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());

        // render arabic text
        arMeasurer.setPosition(arIterator.getBeginIndex());

        g.setColor(Color.BLACK);
        float dy = MARGIN;
        while (arMeasurer.getPosition() < arIterator.getEndIndex()) {
            TextLayout layout = arMeasurer.nextLayout(WRAPPING_WIDTH);

            dy += layout.getAscent();
            float dx = layout.isLeftToRight() ? MARGIN : (WRAPPING_WIDTH - layout.getAdvance()) + MARGIN;

            layout.draw(g, dx, dy);
            dy += layout.getDescent() + layout.getLeading();
        }

        tlMeasurer.setPosition(tlIterator.getBeginIndex());

        g.setFont(latinFont);

        dy += SECTION_GAP;
        while (tlMeasurer.getPosition() < tlIterator.getEndIndex()) {
            TextLayout layout = tlMeasurer.nextLayout(WRAPPING_WIDTH);

            dy += layout.getAscent();
            float dx = layout.isLeftToRight() ? MARGIN : (WRAPPING_WIDTH - layout.getAdvance()) + MARGIN;

            layout.draw(g, dx, dy);
            dy += layout.getDescent() + layout.getLeading();
        }

        TextLayout surahNameLayout = new TextLayout(surahName, latinFont, frc);
        surahNameLayout.draw(g, (float) MARGIN, dy + SECTION_GAP);

        g.dispose();

        String path = App.CACHE_RESOURCE_PATH + "/" + surah.number() + "_" + ayah.number() + ".png";
        try {
            ImageIO.write(image, "png", new File(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
