package com.ivanzkyanto.qcv2.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.IOException;

@Slf4j
public class FontRegister {

    public static void regist() throws IOException, FontFormatException {
        var graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

        var lateefRegularFont = Font.createFont(Font.TRUETYPE_FONT, ResourceUtils.getFile("classpath:fonts/Lateef-Regular.ttf"));
        graphicsEnvironment.registerFont(lateefRegularFont);
        log.info(String.format("Font registered: %s", lateefRegularFont.getFontName()));
    }

}
