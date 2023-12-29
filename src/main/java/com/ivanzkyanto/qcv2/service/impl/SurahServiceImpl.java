package com.ivanzkyanto.qcv2.service.impl;

import com.ivanzkyanto.qcv2.fetcher.SurahFetcher;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.service.SurahService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurahServiceImpl implements SurahService {

    @NonNull
    private SurahFetcher surahFetcher;

    @Override
    public Surah get(int number) {
        return null;
    }

    @Override
    public Surah search(String name) {
        return null;
    }

    @Override
    public Surah random() {
        return null;
    }

}
