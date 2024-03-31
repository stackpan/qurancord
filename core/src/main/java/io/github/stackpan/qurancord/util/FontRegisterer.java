package io.github.stackpan.qurancord.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;
import java.io.IOException;

@Slf4j
public class FontRegisterer {

    public static void register() throws IOException, FontFormatException {
        var graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

        var katibehRegularFont = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/Katibeh-Regular.ttf").getInputStream());
        graphicsEnvironment.registerFont(katibehRegularFont);
        log.info(String.format("Font registered: %s", katibehRegularFont.getFontName()));
    }

}
