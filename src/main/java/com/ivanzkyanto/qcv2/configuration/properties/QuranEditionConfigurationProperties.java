package com.ivanzkyanto.qcv2.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quran.edition")
public record QuranEditionConfigurationProperties(String verse, String translate) {

    public QuranEditionConfigurationProperties(String verse, String translate) {
        this.verse = verse != null ? verse : "quran-simple-enhanced";
        this.translate = translate != null ? translate : "en.asad";
    }
}
