package com.ivanzkyanto.qcv2.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String identity;

    public ResourceNotFoundException(String identity) {
        this.identity = identity;
    }
}
