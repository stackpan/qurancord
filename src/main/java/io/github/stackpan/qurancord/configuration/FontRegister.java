package io.github.stackpan.qurancord.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class FontRegister {

    public static void register() throws IOException, FontFormatException {
        var graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

        var katibehRegularFont = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/Katibeh-Regular.ttf").getInputStream());
        graphicsEnvironment.registerFont(katibehRegularFont);
        log.info(String.format("Font registered: %s", katibehRegularFont.getFontName()));
    }

}
