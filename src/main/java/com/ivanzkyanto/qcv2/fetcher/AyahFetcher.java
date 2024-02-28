package com.ivanzkyanto.qcv2.fetcher;

import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.model.AyahReferenceMaker;

public interface AyahFetcher {

    ApiResponse<AyahDetail> get(AyahReferenceMaker reference);

    ApiResponse<AyahDetail> get(AyahReferenceMaker reference, String edition);

}
