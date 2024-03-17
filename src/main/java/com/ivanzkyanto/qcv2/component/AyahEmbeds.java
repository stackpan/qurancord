package com.ivanzkyanto.qcv2.component;

import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.model.AyahDetailWithTranslate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class AyahEmbeds {

    public static MessageEmbed create(AyahDetailWithTranslate ayah) {
        return initializeBuilder(ayah)
                .setDescription(ayah.getText())
                .setFooter(ayah.getTranslate().getText())
                .build();
    }

    public static MessageEmbed create(AyahDetailWithTranslate ayah, String imageFilename) {
        return initializeBuilder(ayah).setImage("attachment://%s".formatted(imageFilename)).build();

    }

    public static EmbedBuilder initializeBuilder(AyahDetail ayah) {
        return new EmbedBuilder()
                .setTitle("%s %d:%d".formatted(
                        ayah.getSurah().getEnglishName(),
                        ayah.getSurah().getNumber(),
                        ayah.getNumberInSurah()
                ));
    }

}
