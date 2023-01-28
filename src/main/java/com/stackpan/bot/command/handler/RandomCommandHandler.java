package com.stackpan.bot.command.handler;

import com.stackpan.service.QuranService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class RandomCommandHandler extends ListenerAdapter {

    private final QuranService quranService;

    public RandomCommandHandler(QuranService quranService) {
        this.quranService = quranService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // todo: make it more readable and maintainable
        // todo: prevent code repetition
        // todo: beautify the reply using Discord Embeds
        if (event.getName().equals("random")) {
            if (Objects.equals(event.getSubcommandName(), "surah")) {
                event.deferReply().queue();
                var serviceResult = quranService.getRandomSurah();
                event.getHook().sendMessage(serviceResult.toString()).queue();
            }

            if (Objects.equals(event.getSubcommandGroup(), "ayah")) {
                if (Objects.equals(event.getSubcommandName(), "any")) {
                    event.deferReply().queue();
                    var serviceResult = quranService.getRandomAyah();
                    event.getHook().sendMessage(serviceResult.toString()).queue();
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-name")) {
                    event.deferReply().queue();
                    var searchName = Objects.requireNonNull(event.getOption("surah_name")).getAsString();
                    var serviceResult = quranService.getRandomAyah(searchName);
                    event.getHook().sendMessage(serviceResult.toString()).queue();
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-number")) {
                    event.deferReply().queue();
                    var searchNumber = Objects.requireNonNull(event.getOption("surah_number")).getAsInt();
                    var serviceResult = quranService.getRandomAyah(searchNumber);
                    event.getHook().sendMessage(serviceResult.toString()).queue();
                }
            }
        }
    }
}
