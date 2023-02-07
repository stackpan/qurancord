package com.stackpan.service;

import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
import com.stackpan.exception.AyahNotFoundException;
import com.stackpan.exception.SurahNotFoundException;
import com.stackpan.repository.*;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class DiscordQuranService implements QuranService {

    private final SurahRepository apiSurahRepository;
    private final AyahRepository apiAyahRepository;
    private final StorableRepository<Surah> memorySurahRepository;
    private final StorableRepository<Ayah> memoryAyahRepository;

    public DiscordQuranService(HttpClient httpClient) {
        this.apiSurahRepository = new ApiSurahRepository(httpClient);
        this.apiAyahRepository = new ApiAyahRepository(httpClient);
        this.memorySurahRepository = new MemorySurahRepository();
        this.memoryAyahRepository = new MemoryAyahRepository();
    }

    @Override
    public Surah getRandomSurah() {
        return searchSurah(1 + (new Random().nextInt(SurahRepository.MAX_SURAH)));
    }

    @Override
    public Map<String, Object> getRandomAyah() {
        return getRandomAyah(searchSurah(getRandomSurah().number()).number());
    }

    @Override
    public Map<String, Object> getRandomAyah(String surahName) {
        var surah = searchSurah(surahName);
        if (surah == null) throw new SurahNotFoundException();

        return getRandomAyah(surah.number());
    }

    @Override
    public Map<String, Object> getRandomAyah(Integer surahNumber) {
        var surah = searchSurah(surahNumber);
        if (surah == null) throw new SurahNotFoundException();

        return searchAyah(surahNumber, 1 + new Random()
                .nextInt(searchSurah(surahNumber).ayahCount() - 1));
    }

    @Override
    public Surah searchSurah(String surahName) {
        var result = Optional.ofNullable(((SurahRepository) memorySurahRepository)
                        .getByLatinName(surahName))
                .orElseGet(() -> {
                    apiSurahRepository.getAll().forEach(memorySurahRepository::store);
                    return ((SurahRepository) memorySurahRepository).getByLatinName(surahName);
                });

        if (result == null) throw new SurahNotFoundException();

        return result;
    }

    @Override
    public Surah searchSurah(Integer surahNumber) {
        var readableMemoryRepository = (SurahRepository) memorySurahRepository;

        var result = Optional.ofNullable(readableMemoryRepository.getByNumber(surahNumber))
                .orElseGet(() -> {
                    if (readableMemoryRepository.getAll().isEmpty()) apiSurahRepository.getAll().forEach(memorySurahRepository::store);
                    return ((SurahRepository) memorySurahRepository).getByNumber(surahNumber);
                });

        if (result == null) throw new SurahNotFoundException();

        return result;
    }

    @Override
    public Map<String, Object> searchAyah(Integer surahNumber, Integer ayahNumber) {
        var surah = searchSurah(surahNumber);
        var ayah = Optional.ofNullable(((AyahRepository) memoryAyahRepository)
                        .getBySurah(surah.number(), ayahNumber))
                .orElseGet(() -> {
                    apiAyahRepository.getAllBySurah(surahNumber).forEach(memoryAyahRepository::store);
                    return ((AyahRepository) memoryAyahRepository).getBySurah(surah.number(), ayahNumber);
                });
        if (ayah == null) throw new AyahNotFoundException();

        return new HashMap<>(Map.of("surah", surah, "ayah", ayah));
    }

    @Override
    public Map<String, Object> searchAyah(String surahName, Integer ayahNumber) {
        var surah = searchSurah(surahName);
        if (surah == null) throw new AyahNotFoundException();

        return searchAyah(surah.number(), ayahNumber);
    }
}
