package io.github.stackpan.qurancord.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quran")
public record QuranConfigurationProperties(QuranEditionConfigurationProperties edition) {
}
