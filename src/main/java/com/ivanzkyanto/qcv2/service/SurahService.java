package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.model.SurahDetail;

public interface SurahService {

    SurahDetail get(Integer number);

    Surah search(String keyword);

    SurahDetail random();

}
