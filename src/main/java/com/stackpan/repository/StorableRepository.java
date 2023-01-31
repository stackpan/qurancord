package com.stackpan.repository;

import java.util.List;

public sealed interface StorableRepository<T>
        permits MemoryAyahRepository, MemorySurahRepository
{
    void store(List<T> data);
}
