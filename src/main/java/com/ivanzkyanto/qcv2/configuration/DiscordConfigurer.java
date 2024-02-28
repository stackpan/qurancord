package com.ivanzkyanto.qcv2.configuration;

import com.freya02.botcommands.api.CommandsBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DiscordConfigurer implements CommandLineRunner {

    private final DiscordConfigurationProperties configuration;

    private final ExtensionRegister extensionRegister;

    private JDA jda;

    @Override
    public void run(String... args) {
        this.start();
        this.buildCommands();
    }

    private void start() {
        this.jda = JDABuilder.createDefault(configuration.botToken()).build();
    }

    private void buildCommands() {
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
