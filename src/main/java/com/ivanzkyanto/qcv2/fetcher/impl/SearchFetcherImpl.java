package com.ivanzkyanto.qcv2.fetcher.impl;

import com.ivanzkyanto.qcv2.fetcher.SearchFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.SearchEditionReference;
import com.ivanzkyanto.qcv2.model.SearchResult;
import com.ivanzkyanto.qcv2.model.SearchEditionReferenceMaker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SearchFetcherImpl implements SearchFetcher {

    @NonNull
    private RestTemplate restTemplate;

    @Override
    public ApiResponse<SearchResult> search(String keyword) {
        return search(keyword, null);
    }

    @Override
    public ApiResponse<SearchResult> search(String keyword, Integer surah) {
        return search(keyword, surah, new SearchEditionReference("en", "asad"));
    }

    @Override
    public ApiResponse<SearchResult> search(String keyword, Integer surah, SearchEditionReferenceMaker languageOrEdition) {
        String surahPath = (Objects.isNull(surah)) ? "all" : surah.toString();

        URI uri = URI.create("http://api.alquran.cloud/v1/search/" + keyword + "/" + surahPath + "/" + languageOrEdition.make());

        RequestEntity<Void> request = new RequestEntity<>(HttpMethod.GET, uri);

        ResponseEntity<ApiResponse<SearchResult>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }
}
