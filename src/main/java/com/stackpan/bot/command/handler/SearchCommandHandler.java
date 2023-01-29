package com.stackpan.bot.command.handler;

import com.stackpan.bot.reply.AyahReply;
import com.stackpan.bot.reply.SurahReply;
import com.stackpan.service.QuranService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SearchCommandHandler extends ListenerAdapter {

    private final QuranService quranService;

    public SearchCommandHandler(QuranService quranService) {
        this.quranService = quranService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        // todo: make it more readable and maintainable
        // todo: prevent code repetition
        // todo: beautify the reply using Discord Embeds

        SurahReply surahReply = new SurahReply(event);
        AyahReply ayahReply = new AyahReply(event);

        if (event.getName().equals("search")) {
            if (Objects.equals(event.getSubcommandGroup(), "surah")) {
                if (Objects.requireNonNull(event.getSubcommandName()).equals("name")) {
                    event.deferReply().queue();
                    var searchName = Objects.requireNonNull(event.getOption("search_name")).getAsString();
                    surahReply.send(quranService.searchSurah(searchName));
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("number")) {
                    event.deferReply().queue();
                    var searchNumber = Objects.requireNonNull(event.getOption("search_number")).getAsInt();
                    surahReply.send(quranService.searchSurah(searchNumber));
                }
            }

            if (Objects.equals(event.getSubcommandGroup(), "ayah")) {
                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-name")) {
                    event.deferReply().queue();
                    var searchName = Objects.requireNonNull(event.getOption("surah_name")).getAsString();
                    var ayahNumber = Objects.requireNonNull(event.getOption("ayah_number")).getAsInt();
                    ayahReply.send(quranService.searchAyah(searchName, ayahNumber));
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-number")) {
                    event.deferReply().queue();
                    var searchNumber = Objects.requireNonNull(event.getOption("surah_number")).getAsInt();
                    var ayahNumber = Objects.requireNonNull(event.getOption("ayah_number")).getAsInt();
                    ayahReply.send(quranService.searchAyah(searchNumber, ayahNumber));
                }
            }
        }
    }
}
