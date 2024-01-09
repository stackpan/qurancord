package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.model.Ayah;
import com.ivanzkyanto.qcv2.model.SearchResult;

public interface AyahService {

    Ayah get(Integer surahNumber, Integer ayahNumber);

    SearchResult search(String keyword);

    Ayah random();

    Ayah random(Integer surahNumber);

}
