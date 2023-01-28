package com.stackpan.bot;

import com.stackpan.bot.command.handler.RandomCommandHandler;
import com.stackpan.bot.command.handler.SearchCommandHandler;
import com.stackpan.service.DiscordQuranService;
import com.stackpan.service.QuranService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.net.http.HttpClient;
import java.time.Duration;

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
        api.updateCommands().addCommands(
                Commands.slash("random", "Meminta ayat atau surah Al-Quran secara acak")
                        .addSubcommands(new SubcommandData("surah", "Meminta surah Al-Quran secara acak"))
                        .addSubcommandGroups(new SubcommandGroupData("ayah", "Meminta ayat Al-Quran secara acak")
                                .addSubcommands(
                                        new SubcommandData("any", "Meminta ayat Al-Quran di surah apapun"),
                                        new SubcommandData("with-surah-name", "Meminta ayat Al-Quran berdasarkan surah")
                                                .addOption(OptionType.STRING, "surah_name", "Nama surah", true),
                                        new SubcommandData("with-surah-number", "Meminta ayat Al-Quran berdasarkan nomor surah")
                                                .addOption(OptionType.INTEGER, "surah_number", "Nomor uruta surah", true))),
                Commands.slash("search", "Mencari ayat atau surah Al-Quran")
                        .addSubcommandGroups(
                                new SubcommandGroupData("surah", "Mencari surah Al-Quran")
                                        .addSubcommands(
                                                new SubcommandData("name", "Mencari surah Al-Quran dengan nama")
                                                        .addOption(OptionType.STRING, "search_name", "Nama surah yang ingin dicari", true),
                                                new SubcommandData("number", "Mencari surah Al-Quran dengan nomor urut surah")
                                                        .addOption(OptionType.INTEGER, "search_number", "Nomor urut surah yang ingin dicari", true)
                                        ),
                                new SubcommandGroupData("ayah", "Mencari ayat Al-Quran")
                                        .addSubcommands(
                                                new SubcommandData("with-surah-name", "Mencari ayat di surah Al-Quran dengan nama surah")
                                                        .addOption(OptionType.STRING, "surah_name", "Nama surah", true)
                                                        .addOption(OptionType.INTEGER, "ayah_number", "Nomor ayat yang ingin dicari", true),
                                                new SubcommandData("with-surah-number", "Mencari ayat di surah Al-Quran dengan nomor urut surah")
                                                        .addOption(OptionType.INTEGER, "surah_number", "Nomor urut surah", true)
                                                        .addOption(OptionType.INTEGER, "ayah_number", "Nomor ayat yang ingin dicari", true)
                                        )
                        )
        ).queue();

        api.addEventListener(new RandomCommandHandler(quranService));
        api.addEventListener(new SearchCommandHandler(quranService));
    }

}
