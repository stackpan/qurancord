package com.stackpan.bot.command.reply;

import com.stackpan.bot.command.reply.embeds.SurahEmbed;
import com.stackpan.entity.Surah;
import com.stackpan.util.StringFormatter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SurahReply {

    private final SlashCommandInteractionEvent event;

    public SurahReply(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    public void send(Surah surah) {
        event.getHook().sendMessageEmbeds(SurahEmbed.show(surah)).queue();
        event.getHook().sendMessage(StringFormatter.formatDiscord(surah.description())).queue();
    }
}
