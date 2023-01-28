package com.stackpan.bot.command.handler;

import com.stackpan.service.QuranService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class SearchCommandHandler extends ListenerAdapter {

    private final QuranService quranService;

    public SearchCommandHandler(QuranService quranService) {
        this.quranService = quranService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // todo: make it more readable and maintainable
        // todo: prevent code repetition
        // todo: beautify the reply using Discord Embeds
        if (event.getName().equals("search")) {
            if (Objects.equals(event.getSubcommandGroup(), "surah")) {
                if (Objects.requireNonNull(event.getSubcommandName()).equals("name")) {
                    event.deferReply().queue();
                    var searchName = Objects.requireNonNull(event.getOption("search_name")).getAsString();
                    var serviceResult = quranService.searchSurah(searchName);
                    event.getHook().sendMessage(serviceResult.toString()).queue();
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("number")) {
                    event.deferReply().queue();
                    var searchNumber = Objects.requireNonNull(event.getOption("search_number")).getAsInt();
                    var serviceResult = quranService.searchSurah(searchNumber);
                    event.getHook().sendMessage(serviceResult.toString()).queue();
                }
            }

            if (Objects.equals(event.getSubcommandGroup(), "ayah")) {
                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-name")) {
                    event.deferReply().queue();
                    var searchName = Objects.requireNonNull(event.getOption("surah_name")).getAsString();
                    var ayahNumber = Objects.requireNonNull(event.getOption("ayah_number")).getAsInt();
                    var serviceResult = quranService.searchAyah(searchName, ayahNumber);
                    event.getHook().sendMessage(serviceResult.toString()).queue();
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-number")) {
                    event.deferReply().queue();
                    var searchNumber = Objects.requireNonNull(event.getOption("surah_number")).getAsInt();
                    var ayahNumber = Objects.requireNonNull(event.getOption("ayah_number")).getAsInt();
                    var serviceResult = quranService.searchAyah(searchNumber, ayahNumber);
                    event.getHook().sendMessage(serviceResult.toString()).queue();
                }
            }
        }
    }
}
