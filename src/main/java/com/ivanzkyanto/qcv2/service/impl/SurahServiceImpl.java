package com.ivanzkyanto.qcv2.service.impl;

import com.ivanzkyanto.qcv2.fetcher.SurahFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import com.ivanzkyanto.qcv2.service.SurahService;
import com.ivanzkyanto.qcv2.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SurahServiceImpl implements SurahService {

    @NonNull
    private SurahFetcher surahFetcher;

    @Override
    public SurahDetail get(Integer number) {
        ApiResponse<SurahDetail> response = surahFetcher.get(number);

        return response.getData();
    }

    @Override
    public Surah search(String keyword) {
        ApiResponse<List<Surah>> response = surahFetcher.getAll();

        return response.getData().stream()
                .filter(surah -> {
                    String regex = StringUtils.regexifySurahName(surah.getEnglishName());
                    return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(keyword).find();
                })
                .findFirst()
                .orElse(null);
    }

    @Override
    public SurahDetail random() {
        Random random = new Random();
        int number = random.nextInt(114) + 1;

        ApiResponse<SurahDetail> response = surahFetcher.get(number);

        return response.getData();
    }

}
