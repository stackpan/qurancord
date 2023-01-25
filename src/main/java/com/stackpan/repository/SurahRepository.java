package com.stackpan.repository;

import com.stackpan.entity.Surah;

import java.util.List;

public sealed interface SurahRepository
        permits SurahRepositoryApi, SurahRepositoryMemory
{
    List<Surah> getAll();

    Surah get(Integer number);
}
