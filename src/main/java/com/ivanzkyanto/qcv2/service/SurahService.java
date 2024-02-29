package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;

import java.util.Optional;

public interface SurahService {

    SurahDetail get(Integer number) throws SurahNotFoundException;

    Optional<Surah> search(String keyword);

    SurahDetail random();

}
