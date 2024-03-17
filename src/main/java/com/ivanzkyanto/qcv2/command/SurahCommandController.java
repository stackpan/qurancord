package com.ivanzkyanto.qcv2.command;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.freya02.botcommands.api.application.slash.annotations.LongRange;
import com.freya02.botcommands.api.localization.Localizable;
import com.freya02.botcommands.api.localization.annotations.LocalizationBundle;
import com.ivanzkyanto.qcv2.component.MessageEmbeds;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.service.StorageService;
import com.ivanzkyanto.qcv2.service.SurahService;
import com.ivanzkyanto.qcv2.util.SurahImageRendererKt;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.freya02.botcommands.api.localization.Localization.Entry.entry;

@CommandMarker
@RequiredArgsConstructor
@LocalizationBundle("lang")
public class SurahCommandController extends ApplicationCommand {

    private final SurahService surahService;

    private final StorageService storageService;

    @JDASlashCommand(name = "surah", subcommand = "find", scope = CommandScope.GLOBAL)
    public void find(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "number") @LongRange(from = 1, to = 114) Integer number
    ) {
        event.deferReply().queue();

        try {
            var surah = surahService.get(number);
            var path = findOrCreateImage(surah);

            event.getHook()
                    .sendMessageEmbeds(MessageEmbeds.surah(event, surah, "surah.png"))
                    .addFiles(FileUpload.fromData(path, "surah.png"))
                    .queue();
        } catch (SurahNotFoundException e) {
            event.getHook()
                    .sendMessage(event.localize("_exception.surah_not_found", entry("number", number)))
                    .queue();
        } catch (IOException e) {
            event.getHook().sendMessage(((Localizable) event).localize("_exception.error")).queue();
        }
    }

    @JDASlashCommand(name = "surah", subcommand = "random", scope = CommandScope.GLOBAL)
    public void random(@NotNull GlobalSlashEvent event) {
        event.deferReply().queue();
        var surah = surahService.random();
        try {
            var path = findOrCreateImage(surah);

            event.getHook()
                    .sendMessageEmbeds(MessageEmbeds.surah(event, surah, "surah.png"))
                    .addFiles(FileUpload.fromData(path, "surah.png"))
                    .queue();
        } catch (IOException e) {
            event.getHook().sendMessage(((Localizable) event).localize("_exception.error")).queue();
        }
    }

    @JDASlashCommand(name = "surah", subcommand = "search", scope = CommandScope.GLOBAL)
    public void search(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "keyword") String keyword
    ) {
        event.deferReply().queue();
        surahService.search(keyword).ifPresentOrElse(
                surah -> {
                    try {
                        var path = findOrCreateImage(surah);

                        event.getHook()
                                .sendMessage(event.localize("surah.search.success", entry("keyword", keyword)))
                                .addEmbeds(MessageEmbeds.surah(event, surah, "surah.png"))
                                .addFiles(FileUpload.fromData(path, "surah.png"))
                                .queue();
                    } catch (IOException e) {
                        event.getHook().sendMessage(((Localizable) event).localize("_exception.error")).queue();
                    }
                },
                () -> event.getHook()
                        .sendMessage(event.localize("_exception.surah_not_found", entry("number", keyword)))
                        .queue()
        );
    }

    private Path findOrCreateImage(Surah surah) throws IOException {
        var surahImageFilename = "surah:%d.png".formatted(surah.getNumber());
        var surahImagePath = storageService.getFileAsPath(surahImageFilename);

        if (!Files.exists(surahImagePath)) {
            storageService.writeImage(SurahImageRendererKt.render(surah.getName()), surahImageFilename);
        }

        return surahImagePath;
    }

}
