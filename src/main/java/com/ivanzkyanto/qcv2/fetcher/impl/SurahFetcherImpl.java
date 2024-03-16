package com.ivanzkyanto.qcv2.fetcher.impl;

import com.ivanzkyanto.qcv2.configuration.properties.QuranEditionConfigurationProperties;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.fetcher.SurahFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SurahFetcherImpl implements SurahFetcher {

    @NonNull
    private RestTemplate restTemplate;

    @NonNull
    private QuranEditionConfigurationProperties quranEditionConfigurationProperties;

    @Override
    public ApiResponse<List<Surah>> getAll() {
        var type = new ParameterizedTypeReference<ApiResponse<List<Surah>>>() {};
        var response = restTemplate.exchange("/v1/surah", HttpMethod.GET, null, type);

        return response.getBody();
    }

    @Override
    public ApiResponse<SurahDetail> get(Integer number) throws SurahNotFoundException {
        return get(number, quranEditionConfigurationProperties.translate());
    }

    @Override
    public ApiResponse<SurahDetail> get(Integer number, String edition) throws SurahNotFoundException {
        try {
            var url = String.format("/v1/surah/%s/%s", number, edition);
            var type = new ParameterizedTypeReference<ApiResponse<SurahDetail>>() {};
            var response = restTemplate.exchange(url, HttpMethod.GET, null, type);

            return response.getBody();
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                throw new SurahNotFoundException(number, edition);
            }
            throw e;
        }
    }

    @Override
    public ApiResponse<SurahDetail[]> get(Integer number, String... editions) throws SurahNotFoundException {
        var joinedEdition = String.join(",", editions);
        try {
            var url = String.format("/v1/surah/%s/%s", number, joinedEdition);
            var type = new ParameterizedTypeReference<ApiResponse<SurahDetail[]>>() {};
            var response = restTemplate.exchange(url, HttpMethod.GET, null, type);

            return response.getBody();
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                throw new SurahNotFoundException(number, joinedEdition);
            }
            throw e;
        }
    }
}
