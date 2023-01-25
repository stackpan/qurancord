package com.stackpan.repository;

import com.stackpan.annotation.Injectable;
import com.stackpan.entity.Ayah;

import java.util.ArrayList;
import java.util.List;

public final class MemoryAyahRepository implements AyahRepository, StorableRepository<Ayah> {

    @Injectable
    private List<Ayah> ayahList;

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
        ayahList.add(data);
    }
}
