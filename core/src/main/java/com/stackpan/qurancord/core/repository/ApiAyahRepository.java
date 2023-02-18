package com.stackpan.qurancord.core.repository;

import com.stackpan.qurancord.core.entity.Ayah;
import com.stackpan.qurancord.core.util.Deserializer;
import com.stackpan.qurancord.core.util.RequestApi;

import java.net.http.HttpClient;
import java.util.List;

public final class ApiAyahRepository implements AyahRepository
{

    private final HttpClient httpClient;

    public ApiAyahRepository(HttpClient httpClient) {
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
