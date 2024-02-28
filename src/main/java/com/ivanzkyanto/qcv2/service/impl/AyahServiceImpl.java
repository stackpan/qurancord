package com.ivanzkyanto.qcv2.service.impl;

import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.fetcher.SearchFetcher;
import com.ivanzkyanto.qcv2.model.*;
import com.ivanzkyanto.qcv2.service.AyahService;
import com.ivanzkyanto.qcv2.service.SurahService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AyahServiceImpl implements AyahService {

    @NonNull
    private AyahFetcher ayahFetcher;

    @NonNull
    private SearchFetcher searchFetcher;

    @NonNull
    private SurahService surahService;

    @Override
    public Ayah get(Integer surahNumber, Integer ayahNumber) {
        ApiResponse<AyahDetail> response = ayahFetcher.get(new AyahReference(surahNumber, ayahNumber));

        return response.getData();
    }

    @Override
    public SearchResult search(String keyword) {
        ApiResponse<SearchResult> response = searchFetcher.search(keyword);

        return response.getData();
    }

    @Override
    public Ayah random() {
        SurahDetail surah = surahService.random();

        return random(surah);
    }

    @Override
    public Ayah random(Integer surahNumber) throws SurahNotFoundException {
        Optional<SurahDetail> surah = surahService.get(surahNumber);

        if (surah.isEmpty()) {
            throw new SurahNotFoundException();
        }

        return random(surah.get());
    }

    private Ayah random(SurahDetail surah) {
        Random random = new Random();
        int ayahNumber = random.nextInt(surah.getNumberOfAyahs()) + 1;

        return surah.getAyahs().stream()
                .filter(ayah -> ayah.getNumberInSurah().equals(ayahNumber))
                .findFirst()
                .orElse(null);
    }
}
