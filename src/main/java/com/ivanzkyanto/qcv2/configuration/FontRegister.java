package com.ivanzkyanto.qcv2.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class FontRegister {

    public static void register() throws IOException, FontFormatException {
        var graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

        var katibehRegularFont = Font.createFont(Font.TRUETYPE_FONT, ResourceUtils.getFile("classpath:fonts/Katibeh-Regular.ttf"));
        graphicsEnvironment.registerFont(katibehRegularFont);
        log.info(String.format("Font registered: %s", katibehRegularFont.getFontName()));
    }

}
