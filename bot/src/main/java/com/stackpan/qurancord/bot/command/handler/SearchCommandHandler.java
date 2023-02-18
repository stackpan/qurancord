package com.stackpan.qurancord.bot.command.handler;

import com.stackpan.qurancord.bot.command.reply.AyahReply;
import com.stackpan.qurancord.bot.command.reply.SurahReply;
import com.stackpan.qurancord.core.service.QuranService;
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
                    surahReply.send(searchName, quranService::searchSurah);
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("number")) {
                    event.deferReply().queue();
                    var searchNumber = Objects.requireNonNull(event.getOption("search_number")).getAsInt();
                    surahReply.send(searchNumber, quranService::searchSurah);
                }
            }

            if (Objects.equals(event.getSubcommandGroup(), "ayah")) {
                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-name")) {
                    event.deferReply().queue();
                    var searchName = Objects.requireNonNull(event.getOption("surah_name")).getAsString();
                    var ayahNumber = Objects.requireNonNull(event.getOption("ayah_number")).getAsInt();
                    ayahReply.send(searchName, ayahNumber, quranService::searchAyah);
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-number")) {
                    event.deferReply().queue();
                    var searchNumber = Objects.requireNonNull(event.getOption("surah_number")).getAsInt();
                    var ayahNumber = Objects.requireNonNull(event.getOption("ayah_number")).getAsInt();
                    ayahReply.send(searchNumber, ayahNumber, quranService::searchAyah);
                }
            }
        }
    }
}
