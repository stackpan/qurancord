package com.stackpan.qurancord.bot.commands;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;

@CommandMarker
public class SlashSearch extends ApplicationCommand {

    private final String name = "search";

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            subcommand = "surah",
            description = "Mencari surah Al-Quran"
    )
    public void onSearchSurah(
            GlobalSlashEvent event,
            @AppOption(
                    name = "surah_search",
                    description = "Nama surah atau nomor surah yang ingin dicari"
            ) String surahSearch) {
        // todo: add onSearchSurah reply
    }

    @JDASlashCommand(
            name = name,
            scope = CommandScope.GLOBAL,
            subcommand = "ayah",
            description = "Mencari ayat Al-Quran"
    )
    public void onSearchAyah(
            GlobalSlashEvent event,
            @AppOption(
                    name = "surah_search",
                    description = "Nama surah atau nomor surah"
            ) String surahSearch,
            @AppOption(
                    name = "ayah_search",
                    description = "Ayat yang ingin dicari"
            ) Integer ayahSearch
    ) {
        // todo: add onSearchSurah reply
    }

}
