package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.model.Surah;

public interface SurahService {

    Surah get(int number);

    Surah search(String name);

    Surah random();

}
