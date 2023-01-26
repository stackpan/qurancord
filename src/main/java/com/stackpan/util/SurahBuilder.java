package com.stackpan.util;

import com.stackpan.entity.RevelationType;
import com.stackpan.entity.Surah;

public class SurahBuilder {
    private Integer number;
    private String arabicName;
    private String latinName;
    private Integer ayahCount;
    private RevelationType revelationType;
    private String meaning;
    private String description;
    private String audioUrl;

    private SurahBuilder() {

    }

    public static SurahBuilder createBuilder() {
        return new SurahBuilder();
    }

    public SurahBuilder setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public SurahBuilder setArabicName(String arabicName) {
        this.arabicName = arabicName;
        return this;
    }

    public SurahBuilder setLatinName(String latinName) {
        this.latinName = latinName;
        return this;
    }

    public SurahBuilder setAyahCount(Integer ayahCount) {
        this.ayahCount = ayahCount;
        return this;
    }

    public SurahBuilder setRevelationType(RevelationType revelationType) {
        this.revelationType = revelationType;
        return this;
    }

    public SurahBuilder setMeaning(String meaning) {
        this.meaning = meaning;
        return this;
    }

    public SurahBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public SurahBuilder setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
        return this;
    }

    public Surah build() {
        try {
            FieldValidator.validateNotNull(this);
        } catch (IllegalAccessException ignored) {
        }
        return new Surah(number, arabicName, latinName, ayahCount, revelationType, meaning, description, audioUrl);
    }
}
