package com.ivanzkyanto.qcv2.configuration;

import com.freya02.botcommands.api.CommandsBuilder;
import com.ivanzkyanto.qcv2.configuration.properties.DiscordConfigurationProperties;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DiscordConfigurer {

    private final DiscordConfigurationProperties configuration;

    private final ExtensionRegister extensionRegister;

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda = JDABuilder.createDefault(configuration.botToken())
                .setActivity(Activity.listening("Ummat"))
                .build();

        jda.awaitReady();
        CommandsBuilder.newBuilder()
                .extensionsBuilder(extensionRegister)
                .applicationCommandBuilder(builder -> builder
                        .addLocalizations("lang", List.of(
                                DiscordLocale.ENGLISH_UK,
                                DiscordLocale.ENGLISH_US,
                                DiscordLocale.INDONESIAN,
                                DiscordLocale.JAPANESE,
                                DiscordLocale.CHINESE_CHINA,
                                DiscordLocale.GERMAN
                        )))
                .build(jda, "com.ivanzkyanto.qcv2.command");

        return jda;
    }
}
