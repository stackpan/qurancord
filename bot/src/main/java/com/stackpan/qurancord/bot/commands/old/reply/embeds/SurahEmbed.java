package com.stackpan.qurancord.bot.commands.old.reply.embeds;

import com.stackpan.qurancord.core.entity.Surah;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class SurahEmbed {

    public static MessageEmbed show(Surah surah) {
        return new EmbedBuilder()
                .setTitle(surah.latinName())
                .setDescription(surah.meaning())
                .addField("Surah ke", Integer.toString(surah.number()), false)
                .addField("Arabic", surah.arabicName(), true)
                .addField("Tempat turun", surah.revelationType().toString(), true)
                .addField("Jumlah ayat", Integer.toString(surah.ayahCount()), true)
                .build();
    }

}
