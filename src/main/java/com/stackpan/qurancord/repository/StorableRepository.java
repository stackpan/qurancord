package com.stackpan.qurancord.repository;

public sealed interface StorableRepository<T>
        permits MemoryAyahRepository, MemorySurahRepository
{
    void store(T data);
}
