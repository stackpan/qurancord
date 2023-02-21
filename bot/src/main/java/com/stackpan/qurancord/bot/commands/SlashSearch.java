package com.stackpan.qurancord.bot.commands;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.annotations.Optional;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.stackpan.qurancord.bot.Bot;
import com.stackpan.qurancord.bot.Replier;
import com.stackpan.qurancord.core.exception.SurahNotFoundException;
import com.stackpan.qurancord.core.service.QuranService;
import com.stackpan.qurancord.core.util.StringUtil;

@CommandMarker
public class SlashSearch extends ApplicationCommand {

    private final String name = "search";

    private final QuranService quranService = Bot.getQuranService();

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
        event.deferReply().queue();

        Replier.processReplier(event, e -> {
            if (StringUtil.checkUserInput(surahSearch)) throw new SurahNotFoundException();

            var data = StringUtil.isNumeric(surahSearch)
                    ? quranService.searchSurah(Integer.valueOf(surahSearch))
                    : quranService.searchSurah(surahSearch);

            Replier.replySurah(e, data);
        });
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
            ) Integer ayahSearch,
            @Optional @AppOption(
                    name = "without_image",
                    description = "Penanda untuk tidak mengirim gambar"
            ) Boolean withoutImage
    ) {
        final boolean noImage = withoutImage != null && withoutImage;

        event.deferReply().queue();

        Replier.processReplier(event, e -> {
            if (StringUtil.checkUserInput(surahSearch)) throw new SurahNotFoundException();

            var data = StringUtil.isNumeric(surahSearch)
                    ? quranService.searchAyah(Integer.valueOf(surahSearch), ayahSearch)
                    : quranService.searchAyah(surahSearch, ayahSearch);

            if (noImage) Replier.replyAyah(e, data);
            else Replier.sendAyahImage(e, data);
        });
    }

}
