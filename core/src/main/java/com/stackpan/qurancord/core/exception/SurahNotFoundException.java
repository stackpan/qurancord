package com.stackpan.qurancord.core.exception;

public class SurahNotFoundException extends NotFoundException {

    public SurahNotFoundException() {
        super("Surah");
    }

    @Override
    public String getUserMessage() {
        return "Surah tidak ditemukan";
    }

}
