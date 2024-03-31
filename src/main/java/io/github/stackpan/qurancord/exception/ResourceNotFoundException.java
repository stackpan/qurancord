package io.github.stackpan.qurancord.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String identity;

    public ResourceNotFoundException(String identity) {
        this.identity = identity;
    }
}
