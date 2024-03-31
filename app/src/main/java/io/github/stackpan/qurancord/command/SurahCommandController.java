package io.github.stackpan.qurancord.command;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.freya02.botcommands.api.application.slash.annotations.LongRange;
import com.freya02.botcommands.api.application.slash.autocomplete.AutocompletionMode;
import com.freya02.botcommands.api.application.slash.autocomplete.annotations.AutocompletionHandler;
import com.freya02.botcommands.api.localization.Localizable;
import com.freya02.botcommands.api.localization.annotations.LocalizationBundle;
import io.github.stackpan.qurancord.component.SurahEmbeds;
import io.github.stackpan.qurancord.exception.SurahNotFoundException;
import io.github.stackpan.qurancord.model.Surah;
import io.github.stackpan.qurancord.service.StorageService;
import io.github.stackpan.qurancord.service.SurahService;
import io.github.stackpan.qurancord.util.LoggerString;
import io.github.stackpan.qurancord.util.SurahImageRendererKt;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static com.freya02.botcommands.api.localization.Localization.Entry.entry;

@Category("surah")
@CommandMarker
@RequiredArgsConstructor
@LocalizationBundle("lang")
@Slf4j
public class SurahCommandController extends ApplicationCommand {

    private final SurahService surahService;

    private final StorageService storageService;

    @JDASlashCommand(name = "surah", subcommand = "find", scope = CommandScope.GLOBAL)
    public void find(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "number") @LongRange(from = 1, to = 114) Integer number
    ) {
        event.deferReply().queue();
        log.info(LoggerString.getLogGlobalCommand(event));

        try {
            var surah = surahService.get(number);
            var path = getOrCreateImage(surah);

            event.getHook()
                    .sendMessageEmbeds(SurahEmbeds.surah(event, surah, "surah.png"))
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
        log.info(LoggerString.getLogGlobalCommand(event));

        var surah = surahService.random();
        try {
            var path = getOrCreateImage(surah);

            event.getHook()
                    .sendMessageEmbeds(SurahEmbeds.surah(event, surah, "surah.png"))
                    .addFiles(FileUpload.fromData(path, "surah.png"))
                    .queue();
        } catch (IOException e) {
            event.getHook().sendMessage(((Localizable) event).localize("_exception.error")).queue();
        }
    }

    @JDASlashCommand(name = "surah", subcommand = "search", scope = CommandScope.GLOBAL)

    public void search(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "keyword", autocomplete = "surahNames") String keyword
    ) {
        event.deferReply().queue();
        log.info(LoggerString.getLogGlobalCommand(event));

        surahService.search(keyword).ifPresentOrElse(
                surah -> {
                    try {
                        var path = getOrCreateImage(surah);

                        event.getHook()
                                .sendMessage(event.localize("surah.search.success", entry("keyword", keyword)))
                                .addEmbeds(SurahEmbeds.surah(event, surah, "surah.png"))
                                .addFiles(FileUpload.fromData(path, "surah.png"))
                                .queue();
                    } catch (IOException e) {
                        event.getHook().sendMessage(((Localizable) event).localize("_exception.error")).queue();
                    }
                },
                () -> event.getHook()
                        .sendMessage(event.localize("_exception.surah_search_not_found", entry("keyword", keyword)))
                        .queue()
        );
    }

    @AutocompletionHandler(name = "surahNames", mode = AutocompletionMode.FUZZY)
    public List<Command.Choice> getSurahNameChoices(CommandAutoCompleteInteractionEvent event) {
        return surahService.getAllNames().stream()
                .map(name -> new Command.Choice(name, name))
                .collect(Collectors.toList());
    }

    private Path getOrCreateImage(Surah surah) throws IOException {
        var filename = "surah:%d.png".formatted(surah.getNumber());
        var path = storageService.getFileAsPath(filename);

        if (!Files.exists(path)) {
            storageService.writeImage(SurahImageRendererKt.render(surah.getName()), filename);
        }

        return path;
    }

}
