package com.stackpan.bot.command.reply;

import com.stackpan.bot.command.reply.embeds.SurahEmbed;
import com.stackpan.entity.Surah;
import com.stackpan.exception.SurahNotFoundException;
import com.stackpan.util.StringUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.function.Function;

public class SurahReply {

    private final SlashCommandInteractionEvent event;

    public SurahReply(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    public void send(Surah surah) {
        event.getHook().sendMessageEmbeds(SurahEmbed.show(surah)).queue();
        event.getHook().sendMessage(StringUtil.formatDiscord(surah.description())).queue();
    }

    public void send(Integer searchNumber, Function<Integer, Surah> function) {
        try {
            send(function.apply(searchNumber));
        } catch (SurahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage(searchNumber)).queue();
        }
    }

    public void send(String search, Function<String, Surah> function) {
        try {
            send(function.apply(search));
        } catch (SurahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage()).queue();
        }
    }
}
