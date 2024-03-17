package com.ivanzkyanto.qcv2.model;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SurahDetail extends Surah {

    private List<Ayah> ayahs;

    private Edition edition;

}
