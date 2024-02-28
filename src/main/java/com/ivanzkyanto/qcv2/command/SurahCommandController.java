package com.ivanzkyanto.qcv2.command;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.ivanzkyanto.qcv2.component.MessageEmbeds;
import com.ivanzkyanto.qcv2.service.SurahService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@CommandMarker
@RequiredArgsConstructor
public class SurahCommandController extends ApplicationCommand {

    private final SurahService surahService;

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

        surahService.get(number).ifPresentOrElse(
                surahDetail -> event.getHook()
                        .sendMessageEmbeds(MessageEmbeds.surah(surahDetail))
                        .queue(),
                () -> event.getHook()
                        .sendMessage("message.surah-not-found")
                        .queue()
        );
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
        event.getHook()
                .sendMessageEmbeds(MessageEmbeds.surah(surah))
                .queue();
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
                surah -> event.getHook()
                        .sendMessageEmbeds(MessageEmbeds.surah(surah))
                        .queue(),
                () -> event.getHook()
                        .sendMessage("message.surah-not-found")
                        .queue()
        );
    }

}
