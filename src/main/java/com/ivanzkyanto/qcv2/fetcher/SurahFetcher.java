package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;

import java.util.List;

public interface SurahFetcher {

    ApiResponse<List<Surah>> getAll();

    ApiResponse<SurahDetail> get (Integer number);

    ApiResponse<SurahDetail> get(Integer number, String edition);

}
