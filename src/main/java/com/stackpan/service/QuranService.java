package com.stackpan.service;

import com.stackpan.entity.Surah;

import java.util.Map;

public interface QuranService {

    Surah getRandomSurah();

    Map<String, Object> getRandomAyah();

    Map<String, Object> getRandomAyah(String surahName);

    Map<String, Object> getRandomAyah(Integer surahNumber);

    Surah searchSurah(String surahName);

    Surah searchSurah(Integer surahNumber);

    Map<String, Object> searchAyah(String surahName, Integer ayahNumber);

    Map<String, Object> searchAyah(Integer surahNumber, Integer ayahNumber);

}
