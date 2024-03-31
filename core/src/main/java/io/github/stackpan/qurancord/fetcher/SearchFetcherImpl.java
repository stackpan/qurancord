package io.github.stackpan.qurancord.fetcher;

import io.github.stackpan.qurancord.properties.QuranEditionConfigurationProperties;
import io.github.stackpan.qurancord.fetcher.provider.SearchFetcherProvider;
import io.github.stackpan.qurancord.model.ApiResponse;
import io.github.stackpan.qurancord.model.SearchEditionReferenceMaker;
import io.github.stackpan.qurancord.model.SearchResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SearchFetcherImpl implements SearchFetcher {

    @NonNull
    private SearchFetcherProvider searchFetcherProvider;

    @NonNull
    private QuranEditionConfigurationProperties quranEditionConfigurationProperties;

    @Override
    public Optional<ApiResponse<SearchResult>> search(String keyword) {
        return searchFetcherProvider.search(keyword, "all", quranEditionConfigurationProperties.translate());
    }

    @Override
    public Optional<ApiResponse<SearchResult>> search(String keyword, String edition) {
        return searchFetcherProvider.search(keyword, "all", edition);
    }

    @Override
    public Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah) {
        return search(keyword, surah, quranEditionConfigurationProperties.translate());
    }

    @Override
    public Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah, SearchEditionReferenceMaker languageOrEdition) {
        return search(keyword, surah, languageOrEdition.make());
    }

    @Override
    public Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah, String edition) {
        return searchFetcherProvider.search(keyword, surah.toString(), edition);
    }
}
