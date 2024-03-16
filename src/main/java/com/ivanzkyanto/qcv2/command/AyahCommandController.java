package com.ivanzkyanto.qcv2.command;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.ivanzkyanto.qcv2.exception.AyahNotFoundException;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.AyahDetail;
import com.ivanzkyanto.qcv2.service.AyahService;
import com.ivanzkyanto.qcv2.service.StorageService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@CommandMarker
@RequiredArgsConstructor
public class AyahCommandController extends ApplicationCommand {

    private final AyahService ayahService;

    private final StorageService storageService;

    @JDASlashCommand(
            name = "ayah",
            subcommand = "find",
            scope = CommandScope.GLOBAL,
            description = "command.ayah.find.description"
    )
    public void find(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "surah", description = "option.ayah.find.surah.description") Integer surah,
            @AppOption(name = "number", description = "option.ayah.find.number.description") Integer number
    ) {
        event.deferReply().queue();

        try {
            var ayah = ayahService.get(surah, number);

            event.getHook()
                    .sendMessageFormat("%s \n**Q.S. %s: %d**", ayah.getText(), ayah.getSurah().getEnglishName(), ayah.getNumber())
                    .queue();
        } catch (AyahNotFoundException e) {
            event.getHook()
                    .sendMessage("message.ayah-not-found")
                    .queue();
        }
    }

    @JDASlashCommand(
            name = "ayah",
            subcommand = "search",
            scope = CommandScope.GLOBAL,
            description = "command.ayah.search.description"
    )
    public void search(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "keyword", description = "option.ayah.search.keyword.description") String keyword
    ) {
        event.deferReply().queue();

        ayahService.search(keyword).ifPresentOrElse(
                searchResult -> {
                    var ayah = searchResult.getMatches().get(0);

                    event.getHook()
                        .sendMessageEmbeds(new EmbedBuilder()
                                .setTitle(String.format("Found %s results", searchResult.getCount()))
                                .setDescription(String.format("%s \n**Q.S. %s: %d**", ayah.getText(), ayah.getSurah().getEnglishName(), ayah.getNumber()))
                                .build())
                        .queue();
                },
                () -> event.getHook()
                        .sendMessage("message.search-not-found")
                        .queue()
        );
    }

    @JDASlashCommand(
            name = "ayah",
            group = "random",
            subcommand = "any",
            scope = CommandScope.GLOBAL,
            description = "commmand.ayah.random.description"
    )
    public void random(@NotNull GlobalSlashEvent event) {
        event.deferReply().queue();

        var ayah = ayahService.random();

        event.getHook()
                .sendMessageFormat("%s \n**Q.S. %s: %d**", ayah.getText(), ayah.getSurah().getEnglishName(), ayah.getNumber())
                .queue();
    }

    @JDASlashCommand(
            name = "ayah",
            group = "random",
            subcommand = "in-surah",
            scope = CommandScope.GLOBAL,
            description = "commmand.ayah.random.description"
    )
    public void randomInSurah(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "in_surah", description = "option.ayah.search.in_surah.description") Integer number
    ) {
        event.deferReply().queue();

        try {
            var ayah = ayahService.random(number);

            event.getHook()
                    .sendMessageFormat("%s \n**Q.S. %s: %d**", ayah.getText(), ayah.getSurah().getEnglishName(), ayah.getNumber())
                    .queue();
        } catch (SurahNotFoundException e) {
            event.getHook()
                    .sendMessage("message.surah-not-found")
                    .queue();
        }
    }

}
