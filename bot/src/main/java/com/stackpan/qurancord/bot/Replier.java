package com.stackpan.qurancord.bot;

import com.stackpan.qurancord.bot.embeds.SurahEmbed;
import com.stackpan.qurancord.core.App;
import com.stackpan.qurancord.core.entity.Ayah;
import com.stackpan.qurancord.core.entity.Surah;
import com.stackpan.qurancord.core.exception.AyahNotFoundException;
import com.stackpan.qurancord.core.exception.SurahNotFoundException;
import com.stackpan.qurancord.core.util.StringUtil;
import com.stackpan.qurancord.core.worker.AyahImageWorker;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;

public class Replier {

    public static void replySurah(SlashCommandInteractionEvent event, Surah surah) {
        event.getHook().sendMessageEmbeds(SurahEmbed.show(surah)).queue();
        event.getHook().sendMessage(StringUtil.formatDiscord(surah.description())).queue();
    }

    public static void replyAyah(SlashCommandInteractionEvent event, Map<String, Object> serviceResult) {
        var surah = (Surah) serviceResult.get("surah");
        var ayah = (Ayah) serviceResult.get("ayah");

        event.getHook().sendMessageFormat("%s \n**Q.S. %s: %d**", ayah.arabicText().strip(), surah.latinName(), ayah.number()).queue();
    }

    public static void processReplier(SlashCommandInteractionEvent event, Consumer<SlashCommandInteractionEvent> consumer) {
        try {
            consumer.accept(event);
        } catch (SurahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage()).queue();
        } catch (AyahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage()).queue();
        }
    }

    public static void sendAyahImage(SlashCommandInteractionEvent event, Map<String, Object> serviceResult) {
        var surah = (Surah) serviceResult.get("surah");
        var ayah = (Ayah) serviceResult.get("ayah");

        // If file not exist, generate first. Otherwise, send
        Path path = Paths.get(App.CACHE_RESOURCE_PATH + "/" + surah.number() + "_" + ayah.number() + ".png");

        if (!Files.exists(path)) {
            AyahImageWorker worker = new AyahImageWorker(surah, ayah);
            worker.start();
            try {
                worker.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        event.getHook().sendFiles(FileUpload.fromData(path)).queue();
    }

}
