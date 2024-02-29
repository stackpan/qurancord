package com.ivanzkyanto.qcv2.service.impl;

import com.ivanzkyanto.qcv2.exception.AyahNotFoundException;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.fetcher.SearchFetcher;
import com.ivanzkyanto.qcv2.model.*;
import com.ivanzkyanto.qcv2.service.AyahService;
import com.ivanzkyanto.qcv2.service.SurahService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class AyahServiceImpl implements AyahService {

    @NonNull
    private AyahFetcher ayahFetcher;

    @NonNull
    private SearchFetcher searchFetcher;

    @NonNull
    private SurahService surahService;

    @Override
    public AyahDetail get(Integer surahNumber, Integer ayahNumber) throws AyahNotFoundException {
        ApiResponse<AyahDetail> response = ayahFetcher.get(new AyahReference(surahNumber, ayahNumber));
        return response.getData();
    }

    @Override
    public Optional<SearchResult> search(String keyword) {
        Optional<ApiResponse<SearchResult>> response = searchFetcher.search(keyword);
        return response.map(ApiResponse::getData);
    }

    @Override
    public AyahDetail random() {
        SurahDetail surah = surahService.random();

        return random(surah);
    }

    @Override
    public AyahDetail random(Integer surahNumber) throws SurahNotFoundException {
        SurahDetail surah = surahService.get(surahNumber);
        return random(surah);
    }

    private AyahDetail random(SurahDetail surah) {
        Random random = new Random();
        int ayahNumber = random.nextInt(surah.getNumberOfAyahs()) + 1;

       return surah.getAyahs().stream()
                .filter(ayah -> ayah.getNumberInSurah().equals(ayahNumber))
                .findFirst()
                .map(ayah -> ayah.toDetail(surah))
                .orElse(null);
    }
}
