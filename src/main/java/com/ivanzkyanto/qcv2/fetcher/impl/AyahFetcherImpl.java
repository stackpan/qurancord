package com.ivanzkyanto.qcv2.fetcher.impl;

import com.ivanzkyanto.qcv2.exception.AyahNotFoundException;
import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.model.AyahReferenceMaker;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class AyahFetcherImpl implements AyahFetcher {

    @NonNull
    private RestTemplate restTemplate;

    @Override
    public ApiResponse<AyahDetail> get(AyahReferenceMaker reference) throws AyahNotFoundException {
        return get(reference, "en.asad");
    }

    @Override
    public ApiResponse<AyahDetail> get(AyahReferenceMaker reference, String edition) throws AyahNotFoundException {
        try {
            var url = String.format("/v1/ayah/%s/%s", reference.make(), edition);
            var type = new ParameterizedTypeReference<ApiResponse<AyahDetail>>() {};
            var response = restTemplate.exchange(url, HttpMethod.GET, null, type);

            return response.getBody();
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                throw new AyahNotFoundException(reference.make(), edition);
            }
            throw e;
        }
    }
}
