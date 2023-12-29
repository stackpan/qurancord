package com.ivanzkyanto.qcv2.fetcher.impl;

import com.ivanzkyanto.qcv2.fetcher.SurahFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SurahFetcherImpl implements SurahFetcher {

    @NonNull
    private RestTemplate restTemplate;

    @Override
    public ApiResponse<List<Surah>> getAll() {
        RequestEntity<Void> request = new RequestEntity<>(HttpMethod.GET, URI.create("http://api.alquran.cloud/v1/surah"));

        ResponseEntity<ApiResponse<List<Surah>>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }

    @Override
    public ApiResponse<SurahDetail> get(Integer number) {
        return get(number, "en.asad");
    }

    @Override
    public ApiResponse<SurahDetail> get(Integer number, String edition) {
        RequestEntity<Void> request = new RequestEntity<>(HttpMethod.GET, URI.create("http://api.alquran.cloud/v1/surah/" + number + "/" + edition));

        ResponseEntity<ApiResponse<SurahDetail>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }
}
