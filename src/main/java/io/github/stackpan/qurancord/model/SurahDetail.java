package io.github.stackpan.qurancord.model;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SurahDetail extends Surah {

    private List<Ayah> ayahs;

    private Edition edition;

}
