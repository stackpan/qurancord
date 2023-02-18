package com.stackpan.qurancord.core.repository;

public sealed interface StorableRepository<T>
        permits MemoryAyahRepository, MemorySurahRepository
{
    void store(T data);
}
