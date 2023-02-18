package com.stackpan.qurancord.util;

import com.stackpan.qurancord.entity.Ayah;

public class AyahBuilder {
    private Integer id;
    private Integer surahNumber;
    private Integer number;
    private String arabicText;
    private String latinText;
    private String bahasaTranslate;

    private AyahBuilder() {}

    public static AyahBuilder createBuilder() {
        return new AyahBuilder();
    }

    public AyahBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public AyahBuilder setSurahNumber(Integer surahNumber) {
        this.surahNumber = surahNumber;
        return this;
    }

    public AyahBuilder setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public AyahBuilder setArabicText(String arabicText) {
        this.arabicText = arabicText;
        return this;
    }

    public AyahBuilder setLatinText(String latinText) {
        this.latinText = latinText;
        return this;
    }

    public AyahBuilder setBahasaTranslate(String bahasaTranslate) {
        this.bahasaTranslate = bahasaTranslate;
        return this;
    }

    public Ayah build() {
        try {
            FieldValidator.validateNotNull(this);
        } catch (IllegalAccessException ignored) {
        }
        return new Ayah(id, surahNumber, number, arabicText, latinText, bahasaTranslate);
    }
}
