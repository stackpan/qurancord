package com.stackpan.service;

import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
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
        return openSurah(1 + (new Random().nextInt(SurahRepository.MAX_SURAH)));
    }

    @Override
    public Map<String, Object> getRandomAyah() {
        return getRandomAyah(openSurah(getRandomSurah().number()).number());
    }

    @Override
    public Map<String, Object> getRandomAyah(String surahName) {
        return getRandomAyah(openSurah(surahName).number());
    }

    @Override
    public Map<String, Object> getRandomAyah(Integer surahNumber) {
        return openAyah(surahNumber, 1 + new Random()
                .nextInt(openSurah(surahNumber).ayahCount() - 1));
    }

    @Override
    public Surah openSurah(String surahName) {
        return Optional.ofNullable(((SurahRepository) memorySurahRepository)
                        .getByLatinName(surahName))
                .orElseGet(() -> {
                    var apiResult = apiSurahRepository.getByLatinName(surahName);
                    memorySurahRepository.store(apiResult);
                    return apiResult;
                });
    }

    @Override
    public Surah openSurah(Integer surahNumber) {
        return Optional.ofNullable(((SurahRepository) memorySurahRepository)
                        .getByNumber(surahNumber))
                .orElseGet(() -> {
                    var apiResult = apiSurahRepository.getByNumber(surahNumber);
                    memorySurahRepository.store(apiResult);
                    return apiResult;
                });
    }

    @Override
    public Map<String, Object> openAyah(Integer surahNumber, Integer ayahNumber) {
        var surah = openSurah(surahNumber);
        var ayah = Optional.ofNullable(((AyahRepository) memoryAyahRepository)
                        .getBySurah(surah.number(), ayahNumber))
                .orElseGet(() -> {
                    var apiResult = apiAyahRepository.getBySurah(surah.number(), ayahNumber);
                    memoryAyahRepository.store(apiResult);
                    return apiResult;
                });

        return new HashMap<>(Map.of("surah", surah, "ayah", ayah));
    }

    @Override
    public Map<String, Object> openAyah(String surahName, Integer ayahNumber) {
        var surah = openSurah(surahName);
        return openAyah(surah.number(), ayahNumber);
    }
}
