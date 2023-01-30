package com.stackpan.bot.command.reply;

import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Map;

public class AyahReply {

    private final SlashCommandInteractionEvent event;

    public AyahReply(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    public void send(Map<String, Object> serviceResult) {
        var surah = (Surah) serviceResult.get("surah");
        var ayah = (Ayah) serviceResult.get("ayah");
        event.getHook().sendMessage(ayah.arabicText().strip() + "\n" + "**Q.S. " + surah.latinName() + ": " + ayah.number() + "**").queue();
    }
}
