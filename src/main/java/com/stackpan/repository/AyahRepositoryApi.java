package com.stackpan.repository;

import com.stackpan.entity.Ayah;
import com.stackpan.util.Deserializer;
import com.stackpan.util.RequestApi;

import java.net.http.HttpClient;
import java.util.List;

public final class AyahRepositoryApi implements AyahRepository
{

    private final HttpClient httpClient;

    public AyahRepositoryApi(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<Ayah> getAllBySurah(Integer surahNumber) {
        var json = RequestApi.fetchData(httpClient, "https://equran.id/api/surat/" + surahNumber);

        return Deserializer.parseGetAllAyahBySurah(json).stream()
                .map(Deserializer::parseElementAyah)
                .toList();
    }

    @Override
    public Ayah getBySurah(Integer surahNumber, Integer number) {
        return getAllBySurah(surahNumber).stream()
                .filter(ayah -> ayah.number() == number)
                .findFirst()
                .orElse(null);
    }
}
