package io.github.stackpan.qurancord.service;

import io.github.stackpan.qurancord.properties.QuranEditionConfigurationProperties;
import io.github.stackpan.qurancord.exception.AyahNotFoundException;
import io.github.stackpan.qurancord.exception.SurahNotFoundException;
import io.github.stackpan.qurancord.fetcher.AyahFetcher;
import io.github.stackpan.qurancord.fetcher.SearchFetcher;
import io.github.stackpan.qurancord.model.*;
import io.github.stackpan.qurancord.service.provider.AyahServiceProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AyahServiceImpl implements AyahService {

    @NonNull
    private AyahServiceProvider ayahServiceProvider;

    @NonNull
    private AyahFetcher ayahFetcher;

    @NonNull
    private SearchFetcher searchFetcher;

    @NonNull
    private SurahService surahService;

    @NonNull
    private QuranEditionConfigurationProperties quranEditionConfigurationProperties;

    @Override
    public AyahDetail get(Integer surahNumber, Integer ayahNumber) throws AyahNotFoundException {
        ApiResponse<AyahDetail> response = ayahFetcher.get(new AyahReference(surahNumber, ayahNumber));
        return response.getData();
    }

    @Override
    public AyahDetailWithTranslate getWithTranslate(Integer surahNumber, Integer ayahNumber) throws AyahNotFoundException {
        return ayahServiceProvider.get(
                new AyahReference(surahNumber, ayahNumber),
                quranEditionConfigurationProperties.verse(),
                quranEditionConfigurationProperties.translate()
        );
    }

    @Override
    public AyahDetailWithTranslate getWithTranslate(Integer surahNumber, Integer ayahNumber, String translateEdition) throws AyahNotFoundException {
        return ayahServiceProvider.get(
                new AyahReference(surahNumber, ayahNumber),
                quranEditionConfigurationProperties.verse(),
                translateEdition
        );
    }

    @Override
    public Optional<SearchResult> search(String keyword) {
        Optional<ApiResponse<SearchResult>> response = searchFetcher.search(keyword);
        return response.map(ApiResponse::getData);
    }

    @Override
    public Optional<SearchResult> search(String keyword, Integer surahNumber) {
        Optional<ApiResponse<SearchResult>> response = searchFetcher.search(keyword, surahNumber);
        return response.map(ApiResponse::getData);
    }

    @Override
    public AyahDetail random() {
        SurahDetail surah = surahService.random();
        return ayahServiceProvider.random(surah);
    }

    @Override
    public AyahDetail random(Integer surahNumber) throws SurahNotFoundException {
        SurahDetail surah = surahService.get(surahNumber);
        return ayahServiceProvider.random(surah);
    }

    @Override
    public AyahDetailWithTranslate randomWithTranslate() {
        SurahDetail[] surahs = surahService.randomMultiEdition();
        return ayahServiceProvider.random(surahs);
    }

    @Override
    public AyahDetailWithTranslate randomWithTranslate(String translateEdition) {
        SurahDetail[] surahs = surahService.randomMultiEdition(translateEdition);
        return ayahServiceProvider.random(surahs);
    }

    @Override
    public AyahDetailWithTranslate randomWithTranslate(Integer surahNumber) {
        SurahDetail[] surahs = surahService.getMultiEdition(surahNumber);
        return ayahServiceProvider.random(surahs);
    }

    @Override
    public AyahDetailWithTranslate randomWithTranslate(Integer surahNumber, String translateEdition) {
        SurahDetail[] surahs = surahService.getMultiEdition(surahNumber, translateEdition);
        return ayahServiceProvider.random(surahs);
    }
}
