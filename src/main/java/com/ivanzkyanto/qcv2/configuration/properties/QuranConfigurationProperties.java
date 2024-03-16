package com.ivanzkyanto.qcv2.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quran")
public record QuranConfigurationProperties(QuranEditionConfigurationProperties edition) {
}
