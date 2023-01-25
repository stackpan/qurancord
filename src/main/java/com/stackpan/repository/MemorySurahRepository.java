package com.stackpan.repository;

import com.stackpan.annotation.Injectable;
import com.stackpan.entity.Surah;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class MemorySurahRepository implements SurahRepository, StorableRepository<Surah> {

    @Injectable
    private List<Surah> surahList;

    public MemorySurahRepository() {
        this.surahList = new ArrayList<>();
    }

    @Override
    public List<Surah> getAll() {
        return Collections.unmodifiableList(surahList);
    }

    @Override
    public Surah getByNumber(Integer number) {
        return surahList.stream()
                .filter(surah -> surah.number() == number)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Surah getByLatinName(String latinName) {
        return surahList.stream()
                .filter(surah -> Objects.equals(surah.latinName(), latinName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void store(Surah data) {
        if (data == null) throw new NullPointerException("Surah data cannot be null");
        surahList.add(data);
    }
}
