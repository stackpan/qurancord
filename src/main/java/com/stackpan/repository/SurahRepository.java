package com.stackpan.repository;

import com.stackpan.entity.Surah;

import java.util.List;

public sealed interface SurahRepository
        permits ApiSurahRepository, MemorySurahRepository
{
    List<Surah> getAll();

    Surah getByNumber(Integer number);

    // todo: add advanced compare using RegExp
    Surah getByLatinName(String latinName);
}
