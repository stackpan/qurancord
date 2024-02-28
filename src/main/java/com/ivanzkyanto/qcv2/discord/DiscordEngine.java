package com.ivanzkyanto.qcv2.discord;

import com.freya02.botcommands.api.CommandsBuilder;
import com.ivanzkyanto.qcv2.configuration.DiscordConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscordEngine {

    private final DiscordConfigurationProperties configuration;

    private final ExtensionRegister extensionRegister;

    private JDA jda;

    public void start() {
        this.jda = JDABuilder.createDefault(configuration.botToken()).build();
    }

    public void buildCommands() {
        try {
            this.jda.awaitReady();
            CommandsBuilder.newBuilder()
                    .extensionsBuilder(extensionRegister)
                    .build(this.jda, "com.ivanzkyanto.qcv2.command");
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
    }

}
