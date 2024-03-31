package io.github.stackpan.qurancord;

import io.github.stackpan.qurancord.configuration.FontRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;

@SpringBootApplication
@Slf4j
@ConfigurationPropertiesScan("io.github.stackpan.qurancord.configuration.properties")
public class Main {

    public static void main(String[] args) throws IOException, FontFormatException {
        SpringApplication.run(Main.class, args);
        FontRegister.register();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://api.alquran.cloud")
                .build();
    }
}
