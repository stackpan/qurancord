package com.stackpan.bot.command.handler;

import com.stackpan.bot.reply.AyahReply;
import com.stackpan.bot.reply.SurahReply;
import com.stackpan.service.QuranService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RandomCommandHandler extends ListenerAdapter {

    private final QuranService quranService;

    public RandomCommandHandler(QuranService quranService) {
        this.quranService = quranService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        // todo: make it more readable and maintainable
        // todo: prevent code repetition
        // todo: beautify the reply using Discord Embeds

        SurahReply surahReply = new SurahReply(event);
        AyahReply ayahReply = new AyahReply(event);

        if (event.getName().equals("random")) {
            if (Objects.equals(event.getSubcommandName(), "surah")) {
                event.deferReply().queue();
                surahReply.send(quranService.getRandomSurah());
            }

            if (Objects.equals(event.getSubcommandGroup(), "ayah")) {
                if (Objects.equals(event.getSubcommandName(), "any")) {
                    event.deferReply().queue();
                    ayahReply.send(quranService.getRandomAyah());
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-name")) {
                    event.deferReply().queue();
                    var searchName = Objects.requireNonNull(event.getOption("surah_name")).getAsString();
                    ayahReply.send(quranService.getRandomAyah(searchName));
                }

                if (Objects.requireNonNull(event.getSubcommandName()).equals("with-surah-number")) {
                    event.deferReply().queue();
                    var searchNumber = Objects.requireNonNull(event.getOption("surah_number")).getAsInt();
                    ayahReply.send(quranService.getRandomAyah(searchNumber));
                }
            }
        }
    }
}
