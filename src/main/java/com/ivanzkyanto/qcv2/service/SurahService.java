package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;

import java.util.Optional;

public interface SurahService {

    Optional<SurahDetail> get(Integer number);

    Optional<Surah> search(String keyword);

    SurahDetail random();

}
