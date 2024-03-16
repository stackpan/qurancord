package com.ivanzkyanto.qcv2.command;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.freya02.botcommands.api.application.slash.annotations.LongRange;
import com.ivanzkyanto.qcv2.exception.AyahNotFoundException;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.AyahDetailWithTranslate;
import com.ivanzkyanto.qcv2.service.AyahService;
import com.ivanzkyanto.qcv2.service.StorageService;
import com.ivanzkyanto.qcv2.util.AyahImageRendererKt;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;

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
            @NotNull
            GlobalSlashEvent event,

            @AppOption(name = "surah", description = "option.ayah.find.surah.description")
            @LongRange(from = 1, to = 114)
            Integer surah,

            @AppOption(name = "number", description = "option.ayah.find.number.description")
            Integer number
    ) {
        event.deferReply().queue();

        try {
            var ayah = ayahService.getWithTranslate(surah, number);
            sendEmbedWithImage(event, ayah);
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
            @NotNull
            GlobalSlashEvent event,

            @AppOption(name = "keyword", description = "option.ayah.search.keyword.description")
            String keyword,

            @AppOption(name = "surah_number", description = "option.ayah.search.surah.description")
            @LongRange(from = 1, to = 114)
            @Nullable
            Integer surahNumber
    ) {
        event.deferReply().queue();

        var results = surahNumber != null ? ayahService.search(keyword, surahNumber) : ayahService.search(keyword);

        results.ifPresentOrElse(
                searchResult -> {
                    var titleBuilder = new StringBuilder("Top ayah search for keyword \"%s\"".formatted(keyword));
                    if (surahNumber != null) {
                        titleBuilder.append(" in surah number %d".formatted(surahNumber));
                    }

                    var embedBuilder = new EmbedBuilder().setTitle(titleBuilder.toString());

                    searchResult.getMatches().stream().limit(5)
                            .forEach(searchMatch -> {
                                var name = "%s %d:%d".formatted(searchMatch.getSurah().getEnglishName(), searchMatch.getSurah().getNumber(), searchMatch.getNumberInSurah());
                                var value = "\"%s\"".formatted(searchMatch.getText().length() > 999
                                        ? "%s...".formatted(searchMatch.getText().substring(0, 999))
                                        : searchMatch.getText());

                                embedBuilder.addField(name, value, false);
                            });

                    event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
                },
                () -> event.getHook()
                        .sendMessage("message.search-not-found")
                        .queue()
        );
    }

    @JDASlashCommand(
            name = "ayah",
            subcommand = "random",
            scope = CommandScope.GLOBAL,
            description = "commmand.ayah.random.description"
    )
    public void random(
            @NotNull
            GlobalSlashEvent event,

            @AppOption(name = "in_surah", description = "option.ayah.random.in_surah.description")
            @LongRange(from = 1, to = 114)
            @Nullable
            Integer surahNumber
    ) {
        event.deferReply().queue();

        try {
            var ayah = surahNumber != null ? ayahService.randomWithTranslate(surahNumber) : ayahService.randomWithTranslate();
            sendEmbedWithImage(event, ayah);
        } catch (SurahNotFoundException e) {
            event.getHook()
                    .sendMessage("message.surah-not-found")
                    .queue();
        }
    }

    private void sendEmbedWithImage(@NotNull GlobalSlashEvent event, AyahDetailWithTranslate ayah) {
        var ayahImageFilename = "ayah:ed:%s_tl:%s_ref:%d:%d.png".formatted(
                ayah.getEdition().getIdentifier(),
                ayah.getTranslate().getIdentifier(),
                ayah.getSurah().getNumber(),
                ayah.getNumberInSurah()
        );
        var ayahImagePath = storageService.getFileAsPath(ayahImageFilename);

        if (!Files.exists(ayahImagePath)) {
            try {
                var image = AyahImageRendererKt.render(
                        ayah.getText(),
                        ayah.getTranslate().getText(),
                        ayah.getSurah().getEnglishName(),
                        ayah.getNumberInSurah()
                );
                storageService.writeImage(image, ayahImageFilename);
            } catch (IOException e) {
                event.getHook().sendMessage("message.error").queue();
            }
        }

        event.getHook()
                .sendFiles(FileUpload.fromData(ayahImagePath))
                .queue();
    }

}
