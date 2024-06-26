package io.github.stackpan.qurancord.component;

import com.freya02.botcommands.api.localization.Localizable;
import io.github.stackpan.qurancord.model.Surah;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class SurahEmbeds {

    public static MessageEmbed surah(Localizable event, Surah surah, String imageFilename) {
        return initializeBuilder(event, surah)
                .setImage("attachment://%s".formatted(imageFilename))
                .build();
    }

    public static MessageEmbed surah(Localizable event, Surah surah) {
        return initializeBuilder(event, surah).build();
    }

    private static EmbedBuilder initializeBuilder(Localizable event, Surah surah) {
        return new EmbedBuilder()
                .setTitle(surah.getEnglishName())
                .setDescription(surah.getEnglishNameTranslation())
                .addField(event.localize("_embed.surah.field.arabic"), surah.getName(), false)
                .addField(event.localize("_embed.surah.field.number"), Integer.toString(surah.getNumber()), true)
                .addField(event.localize("_embed.surah.field.revelation_type"), surah.getRevelationType(), true)
                .addField(event.localize("_embed.surah.field.number_of_ayahs"), Integer.toString(surah.getNumberOfAyahs()), true);
    }

}
