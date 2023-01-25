package com.stackpan.repository;

import com.stackpan.annotation.Injectable;
import com.stackpan.entity.Surah;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SurahRepositoryMemory implements SurahRepository, StorableRepository<Surah> {

    @Injectable
    private List<Surah> surahList;

    public SurahRepositoryMemory() {
        this.surahList = new ArrayList<>();
    }

    @Override
    public List<Surah> getAll() {
        return Collections.unmodifiableList(surahList);
    }

    @Override
    public Surah get(Integer number) {
        return surahList.stream()
                .filter(surah -> surah.number() == number)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void store(Surah data) {
        if (data == null) throw new NullPointerException("Surah data cannot be null");
        surahList.add(data);
    }
}
