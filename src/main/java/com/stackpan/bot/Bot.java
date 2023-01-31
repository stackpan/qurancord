package com.stackpan.bot;

import com.stackpan.bot.command.RandomCommandAbstract;
import com.stackpan.bot.command.SearchCommandAbstract;
import com.stackpan.bot.command.handler.RandomCommandHandler;
import com.stackpan.bot.command.handler.SearchCommandHandler;
import com.stackpan.service.DiscordQuranService;
import com.stackpan.service.QuranService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Bot {

    public static void main(String[] args) {

        JDA api = JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMinutes(1))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        QuranService quranService = new DiscordQuranService(httpClient);

        // todo: make commands manager
        // todo: make language manager
        List<SlashCommandData> slashCommands = new ArrayList<>(List.of(
                (SlashCommandData) new RandomCommandAbstract().get(),
                (SlashCommandData) new SearchCommandAbstract().get()
        ));

        api.updateCommands().addCommands(slashCommands).queue();

        System.out.println(quranService.hashCode());

        api.addEventListener(new RandomCommandHandler(quranService));
        api.addEventListener(new SearchCommandHandler(quranService));
    }

}
