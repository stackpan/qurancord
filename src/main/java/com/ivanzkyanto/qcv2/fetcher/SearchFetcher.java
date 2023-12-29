package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.SearchResult;
import com.ivanzkyanto.qcv2.util.SearchEditionReferenceMaker;

public interface SearchFetcher {

    ApiResponse<SearchResult> search(String keyword);

    ApiResponse<SearchResult> search(String keyword, Integer surah);
    
    ApiResponse<SearchResult> search(String keyword, Integer surah, SearchEditionReferenceMaker languageOrEdition);

}
