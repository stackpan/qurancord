package com.ivanzkyanto.qcv2.command;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.ivanzkyanto.qcv2.component.MessageEmbeds;
import com.ivanzkyanto.qcv2.exception.SurahNotFoundException;
import com.ivanzkyanto.qcv2.model.Surah;
import com.ivanzkyanto.qcv2.service.StorageService;
import com.ivanzkyanto.qcv2.service.SurahService;
import com.ivanzkyanto.qcv2.util.SurahImageRendererKt;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;

@CommandMarker
@RequiredArgsConstructor
public class SurahCommandController extends ApplicationCommand {

    private final SurahService surahService;

    private final StorageService storageService;

    @JDASlashCommand(
            name = "surah",
            subcommand = "find",
            scope = CommandScope.GLOBAL,
            description = "command.surah.find.description"
    )
    public void find(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "number", description = "option.surah.find.number.description") Integer number
    ) {
        event.deferReply().queue();

        try {
            var surah = surahService.get(number);
            sendEmbedWithImage(event, surah);
        } catch (SurahNotFoundException e) {
            event.getHook()
                    .sendMessage("message.surah-not-found")
                    .queue();
        }
    }

    @JDASlashCommand(
            name = "surah",
            subcommand = "random",
            scope = CommandScope.GLOBAL,
            description = "command.surah.random.description"
    )
    public void random(@NotNull GlobalSlashEvent event) {
        event.deferReply().queue();
        var surah = surahService.random();
        sendEmbedWithImage(event, surah);
    }

    @JDASlashCommand(
            name = "surah",
            subcommand = "search",
            scope = CommandScope.GLOBAL,
            description = "command.surah.search.description"
    )
    public void search(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "keyword", description = "option.surah.search.keyword.description") String keyword
    ) {
        event.deferReply().queue();
        surahService.search(keyword).ifPresentOrElse(
                surah -> sendEmbedWithImage(event, surah),
                () -> event.getHook()
                        .sendMessage("message.surah-not-found")
                        .queue()
        );
    }

    private void sendEmbedWithImage(@NotNull SlashCommandInteractionEvent event, Surah surah) {
        var surahImageFilename = "surah:%d.png".formatted(surah.getNumber());
        var surahImagePath = storageService.getFileAsPath(surahImageFilename);

        if (!Files.exists(surahImagePath)) {
            try {
                storageService.writeImage(SurahImageRendererKt.render(surah.getName()), surahImageFilename);
            } catch (IOException e) {
                event.getHook().sendMessage("message.error").queue();
            }
        }

        event.getHook()
                .sendMessageEmbeds(MessageEmbeds.surah(surah, "surah.png"))
                .addFiles(FileUpload.fromData(surahImagePath, "surah.png"))
                .queue();
    }

}
