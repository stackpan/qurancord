package com.ivanzkyanto.qcv2.configuration;

import com.freya02.botcommands.api.CommandsBuilder;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DiscordConfigurer {

    private final DiscordConfigurationProperties configuration;

    private final ExtensionRegister extensionRegister;

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda =  JDABuilder.createDefault(configuration.botToken()).build();

        jda.awaitReady();
        CommandsBuilder.newBuilder()
                .extensionsBuilder(extensionRegister)
                .build(jda, "com.ivanzkyanto.qcv2.command");

        return jda;
    }
}
