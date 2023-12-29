package com.ivanzkyanto.qcv2.fetcher.impl;

import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.util.AyahReferenceMaker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class AyahFetcherImpl implements AyahFetcher {

    @NonNull
    private RestTemplate restTemplate;

    @Override
    public ApiResponse<AyahDetail> get(AyahReferenceMaker reference) {
        return get(reference, "en.asad");
    }

    @Override
    public ApiResponse<AyahDetail> get(AyahReferenceMaker reference, String edition) {
        RequestEntity<Void> request = new RequestEntity<>(HttpMethod.GET, URI.create("http://api.alquran.cloud/v1/ayah/" + reference.make() + "/" + edition));

        ResponseEntity<ApiResponse<AyahDetail>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }
}
