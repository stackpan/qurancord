package com.ivanzkyanto.qcv2.service.impl;

import com.ivanzkyanto.qcv2.configuration.properties.QuranEditionConfigurationProperties;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.fetcher.SurahFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import com.ivanzkyanto.qcv2.service.SurahService;
import com.ivanzkyanto.qcv2.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurahServiceImpl implements SurahService {

    @NonNull
    private SurahFetcher surahFetcher;

    @NonNull
    private QuranEditionConfigurationProperties quranEditionConfigurationProperties;

    @Override
    public List<String> getAllNames() {
        return surahFetcher.getAll().getData().stream()
                .map(Surah::getEnglishName)
                .toList();
    }

    @Override
    public SurahDetail get(Integer number) throws SurahNotFoundException {
        var response = surahFetcher.get(number);
        return response.getData();
    }

    @Override
    public SurahDetail[] getMultiEdition(Integer number) throws SurahNotFoundException {
        var response = surahFetcher.get(
                number,
                quranEditionConfigurationProperties.verse(),
                quranEditionConfigurationProperties.translate()
        );
        return response.getData();
    }

    @Override
    public SurahDetail[] getMultiEdition(Integer number, String translateEdition) throws SurahNotFoundException {
        var response = surahFetcher.get(
                number,
                quranEditionConfigurationProperties.verse(),
                translateEdition
        );
        return response.getData();
    }

    @Override
    public Optional<Surah> search(String keyword) {
        ApiResponse<List<Surah>> response = surahFetcher.getAll();

        return response.getData().stream()
                .filter(surah -> {
                    String regex = StringUtils.regexifySurahName(surah.getEnglishName());
                    return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(keyword).find();
                })
                .findFirst();
    }

    @Override
    public SurahDetail random() {
        Random random = new Random();
        int number = random.nextInt(114) + 1;

        ApiResponse<SurahDetail> response = surahFetcher.get(number);

        return response.getData();
    }

    @Override
    public SurahDetail[] randomMultiEdition() {
        Random random = new Random();
        int number = random.nextInt(114) + 1;

        ApiResponse<SurahDetail[]> response = surahFetcher.get(
                number,
                quranEditionConfigurationProperties.verse(),
                quranEditionConfigurationProperties.translate()
        );

        return response.getData();
    }

    @Override
    public SurahDetail[] randomMultiEdition(String translateEdition) {
        Random random = new Random();
        int number = random.nextInt(114) + 1;

        ApiResponse<SurahDetail[]> response = surahFetcher.get(
                number,
                quranEditionConfigurationProperties.verse(),
                translateEdition
        );

        return response.getData();
    }

}
