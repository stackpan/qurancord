package com.stackpan.qurancord.core.exception;

public class SurahNotFoundException extends NotFoundException {

    public SurahNotFoundException() {
        super("Surah");
    }

    @Override
    public String getUserMessage() {
        return "Surah tidak ditemukan";
    }

    public String getUserMessage(Integer userInputInteger) {
        return (userInputInteger < 1) ? "Input anda sulit dimengerti. Semoga anda tetap berada di jalan yang benar" :
                "Surah di Al-Quran hanya berjumlah 114 surah, saya tidak menemukan adanya surah ke " + userInputInteger;
    }
}
