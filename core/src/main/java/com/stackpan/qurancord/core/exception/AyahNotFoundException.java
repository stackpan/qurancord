package com.stackpan.qurancord.core.exception;

public class AyahNotFoundException extends NotFoundException {
    public AyahNotFoundException() {
        super("Ayah");
    }

    @Override
    public String getUserMessage() {
        return "Ayat tidak ditemukan";
    }


}
