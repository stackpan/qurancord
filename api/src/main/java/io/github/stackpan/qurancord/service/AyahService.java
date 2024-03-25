package io.github.stackpan.qurancord.service;

import io.github.stackpan.qurancord.exception.AyahNotFoundException;
import io.github.stackpan.qurancord.exception.SurahNotFoundException;
import io.github.stackpan.qurancord.model.AyahDetail;
import io.github.stackpan.qurancord.model.AyahDetailWithTranslate;
import io.github.stackpan.qurancord.model.SearchResult;

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
