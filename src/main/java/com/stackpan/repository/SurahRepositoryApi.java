package com.stackpan.repository;

import com.stackpan.entity.Surah;
import com.stackpan.util.Deserializer;
import com.stackpan.util.RequestApi;

import java.net.http.HttpClient;
import java.util.List;

public final class SurahRepositoryApi implements SurahRepository {

    private final HttpClient httpClient;

    public SurahRepositoryApi(HttpClient httpClient) {
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
    public Surah get(Integer number) {
        var json = RequestApi.fetchData(httpClient, "https://equran.id/api/surat/" + number);

        return Deserializer.parseElementSurah(Deserializer.parseGetSurahDetail(json));
    }

}
