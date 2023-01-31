package com.stackpan.repository;

import com.stackpan.entity.RevelationType;
import com.stackpan.entity.Surah;
import com.stackpan.util.Injector;
import com.stackpan.util.SurahBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemorySurahRepositoryTest {

    @Test
    void testGetAll() {
        SurahRepository surahRepository = new MemorySurahRepository();

        List<Surah> surahList = generateDummy(10);

        try {
            new Injector<>().injectField(surahRepository, surahList);
        } catch (IllegalAccessException ignored) {
        }

        Assertions.assertNotNull(surahRepository.getAll());
        Assertions.assertEquals(surahList, surahRepository.getAll());
    }

    @Test
    void testGetByNumber() {
        SurahRepository surahRepository = new MemorySurahRepository();

        List<Surah> surahList = generateDummy(10);

        try {
            new Injector<>().injectField(surahRepository, surahList);
        } catch (IllegalAccessException ignored) {
        }

        Assertions.assertNotNull(surahRepository.getByNumber(2));
        Assertions.assertEquals(surahList.get(1), surahRepository.getByNumber(2));
    }

    @Test
    void testGetByLatinName() {
        SurahRepository surahRepository = new MemorySurahRepository();

        List<Surah> surahList = generateDummy(10);

        try {
            new Injector<>().injectField(surahRepository, surahList);
        } catch (IllegalAccessException ignored) {
        }

        Assertions.assertNotNull(surahRepository.getByLatinName("Surah latinName: 1"));
        Assertions.assertEquals(surahList.get(0), surahRepository.getByLatinName("Surah latinName: 1"));
    }

    @Test
    void testStore() {
        StorableRepository<Surah> surahStorableRepository = new MemorySurahRepository();
        surahStorableRepository.store(Collections.singletonList(generateDummy(1).get(0)));

        SurahRepository surahRepository = (SurahRepository) surahStorableRepository;
        Assertions.assertNotNull(surahRepository.getAll());
    }

    @Test
    void testStoreThrows() {
        StorableRepository<Surah> surahStorableRepository = new MemorySurahRepository();
        Assertions.assertThrows(NullPointerException.class, () -> surahStorableRepository.store(null));
    }

    private List<Surah> generateDummy(int max) {
        List<Surah> dummy = new ArrayList<>();

        for (int i = 1; i <= max; i++) {
            dummy.add(SurahBuilder.createBuilder()
                    .setNumber(i)
                    .setArabicName("Surah arabicName: " + i)
                    .setLatinName("Surah latinName: " + i)
                    .setDescription("Surah description: " + i)
                    .setMeaning("Surah meaning: " + i)
                    .setAyahCount(i)
                    .setRevelationType(i % 2 == 0 ? RevelationType.MECCAN : RevelationType.MEDINAN)
                    .setAudioUrl("Surah audioUrl: " + i)
                    .build());
        }

        return dummy;
    }
}
