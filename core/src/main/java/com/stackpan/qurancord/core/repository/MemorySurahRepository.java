package com.stackpan.qurancord.core.repository;

import com.stackpan.qurancord.core.annotation.Injectable;
import com.stackpan.qurancord.core.entity.Surah;
import com.stackpan.qurancord.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MemorySurahRepository implements SurahRepository, StorableRepository<Surah> {

    @Injectable
    private final List<Surah> surahList;

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
                .filter(surah -> StringUtil.matchSurahName(surah.latinNameRegex(), latinName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void store(Surah data) {
        if (data == null) throw new NullPointerException("Surah data cannot be null");
        var isThere = surahList.stream().anyMatch(surah -> surah.equals(data));
        if (!isThere) surahList.add(data); // anti-duplicate
    }
}
