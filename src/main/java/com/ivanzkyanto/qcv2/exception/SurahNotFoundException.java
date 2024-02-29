package com.ivanzkyanto.qcv2.exception;

import lombok.Getter;

@Getter
public class SurahNotFoundException extends ResourceNotFoundException {

    private final Integer number;

    private final String edition;

    public SurahNotFoundException(Integer number, String edition) {
        super(String.format("%s:%s", number, edition));
        this.number = number;
        this.edition = edition;
    }
}
