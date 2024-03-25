package io.github.stackpan.qurancord.model;

import lombok.Data;

@Data
public class Surah {

    private Integer number;

    private String name;

    private String englishName;

    private String englishNameTranslation;

    private String revelationType;

    private Integer numberOfAyahs;

}
