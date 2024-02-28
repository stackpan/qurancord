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
        result.setNumber(this.getNumber());
        result.setText(this.getText());
        result.setNumberInSurah(this.getNumberInSurah());
        result.setJuz(this.getJuz());
        result.setManzil(this.getManzil());
        result.setPage(this.getPage());
        result.setRuku(this.getRuku());
        result.setHizbQuarter(this.getHizbQuarter());
        result.setSajda(this.getSajda());
        result.setSurah(surah);
        result.setEdition(surah.getEdition());

        return result;
    }
    
}
