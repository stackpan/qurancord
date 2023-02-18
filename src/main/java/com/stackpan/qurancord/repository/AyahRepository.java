package com.stackpan.qurancord.repository;

import com.stackpan.qurancord.entity.Ayah;

import java.util.List;

public sealed interface AyahRepository
        permits ApiAyahRepository, MemoryAyahRepository
{
    List<Ayah> getAllBySurah(Integer surahNumber);

    Ayah getBySurah(Integer surahNumber, Integer number);
}
