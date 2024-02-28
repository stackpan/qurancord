package com.ivanzkyanto.qcv2.service.impl;

import com.ivanzkyanto.qcv2.fetcher.SurahFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import com.ivanzkyanto.qcv2.service.SurahService;
import com.ivanzkyanto.qcv2.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

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

    @Override
    public Optional<SurahDetail> get(Integer number) {
        try {
            ApiResponse<SurahDetail> response = surahFetcher.get(number);
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

}
