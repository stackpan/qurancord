package com.stackpan.qurancord.bot.commands;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;

@CommandMarker
public class SlashRandom extends ApplicationCommand {

    private final String name = "random";

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            subcommand = "surah",
            description = "Meminta surah Al-Quran secara acak"
    )
    public void onRandomSurah(GlobalSlashEvent event) {
        // todo: add onRandomSurah reply
    }

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            group = "ayah",
            subcommand = "any",
            description = "Meminta ayat Al-Quran di surah apapun"
    )
    public void onRandomAyahAny(GlobalSlashEvent event) {
        // todo: add onRandomAyahAny reply
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
        // todo: add onRandomAyahSurahName reply
    }

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            group = "ayah",
            subcommand = "image",
            description = "Meminta ayat Al-Quran di surah apapun secara acak dengan gambar"
    )
    public void onRandomAyahImage(GlobalSlashEvent event) {
        // todo: add onRandomAyahImage reply
    }

}
