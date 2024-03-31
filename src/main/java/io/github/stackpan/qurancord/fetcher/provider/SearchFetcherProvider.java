package io.github.stackpan.qurancord.fetcher.provider;

import io.github.stackpan.qurancord.model.ApiResponse;
import io.github.stackpan.qurancord.model.SearchResult;
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
public class SearchFetcherProvider {

    @NonNull
    private RestTemplate restTemplate;

    public Optional<ApiResponse<SearchResult>> search(String keyword, String surah, String edition) {
        var url = String.format("/v1/search/%s/%s/%s", keyword, surah, edition);

        var type = new ParameterizedTypeReference<ApiResponse<SearchResult>>() {};
        var response = restTemplate.exchange(url, HttpMethod.GET, null, type);

        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(204)) || Objects.isNull(response.getBody())) {
            return Optional.empty();
        }

        return Optional.of(response.getBody());
    }

}
