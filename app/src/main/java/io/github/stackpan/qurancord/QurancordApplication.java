package io.github.stackpan.qurancord;

import io.github.stackpan.qurancord.util.FontRegisterer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.awt.*;
import java.io.IOException;

@SpringBootApplication
@Slf4j
@ConfigurationPropertiesScan("io.github.stackpan.qurancord.properties")
public class QurancordApplication {

    public static void main(String[] args) throws IOException, FontFormatException {
        SpringApplication.run(QurancordApplication.class, args);
        FontRegisterer.register();
    }

}
