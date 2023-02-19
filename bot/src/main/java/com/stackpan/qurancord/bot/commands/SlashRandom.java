package com.stackpan.qurancord.bot.commands;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.stackpan.qurancord.bot.Bot;
import com.stackpan.qurancord.bot.Replier;
import com.stackpan.qurancord.core.service.QuranService;
import com.stackpan.qurancord.core.util.StringUtil;

@CommandMarker
public class SlashRandom extends ApplicationCommand {

    private final String name = "random";
    private final QuranService quranService = Bot.getQuranService();

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            subcommand = "surah",
            description = "Meminta surah Al-Quran secara acak"
    )
    public void onRandomSurah(GlobalSlashEvent event) {
        event.deferReply().queue();
        Replier.replySurah(event, quranService.getRandomSurah());
    }

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            group = "ayah",
            subcommand = "any",
            description = "Meminta ayat Al-Quran di surah apapun"
    )
    public void onRandomAyahAny(GlobalSlashEvent event) {
        event.deferReply().queue();
        Replier.replyAyah(event, quranService.getRandomAyah());
    }

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            group = "ayah",
            subcommand = "from-surah",
            description = "Meminta ayat Al-Quran berdasarkan surah"
    )
    public void onRandomAyahSurahName(
            GlobalSlashEvent event,
            @AppOption(
                    name = "surah",
                    description = "Nama surah atau nomor surah"
            ) String surah) {
        event.deferReply().queue();
        if (StringUtil.isNumeric(surah)) {
            Replier.replyAyah(event, Integer.valueOf(surah), quranService::getRandomAyah);
        } else {
            Replier.replyAyah(event, surah, quranService::getRandomAyah);
        }
    }

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            group = "ayah",
            subcommand = "image",
            description = "Meminta ayat Al-Quran di surah apapun secara acak dengan gambar"
    )
    public void onRandomAyahImage(GlobalSlashEvent event) {
        event.deferReply().queue();
        Replier.sendAyahImage(event, quranService.getRandomAyah());
    }

}
