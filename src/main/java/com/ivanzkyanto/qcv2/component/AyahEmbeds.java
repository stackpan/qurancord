package com.ivanzkyanto.qcv2.component;

import com.freya02.botcommands.api.localization.Localizable;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.model.AyahDetailWithTranslate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class AyahEmbeds {

    public static MessageEmbed create(Localizable event, AyahDetailWithTranslate ayah) {
        return createBuilder(ayah)
                .setDescription(ayah.getText())
                .addField(event.localize("_embed.ayah.field.translation"), ayah.getTranslate().getText(), false)
                .build();
    }

    public static MessageEmbed create(AyahDetailWithTranslate ayah, String imageFilename) {
        return createBuilder(ayah).setImage("attachment://%s".formatted(imageFilename)).build();

    }

    public static EmbedBuilder createBuilder(AyahDetail ayah) {
        return new EmbedBuilder()
                .setTitle("%s %d:%d".formatted(
                        ayah.getSurah().getEnglishName(),
                        ayah.getSurah().getNumber(),
                        ayah.getNumberInSurah()
                ));
    }

}
