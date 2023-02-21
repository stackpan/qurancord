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
    public void onRandomAyahAny(
            GlobalSlashEvent event,
            @Optional @AppOption(
                    name = "without_image",
                    description = "Penanda untuk tidak mengirim gambar"
            ) Boolean withoutImage) {
        if (withoutImage == null) withoutImage = false;

        event.deferReply().queue();

        if (withoutImage) Replier.replyAyah(event, quranService.getRandomAyah());
        else Replier.sendAyahImage(event, quranService.getRandomAyah());
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
            ) String surah,
            @Optional @AppOption(
                    name = "without_image",
                    description = "Penanda untuk tidak mengirim gambar"
            ) Boolean withoutImage) {
        final boolean noImage = withoutImage != null && withoutImage;

        event.deferReply().queue();

        Replier.processReplier(event, e -> {
            if (StringUtil.checkUserInput(surah)) throw new SurahNotFoundException();

            var data = StringUtil.isNumeric(surah)
                    ? quranService.getRandomAyah(Integer.valueOf(surah))
                    : quranService.getRandomAyah(surah);

            if (noImage) Replier.replyAyah(e, data);
            else Replier.sendAyahImage(e, data);
        });
    }
}
