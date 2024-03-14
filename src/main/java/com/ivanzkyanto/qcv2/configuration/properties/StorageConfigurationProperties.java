package com.ivanzkyanto.qcv2.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public record StorageConfigurationProperties(String path) {
    public StorageConfigurationProperties(String path) {
        this.path = path != null ? path : "storage";
    }
}
