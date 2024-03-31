package io.github.stackpan.qurancord.fetcher;

import io.github.stackpan.qurancord.model.ApiResponse;
import io.github.stackpan.qurancord.model.SearchResult;
import io.github.stackpan.qurancord.model.SearchEditionReferenceMaker;

import java.util.Optional;

public interface SearchFetcher {

    Optional<ApiResponse<SearchResult>> search(String keyword);

    Optional<ApiResponse<SearchResult>> search(String keyword, String edition);

    Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah);

    Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah, SearchEditionReferenceMaker languageOrEdition);

    Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah, String edition);

}
