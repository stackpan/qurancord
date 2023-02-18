package com.stackpan.qurancord.bot.command;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

public class SearchCommandData extends AbstractSlashCommandData {
    @Override
    protected SlashCommandData init() {
        return Commands.slash(this.getName(), this.getDescription())
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
                );
    }

    @Override
    public String nameInit() {
        return "search";
    }

    @Override
    public String descriptionInit() {
        return "Mencari ayat atau surah Al-Quran";
    }
}
