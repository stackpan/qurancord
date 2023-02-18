package com.stackpan.qurancord.core.exception;

public class AyahNotFoundException extends NotFoundException {
    public AyahNotFoundException() {
        super("Ayah");
    }

    @Override
    public String getUserMessage() {
        return "Ayat tidak ditemukan";
    }

    public String getUserMessage(Integer userInputInteger) {
        return (userInputInteger < 1) ? "Input anda sulit dimengerti. Semoga anda tetap berada di jalan yang benar" : getUserMessage();
    }
}
