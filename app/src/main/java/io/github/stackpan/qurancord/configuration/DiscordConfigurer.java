package io.github.stackpan.qurancord.configuration;

import com.freya02.botcommands.api.CommandsBuilder;
import io.github.stackpan.qurancord.properties.DiscordConfigurationProperties;
import io.github.stackpan.qurancord.util.ExtensionRegisterer;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class DiscordConfigurer {

    private final DiscordConfigurationProperties configuration;

    private final ExtensionRegisterer extensionRegisterer;

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda = JDABuilder.createDefault(configuration.botToken())
                .setActivity(Activity.listening("Ummat"))
                .build();

        jda.awaitReady();
        CommandsBuilder.newBuilder()
                .addOwners(configuration.ownerId())
                .extensionsBuilder(extensionRegisterer)
                .applicationCommandBuilder(builder -> builder
                        .addLocalizations("lang", List.of(
                                DiscordLocale.ENGLISH_UK,
                                DiscordLocale.ENGLISH_US,
                                DiscordLocale.INDONESIAN,
                                DiscordLocale.JAPANESE,
                                DiscordLocale.CHINESE_CHINA,
                                DiscordLocale.GERMAN
                        )))
                .setUncaughtExceptionHandler((context, event, throwable) -> {
                    if (Objects.requireNonNull(event) instanceof GenericCommandInteractionEvent e) {
                        e.getHook().sendMessage("There is something wrong happened to me. Sorry I cannot fulfill your request at this time.").queue();
                        return;
                    }
                    throw new RuntimeException(throwable);
                })
                .build(jda, "io.github.stackpan.qurancord.command");

        return jda;
    }
}
