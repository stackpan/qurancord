package com.stackpan.repository;

public sealed interface StorableRepository<T>
        permits AyahRepositoryMemory, SurahRepositoryMemory
{
    void store(T data);
}
