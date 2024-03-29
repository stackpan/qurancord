package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.exception.AyahNotFoundException;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.Ayah;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.model.AyahDetailWithTranslate;
import com.ivanzkyanto.qcv2.model.SearchResult;

import java.util.Optional;

public interface AyahService {

    AyahDetail get(Integer surahNumber, Integer ayahNumber) throws AyahNotFoundException;

    AyahDetailWithTranslate getWithTranslate(Integer surahNumber, Integer ayahNumber) throws AyahNotFoundException;

    AyahDetailWithTranslate getWithTranslate(Integer surahNumber, Integer ayahNumber, String translateEdition) throws AyahNotFoundException;

    Optional<SearchResult> search(String keyword);

    Optional<SearchResult> search(String keyword, Integer surahNumber);

    AyahDetail random();

    AyahDetail random(Integer surahNumber) throws SurahNotFoundException;

    AyahDetailWithTranslate randomWithTranslate();

    AyahDetailWithTranslate randomWithTranslate(String translateEdition);

    AyahDetailWithTranslate randomWithTranslate(Integer surahNumber);

    AyahDetailWithTranslate randomWithTranslate(Integer surahNumber, String translateEdition);

}
