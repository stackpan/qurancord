package com.ivanzkyanto.qcv2.service.impl;

import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.fetcher.SearchFetcher;
import com.ivanzkyanto.qcv2.model.*;
import com.ivanzkyanto.qcv2.service.AyahService;
import com.ivanzkyanto.qcv2.service.SurahService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Objects;
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
    public Optional<AyahDetail> get(Integer surahNumber, Integer ayahNumber) {
        try {
            ApiResponse<AyahDetail> response = ayahFetcher.get(new AyahReference(surahNumber, ayahNumber));

            return Optional.of(response.getData());
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                return Optional.empty();
            }
            log.error(exception.getMessage());
            throw exception;
        }
    }

    @Override
    public Optional<SearchResult> search(String keyword) {
        ApiResponse<SearchResult> response = searchFetcher.search(keyword);

        if (Objects.isNull(response)) {
            return Optional.empty();
        }

        return Optional.of(response.getData());
    }

    @Override
    public AyahDetail random() {
        SurahDetail surah = surahService.random();

        return random(surah);
    }

    @Override
    public AyahDetail random(Integer surahNumber) throws SurahNotFoundException {
        Optional<SurahDetail> surah = surahService.get(surahNumber);

        if (surah.isEmpty()) {
            throw new SurahNotFoundException();
        }

        return random(surah.get());
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
