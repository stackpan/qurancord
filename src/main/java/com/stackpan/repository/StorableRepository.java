package com.stackpan.repository;

public sealed interface StorableRepository<T>
        permits MemoryAyahRepository, MemorySurahRepository
{
    void store(T data);
}
