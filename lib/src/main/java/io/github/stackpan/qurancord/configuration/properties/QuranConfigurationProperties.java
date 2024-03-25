package io.github.stackpan.qurancord.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quran")
public record QuranConfigurationProperties(QuranEditionConfigurationProperties edition) {
}
