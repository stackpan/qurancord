package com.stackpan.qurancord.bot;

import com.stackpan.qurancord.bot.commands.old.RandomCommandData;
import com.stackpan.qurancord.bot.commands.old.SearchCommandData;
import com.stackpan.qurancord.bot.commands.old.handler.RandomCommandHandler;
import com.stackpan.qurancord.bot.commands.old.handler.SearchCommandHandler;
import com.stackpan.qurancord.core.Bootstrap;
import com.stackpan.qurancord.core.service.DiscordQuranService;
import com.stackpan.qurancord.core.service.QuranService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Bot {

    public static void main(String[] args) {

        Bootstrap.checkEnv();
        Bootstrap.boot();

        JDA api = JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMinutes(1))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        QuranService quranService = new DiscordQuranService(httpClient);

        // todo: make commands manager
        // todo: make language manager
        List<SlashCommandData> slashCommands = new ArrayList<>(List.of(
                (SlashCommandData) new RandomCommandData().get(),
                (SlashCommandData) new SearchCommandData().get()
        ));

        api.updateCommands().addCommands(slashCommands).queue();

        api.addEventListener(new RandomCommandHandler(quranService));
        api.addEventListener(new SearchCommandHandler(quranService));
    }

}
