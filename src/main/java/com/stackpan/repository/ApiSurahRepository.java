package com.stackpan.repository;

import com.stackpan.entity.Surah;
import com.stackpan.util.Deserializer;
import com.stackpan.util.RequestApi;
import com.stackpan.util.StringUtil;

import java.net.http.HttpClient;
import java.util.List;

public final class ApiSurahRepository implements SurahRepository {

    private final HttpClient httpClient;

    public ApiSurahRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<Surah> getAll() {
        // fetch data from API
        var json = RequestApi.fetchData(httpClient, "https://equran.id/api/surat");

        // parse json data and map to the desired type to return
        return Deserializer.parseGetSurah(json).stream()
                .map(Deserializer::parseElementSurah)
                .toList();
    }

    @Override
    public Surah getByNumber(Integer number) {
       return getAll().stream()
                .filter(surah -> surah.number() == number)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Surah getByLatinName(String latinName) {
        return getAll().stream()
                .filter(surah -> StringUtil.matchSurahName(surah.latinNameRegex(), latinName))
                .findFirst()
                .orElse(null);
    }

}
