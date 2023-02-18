package com.stackpan.qurancord.core.repository;

import com.stackpan.qurancord.core.entity.Surah;

import java.util.List;

public sealed interface SurahRepository
        permits ApiSurahRepository, MemorySurahRepository
{

    int MAX_SURAH = 114;

    List<Surah> getAll();

    Surah getByNumber(Integer number);

    Surah getByLatinName(String latinName);
}
