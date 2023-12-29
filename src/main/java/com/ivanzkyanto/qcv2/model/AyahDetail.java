package com.ivanzkyanto.qcv2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AyahDetail extends Ayah {

    private Edition edition;

    private Surah surah;

}
