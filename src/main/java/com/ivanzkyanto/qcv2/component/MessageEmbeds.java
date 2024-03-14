package com.ivanzkyanto.qcv2.component;

import com.ivanzkyanto.qcv2.model.Surah;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class MessageEmbeds {

    public static MessageEmbed surah(Surah surah, String imageFilename) {
        return createEmbedBuilder(surah)
                .setImage("attachment://%s".formatted(imageFilename))
                .build();
    }

    public static MessageEmbed surah(Surah surah) {
        return createEmbedBuilder(surah).build();
    }

    private static EmbedBuilder createEmbedBuilder(Surah surah) {
        return new EmbedBuilder()
                .setTitle(surah.getEnglishName())
                .setDescription(surah.getEnglishNameTranslation())
                .addField("Arabic", surah.getName(), false)
                .addField("Number", Integer.toString(surah.getNumber()), true)
                .addField("Revelation type", surah.getRevelationType(), true)
                .addField("Number of ayahs", Integer.toString(surah.getNumberOfAyahs()), true);
    }

}
