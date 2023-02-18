package com.stackpan.qurancord.core.repository;

import com.stackpan.qurancord.core.entity.Ayah;

import java.util.List;

public sealed interface AyahRepository
        permits ApiAyahRepository, MemoryAyahRepository
{
    List<Ayah> getAllBySurah(Integer surahNumber);

    Ayah getBySurah(Integer surahNumber, Integer number);
}
