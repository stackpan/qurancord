package io.github.stackpan.qurancord.fetcher;

import io.github.stackpan.qurancord.exception.AyahNotFoundException;
import io.github.stackpan.qurancord.model.ApiResponse;
import io.github.stackpan.qurancord.model.AyahDetail;
import io.github.stackpan.qurancord.model.AyahReferenceMaker;

public interface AyahFetcher {

    ApiResponse<AyahDetail> get(AyahReferenceMaker reference) throws AyahNotFoundException;

    ApiResponse<AyahDetail> get(AyahReferenceMaker reference, String edition) throws AyahNotFoundException;

    ApiResponse<AyahDetail[]> get(AyahReferenceMaker reference, String... editions) throws AyahNotFoundException;
}
