package io.github.stackpan.qurancord.command;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.freya02.botcommands.api.application.slash.annotations.LongRange;
import com.freya02.botcommands.api.localization.annotations.LocalizationBundle;
import com.freya02.botcommands.api.prefixed.annotations.Category;
import io.github.stackpan.qurancord.component.AyahEmbeds;
import io.github.stackpan.qurancord.exception.AyahNotFoundException;
import io.github.stackpan.qurancord.exception.SurahNotFoundException;
import io.github.stackpan.qurancord.model.AyahDetailWithTranslate;
import io.github.stackpan.qurancord.service.AyahService;
import io.github.stackpan.qurancord.service.StorageService;
import io.github.stackpan.qurancord.util.AyahImageRendererKt;
import io.github.stackpan.qurancord.util.LoggerString;
import io.github.stackpan.qurancord.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.freya02.botcommands.api.localization.Localization.Entry.entry;

@Category("ayah")
@CommandMarker
@RequiredArgsConstructor
@LocalizationBundle("lang")
@Slf4j
public class AyahCommandController extends ApplicationCommand {

    private final AyahService ayahService;

    private final StorageService storageService;

    @JDASlashCommand(name = "ayah", subcommand = "find", scope = CommandScope.GLOBAL)
    public void find(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "surah") @LongRange(from = 1, to = 114) Integer surah,
            @AppOption(name = "number") Integer number,
            @AppOption(name = "image") @Nullable Boolean image
    ) {
        event.deferReply().queue();
        log.info(LoggerString.getLogGlobalCommand(event));

        try {
            var ayah = ayahService.getWithTranslate(surah, number);

            if (image != null && image) {
                var imagePath = getOrCreateImage(ayah);

                event.getHook()
                        .sendMessageEmbeds(AyahEmbeds.create(ayah, "ayah.png"))
                        .addFiles(FileUpload.fromData(imagePath, "ayah.png"))
                        .queue();
            } else {
                event.getHook().sendMessageEmbeds(AyahEmbeds.create(ayah)).queue();
            }
        } catch (AyahNotFoundException e) {
            event.getHook()
                    .sendMessage(event.localize(
                            "_exception.ayah_not_found",
                            entry("ayahNumber", number),
                            entry("surahNumber", surah))
                    )
                    .queue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @JDASlashCommand(name = "ayah", subcommand = "search", scope = CommandScope.GLOBAL)
    public void search(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "keyword") String keyword,
            @AppOption(name = "surah") @LongRange(from = 1, to = 114) @Nullable Integer surahNumber
    ) {
        event.deferReply().queue();
        log.info(LoggerString.getLogGlobalCommand(event));

        var sanitizedKeyword = StringUtils.sanitize(keyword);
        var results = surahNumber != null ? ayahService.search(sanitizedKeyword, surahNumber) : ayahService.search(sanitizedKeyword);

        results.ifPresentOrElse(
                searchResult -> {
                    var titleBuilder = new StringBuilder(event.localize("ayah.search.success1", entry("keyword", keyword)));
                    if (surahNumber != null) {
                        titleBuilder.append(event.localize("ayah.search.success2", entry("number", surahNumber)));
                    }

                    var embedBuilder = new EmbedBuilder().setTitle(titleBuilder.toString());

                    searchResult.getMatches().stream().limit(5)
                            .forEach(searchMatch -> {
                                var name = "%s %d:%d".formatted(
                                        searchMatch.getSurah().getEnglishName(),
                                        searchMatch.getSurah().getNumber(),
                                        searchMatch.getNumberInSurah()
                                );
                                var value = "\"%s\"".formatted(searchMatch.getText().length() > 999
                                        ? "%s...".formatted(searchMatch.getText().substring(0, 999))
                                        : searchMatch.getText());

                                embedBuilder.addField(name, value, false);
                            });

                    event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
                },
                () -> event.getHook()
                        .sendMessage(event.localize("_exception.ayah_search_no_results", entry("keyword", keyword)))
                        .queue()
        );
    }

    @JDASlashCommand(name = "ayah", subcommand = "random", scope = CommandScope.GLOBAL)
    public void random(
            @NotNull GlobalSlashEvent event,
            @AppOption(name = "surah") @LongRange(from = 1, to = 114) @Nullable Integer surahNumber,
            @AppOption(name = "image") @Nullable Boolean image
    ) {
        event.deferReply().queue();
        log.info(LoggerString.getLogGlobalCommand(event));

        try {
            var ayah = surahNumber != null ? ayahService.randomWithTranslate(surahNumber) : ayahService.randomWithTranslate();

            if (image != null && image) {
                var imagePath = getOrCreateImage(ayah);

                event.getHook()
                        .sendMessageEmbeds(AyahEmbeds.create(ayah, "ayah.png"))
                        .addFiles(FileUpload.fromData(imagePath, "ayah.png"))
                        .queue();
            } else {
                event.getHook().sendMessageEmbeds(AyahEmbeds.create(ayah)).queue();
            }
        } catch (SurahNotFoundException e) {
            assert surahNumber != null;
            event.getHook()
                    .sendMessage(event.localize("_exception.surah_not_found", entry("number", surahNumber)))
                    .queue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getOrCreateImage(AyahDetailWithTranslate ayah) throws IOException {
        var filename = "ayah:ed:%s_tl:%s_ref:%d:%d.png".formatted(
                ayah.getEdition().getIdentifier(),
                ayah.getTranslate().getIdentifier(),
                ayah.getSurah().getNumber(),
                ayah.getNumberInSurah()
        );
        var path = storageService.getFileAsPath(filename);

        if (!Files.exists(path)) {
            var image = AyahImageRendererKt.render(
                    ayah.getText(),
                    ayah.getTranslate().getText(),
                    ayah.getSurah().getEnglishName(),
                    ayah.getNumberInSurah()
            );
            storageService.writeImage(image, filename);
        }

        return path;
    }

}
