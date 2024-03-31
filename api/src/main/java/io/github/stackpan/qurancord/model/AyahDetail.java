package io.github.stackpan.qurancord.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AyahDetail extends Ayah {

    private Edition edition;

    private Surah surah;

    public AyahDetailWithTranslate withTranslate(TranslateEdition translate) {
        var result = new AyahDetailWithTranslate();
        result.setNumber(this.getNumber());
        result.setText(this.getText());
        result.setNumberInSurah(this.getNumberInSurah());
        result.setJuz(this.getJuz());
        result.setManzil(this.getManzil());
        result.setPage(this.getPage());
        result.setRuku(this.getRuku());
        result.setHizbQuarter(this.getHizbQuarter());
        result.setSajda(this.getSajda());
        result.setSurah(this.surah);
        result.setEdition(this.edition);
        result.setTranslate(translate);

        return result;
    }

}
