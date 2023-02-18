package com.stackpan.qurancord.repository;

import com.stackpan.qurancord.annotation.Injectable;
import com.stackpan.qurancord.entity.Ayah;

import java.util.ArrayList;
import java.util.List;

public final class MemoryAyahRepository implements AyahRepository, StorableRepository<Ayah> {

    @Injectable
    private final List<Ayah> ayahList;

    public MemoryAyahRepository() {
        ayahList = new ArrayList<>();
    }

    @Override
    public List<Ayah> getAllBySurah(Integer surahNumber) {
        return ayahList.stream()
                .filter(ayah -> ayah.surahNumber() == surahNumber)
                .toList();
    }

    @Override
    public Ayah getBySurah(Integer surahNumber, Integer number) {
        return getAllBySurah(surahNumber).stream()
                .filter(ayah -> ayah.number() == number)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void store(Ayah data) {
        if (data == null) throw new NullPointerException("Ayah data cannot be null");
        var isThere = ayahList.stream().anyMatch(surah -> surah.equals(data));
        if (!isThere) ayahList.add(data); // anti-duplicate
    }
}
