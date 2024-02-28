package com.ivanzkyanto.qcv2;

import com.ivanzkyanto.qcv2.discord.DiscordEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
@ConfigurationPropertiesScan("com.ivanzkyanto.qcv2.configuration")
public class Main implements CommandLineRunner {

    private final DiscordEngine botEngine;

    public Main(DiscordEngine botEngine) {
        this.botEngine = botEngine;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public void run(String... args) {
        this.botEngine.start();
        this.botEngine.buildCommands();
    }
}
