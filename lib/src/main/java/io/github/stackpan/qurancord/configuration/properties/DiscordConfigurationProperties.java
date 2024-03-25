package io.github.stackpan.qurancord.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord")
public record DiscordConfigurationProperties(String botToken, Long ownerId) {
}
