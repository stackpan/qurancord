package io.github.stackpan.qurancord.fetcher;

import io.github.stackpan.qurancord.exception.SurahNotFoundException;
import io.github.stackpan.qurancord.model.ApiResponse;
import io.github.stackpan.qurancord.model.Surah;
import io.github.stackpan.qurancord.model.SurahDetail;

import java.util.List;

public interface SurahFetcher {

    ApiResponse<List<Surah>> getAll();

    ApiResponse<SurahDetail> get(Integer number) throws SurahNotFoundException;

    ApiResponse<SurahDetail> get(Integer number, String edition) throws SurahNotFoundException;

    ApiResponse<SurahDetail[]> get(Integer number, String... editions) throws SurahNotFoundException;

}
