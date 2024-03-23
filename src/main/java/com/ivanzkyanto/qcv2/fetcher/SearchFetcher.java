package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.SearchResult;
import com.ivanzkyanto.qcv2.model.SearchEditionReferenceMaker;

import java.util.Optional;

public interface SearchFetcher {

    Optional<ApiResponse<SearchResult>> search(String keyword);

    Optional<ApiResponse<SearchResult>> search(String keyword, String edition);

    Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah);

    Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah, SearchEditionReferenceMaker languageOrEdition);

    Optional<ApiResponse<SearchResult>> search(String keyword, Integer surah, String edition);

}
