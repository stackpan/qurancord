package com.ivanzkyanto.qcv2.model;

import lombok.Data;

@Data
public class Ayah {

    private Integer number;

    private String text;

    private Integer numberInSurah;

    private Integer juz;

    private Integer manzil;

    private Integer page;

    private Integer ruku;

    private Integer hizbQuarter;

    private Object sajda;

    public AyahDetail toDetail(SurahDetail surah) {
        var result = new AyahDetail();
        result.setNumber(this.number);
        result.setText(this.text);
        result.setNumberInSurah(this.numberInSurah);
        result.setJuz(this.juz);
        result.setManzil(this.manzil);
        result.setPage(this.page);
        result.setRuku(this.ruku);
        result.setHizbQuarter(this.hizbQuarter);
        result.setSajda(this.sajda);
        result.setSurah(surah);
        result.setEdition(surah.getEdition());

        return result;
    }
    
}
