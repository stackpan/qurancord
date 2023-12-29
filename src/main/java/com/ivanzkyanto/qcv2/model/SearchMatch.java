package com.ivanzkyanto.qcv2.model;

import lombok.Data;

@Data
public class SearchMatch {

    private Integer number;

    private String text;

    private Integer numberInSurah;

    private Edition edition;

    private Surah surah;

}
