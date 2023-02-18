package com.stackpan.qurancord.exception;

public abstract class NotFoundException extends RuntimeException {

    public NotFoundException(String context) {
        super(context + " is not found");
    }

    public abstract String getUserMessage();

    public abstract String getUserMessage(Integer userInputInteger);
}
