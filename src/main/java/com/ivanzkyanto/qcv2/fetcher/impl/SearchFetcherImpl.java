package com.ivanzkyanto.qcv2.fetcher.impl;

import com.ivanzkyanto.qcv2.configuration.properties.QuranEditionConfigurationProperties;
import com.ivanzkyanto.qcv2.fetcher.SearchFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.SearchEditionReference;
import com.ivanzkyanto.qcv2.model.SearchEditionReferenceMaker;
import com.ivanzkyanto.qcv2.model.SearchResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SearchFetcherImpl implements SearchFetcher {

    @NonNull
    private RestTemplate restTemplate;

    @NonNull
    private QuranEditionConfigurationProperties quranEditionConfigurationProperties;

    @Override
    public Optional<ApiResponse<SearchResult>> search(String keyword) {
        return search(keyword, null);
    }

    @Override
    public Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah) {
        var split = quranEditionConfigurationProperties.translate().split("\\.");
        return search(keyword, surah, new SearchEditionReference(split[0], split[1]));
    }

    @Override
    public Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah, SearchEditionReferenceMaker languageOrEdition) {
        String surahPath = (Objects.isNull(surah)) ? "all" : surah.toString();

        var url = String.format("/v1/search/%s/%s/%s", keyword, surahPath, languageOrEdition.make());
        var type = new ParameterizedTypeReference<ApiResponse<SearchResult>>() {};
        var response = restTemplate.exchange(url, HttpMethod.GET, null, type);

        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(204)) || Objects.isNull(response.getBody())) {
            return Optional.empty();
        }

        return Optional.of(response.getBody());
    }
}
