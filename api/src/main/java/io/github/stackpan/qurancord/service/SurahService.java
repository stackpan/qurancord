package io.github.stackpan.qurancord.service;

import io.github.stackpan.qurancord.exception.SurahNotFoundException;
import io.github.stackpan.qurancord.model.Surah;
import io.github.stackpan.qurancord.model.SurahDetail;

import java.util.List;
import java.util.Optional;

public interface SurahService {

    List<String> getAllNames();

    SurahDetail get(Integer number) throws SurahNotFoundException;

    SurahDetail[] getMultiEdition(Integer number) throws SurahNotFoundException;

    SurahDetail[] getMultiEdition(Integer number, String translateEdition) throws SurahNotFoundException;

    Optional<Surah> search(String keyword);

    SurahDetail random();

    SurahDetail[] randomMultiEdition();

    SurahDetail[] randomMultiEdition(String translateEdition);

}
