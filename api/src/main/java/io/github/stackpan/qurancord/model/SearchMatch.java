package io.github.stackpan.qurancord.model;

import lombok.Data;

@Data
public class SearchMatch {

    private Integer number;

    private String text;

    private Integer numberInSurah;

    private Edition edition;

    private Surah surah;

}
