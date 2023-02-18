package com.stackpan.qurancord.core.repository;

import com.stackpan.qurancord.core.entity.Ayah;
import com.stackpan.qurancord.core.util.AyahBuilder;
import com.stackpan.qurancord.core.util.Injector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryAyahRepositoryTest {

    @Test
    void testGetAllBySurah() {
        AyahRepository ayahRepository = new MemoryAyahRepository();

        List<Ayah> dummyList = generateDummyList(5, 2);

        try {
            new Injector<>().injectField(ayahRepository, dummyList);
        } catch (IllegalAccessException ignored) {
        }

        Assertions.assertNotNull(ayahRepository.getAllBySurah(2));
        Assertions.assertEquals(5, ayahRepository.getAllBySurah(2).size());
    }

    @Test
    void testGetBySurah() {
        AyahRepository ayahRepository = new MemoryAyahRepository();

        List<Ayah> dummyList = generateDummyList(5, 2);

        try {
            new Injector<>().injectField(ayahRepository, dummyList);
        } catch (IllegalAccessException ignored) {
        }

        Assertions.assertNotNull(ayahRepository.getBySurah(2, 5));
        Assertions.assertEquals(dummyList.get(9), ayahRepository.getBySurah(2, 5));
    }

    @Test
    void testStore() {
        StorableRepository<Ayah> ayahStorableRepository = new MemoryAyahRepository();
        generateDummyList(1, 1).forEach(ayahStorableRepository::store);

        AyahRepository surahRepository = (AyahRepository) ayahStorableRepository;
        Assertions.assertNotNull(surahRepository.getAllBySurah(1));
    }

    @Test
    void testStoreAntiDuplicate() {
        StorableRepository<Ayah> ayahStorableRepository = new MemoryAyahRepository();
        generateDummyList(1, 3).forEach(ayahStorableRepository::store);
        generateDummyList(1, 3).forEach(ayahStorableRepository::store);
        generateDummyList(1, 3).forEach(ayahStorableRepository::store);

        AyahRepository surahRepository = (AyahRepository) ayahStorableRepository;
        var surahList = surahRepository.getAllBySurah(1);

        Assertions.assertFalse(surahList.stream().anyMatch(surah -> Collections.frequency(surahList, surah) > 1));
    }

    @Test
    void testStoreThrows() {
        StorableRepository<Ayah> ayahStorableRepository = new MemoryAyahRepository();
        Assertions.assertThrows(NullPointerException.class, () -> ayahStorableRepository.store(null));
    }

    private List<Ayah> generateDummyList(int ayahPerSurah, int surahMax) {
        List<Ayah> dummy = new ArrayList<>();

        int id = 1;
        for (int i = 1; i <= surahMax; i++) {
            for (int j = 1; j <= ayahPerSurah; j++) {
                dummy.add(AyahBuilder.createBuilder()
                        .setId(id)
                        .setSurahNumber(i)
                        .setNumber(j)
                        .setBahasaTranslate("Ayah bahasaTranslate: " + id)
                        .setLatinText("Ayah latinText: " + id)
                        .setArabicText("Ayah arabicText: " + id)
                        .build());
                id++;
            }
        }

        return dummy;
    }
}
