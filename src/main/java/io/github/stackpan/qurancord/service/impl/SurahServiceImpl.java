package io.github.stackpan.qurancord.service.impl;

import io.github.stackpan.qurancord.configuration.properties.QuranEditionConfigurationProperties;
import io.github.stackpan.qurancord.exception.SurahNotFoundException;
import io.github.stackpan.qurancord.fetcher.SurahFetcher;
import io.github.stackpan.qurancord.model.ApiResponse;
import io.github.stackpan.qurancord.model.Surah;
import io.github.stackpan.qurancord.model.SurahDetail;
import io.github.stackpan.qurancord.service.SurahService;
import io.github.stackpan.qurancord.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

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
