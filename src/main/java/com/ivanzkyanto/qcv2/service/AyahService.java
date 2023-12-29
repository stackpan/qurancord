package com.ivanzkyanto.qcv2.service;

import com.ivanzkyanto.qcv2.model.Ayah;

public interface AyahService {

    Ayah get(int surahNumber, int ayahNumber);

    Ayah search(String keyword);

    Ayah random();

    Ayah random(int ayah);

}
