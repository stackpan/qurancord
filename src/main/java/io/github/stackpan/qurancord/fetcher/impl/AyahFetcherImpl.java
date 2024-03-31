package io.github.stackpan.qurancord.fetcher.impl;

import io.github.stackpan.qurancord.exception.AyahNotFoundException;
import io.github.stackpan.qurancord.fetcher.AyahFetcher;
import io.github.stackpan.qurancord.model.ApiResponse;
import io.github.stackpan.qurancord.model.AyahDetail;
import io.github.stackpan.qurancord.model.AyahReferenceMaker;
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
import java.util.Arrays;
import java.util.stream.Collectors;

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

    @Override
    public ApiResponse<AyahDetail[]> get(AyahReferenceMaker reference, String... editions) throws AyahNotFoundException {
        var joinedEdition = String.join(",", editions);
        try {
            var url = String.format("/v1/ayah/%s/editions/%s", reference.make(), joinedEdition);
            var type = new ParameterizedTypeReference<ApiResponse<AyahDetail[]>>() {};
            var response = restTemplate.exchange(url, HttpMethod.GET, null, type);

            return response.getBody();
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                throw new AyahNotFoundException(reference.make(), joinedEdition);
            }
            throw e;
        }
    }
}
