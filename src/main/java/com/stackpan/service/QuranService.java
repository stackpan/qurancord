package com.stackpan.service;

import com.stackpan.entity.Surah;

import java.util.Map;

public interface QuranService {

    Surah getRandomSurah();

    Map<String, Object> getRandomAyah();

    Map<String, Object> getRandomAyah(String surahName);

    Map<String, Object> getRandomAyah(Integer surahNumber);

    Surah openSurah(String surahName);

    Surah openSurah(Integer surahNumber);

    Map<String, Object> openAyah(String surahName, Integer ayahNumber);

    Map<String, Object> openAyah(Integer surahNumber, Integer ayahNumber);

}
