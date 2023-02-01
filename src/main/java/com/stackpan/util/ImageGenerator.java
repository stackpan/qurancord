package com.stackpan.util;

import com.stackpan.entity.Ayah;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageGenerator {

    private final static Map<?, ?> renderingHintsMap = new HashMap<>(Map.of(
            RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY,
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
            RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY,
            RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE,
            RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON,
            RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR,
            RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
            RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE
    ));

    static {
        try {
            var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("DroidNaskh-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static void ayahToImage(Ayah ayah) {

        String text = ayah.arabicText();

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        Font font = new Font("Droid Arabic Naskh", Font.PLAIN, 48);
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int width = fontMetrics.stringWidth(text) + 12;
        int height = fontMetrics.getHeight() + 16;
        g2d.dispose();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setRenderingHints(renderingHintsMap);
        g2d.setFont(font);
        fontMetrics = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLACK);

        final int x = image.getWidth() - width;
        g2d.drawString(text, x, fontMetrics.getAscent() + 8);
        g2d.dispose();
        try {
            ImageIO.write(image, "png", new File("Ayah.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
