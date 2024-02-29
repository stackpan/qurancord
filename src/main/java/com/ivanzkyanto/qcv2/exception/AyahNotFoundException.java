package com.ivanzkyanto.qcv2.exception;

import lombok.Getter;

@Getter
public class AyahNotFoundException extends ResourceNotFoundException {

    private final String reference;

    private final String edition;

    public AyahNotFoundException(String reference, String edition) {
        super(String.format("%s:%s", reference, edition));
        this.reference = reference;
        this.edition = edition;
    }
}
