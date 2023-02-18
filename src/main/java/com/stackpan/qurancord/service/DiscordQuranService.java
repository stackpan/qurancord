package com.stackpan.qurancord.service;

import com.stackpan.qurancord.entity.Ayah;
import com.stackpan.qurancord.entity.Surah;
import com.stackpan.qurancord.exception.AyahNotFoundException;
import com.stackpan.qurancord.exception.SurahNotFoundException;
import com.stackpan.qurancord.repository.*;

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
        return getRandomAyah(searchSurah(surahName).number());
    }

    @Override
    public Map<String, Object> getRandomAyah(Integer surahNumber) {
        return searchAyah(surahNumber, 1 + new Random()
                .nextInt(searchSurah(surahNumber).ayahCount() - 1));
    }

    @Override
    public Surah searchSurah(String surahName) {
        var readableMemoryRepository = (SurahRepository) memorySurahRepository;

        return Optional.ofNullable(readableMemoryRepository.getByLatinName(surahName))
                .orElseGet(() -> {
                    // don't send request if in memory repository is not empty
                    if (readableMemoryRepository.getAll().isEmpty())
                        apiSurahRepository.getAll()
                                .forEach(memorySurahRepository::store);

                    return Optional.ofNullable(((SurahRepository) memorySurahRepository).getByLatinName(surahName))
                            .orElseThrow(SurahNotFoundException::new);
                });
    }

    @Override
    public Surah searchSurah(Integer surahNumber) {
        if (surahNumber < 1 || surahNumber > SurahRepository.MAX_SURAH) throw new SurahNotFoundException();

        var readableMemoryRepository = (SurahRepository) memorySurahRepository;

        return Optional.ofNullable(readableMemoryRepository.getByNumber(surahNumber))
                .orElseGet(() -> {
                    // don't send request if in memory repository is not empty
                    if (readableMemoryRepository.getAll().isEmpty())
                        apiSurahRepository.getAll()
                                .forEach(memorySurahRepository::store);

                    return Optional.ofNullable(((SurahRepository) memorySurahRepository).getByNumber(surahNumber))
                            .orElseThrow(SurahNotFoundException::new);
                });
    }

    @Override
    public Map<String, Object> searchAyah(Integer surahNumber, Integer ayahNumber) throws SurahNotFoundException {
        // surah object is still be required because it will return a Map of Surah and Ayah
        var surah = searchSurah(surahNumber);

        if (ayahNumber < 1 || ayahNumber > surah.ayahCount()) throw new AyahNotFoundException();

        var readableMemoryRepository = (AyahRepository) memoryAyahRepository;

        var ayah = Optional.ofNullable(readableMemoryRepository.getBySurah(surah.number(), ayahNumber))
                .orElseGet(() -> {
                    // don't send request if in memory repository is not empty
                    if (readableMemoryRepository.getAllBySurah(surahNumber).isEmpty())
                        apiAyahRepository.getAllBySurah(surahNumber)
                                .forEach(memoryAyahRepository::store);

                    return Optional.ofNullable(((AyahRepository) memoryAyahRepository).getBySurah(surah.number(), ayahNumber))
                            .orElseThrow(AyahNotFoundException::new);
                });

        return new HashMap<>(Map.of("surah", surah, "ayah", ayah));
    }

    public Map<String, Object> searchAyah(String surahName, Integer ayahNumber) throws SurahNotFoundException {
        // surah object is still be required because it will return a Map of Surah and Ayah
        var surah = searchSurah(surahName);

        return searchAyah(surah.number(), ayahNumber);
    }
}
