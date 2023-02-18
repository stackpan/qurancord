package com.stackpan.qurancord.bot.commands.old.reply;

import com.stackpan.qurancord.core.App;
import com.stackpan.qurancord.core.entity.Ayah;
import com.stackpan.qurancord.core.entity.Surah;
import com.stackpan.qurancord.core.exception.AyahNotFoundException;
import com.stackpan.qurancord.core.exception.SurahNotFoundException;
import com.stackpan.qurancord.core.worker.AyahImageWorker;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    public void send(String search, Function<String, Map<String, Object>> function) {
        try {
            send(function.apply(search));
        } catch (SurahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage()).queue();
        }
    }

    public void send(Integer searchNumber, Function<Integer, Map<String, Object>> function) {
        try {
            send(function.apply(searchNumber));
        } catch (SurahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage(searchNumber)).queue();
        }
    }

    public void send(String search, Integer searchNumber, BiFunction<String, Integer, Map<String, Object>> function) {
        try {
            send(function.apply(search, searchNumber));
        } catch (SurahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage()).queue();
        } catch (AyahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage(searchNumber)).queue();
        }
    }

    public void send(Integer searchNumber1, Integer searchNumber2, BiFunction<Integer, Integer, Map<String, Object>> function) {
        try {
            send(function.apply(searchNumber1, searchNumber2));
        } catch (SurahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage()).queue();
        } catch (AyahNotFoundException e) {
            event.getHook().sendMessage(e.getUserMessage(searchNumber1)).queue();
        }
    }

    public void sendFiles(Map<String, Object> serviceResult) {
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
