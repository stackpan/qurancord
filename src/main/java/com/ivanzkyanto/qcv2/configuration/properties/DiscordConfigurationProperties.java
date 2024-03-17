package com.ivanzkyanto.qcv2.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord")
public record DiscordConfigurationProperties(String botToken) {
}
