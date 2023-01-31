package com.stackpan.bot.command;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.*;

public class RandomCommandAbstract extends AbstractSlashCommandData {

    @Override
    protected SlashCommandData init() {
        return Commands.slash(this.getName(), this.getDescription())
                .addSubcommands(new SubcommandData("surah", "Meminta surah Al-Quran secara acak"))
                .addSubcommandGroups(new SubcommandGroupData("ayah", "Meminta ayat Al-Quran secara acak")
                        .addSubcommands(
                                new SubcommandData("any", "Meminta ayat Al-Quran di surah apapun"),
                                new SubcommandData("with-surah-name", "Meminta ayat Al-Quran berdasarkan surah")
                                        .addOption(OptionType.STRING, "surah_name", "Nama surah", true),
                                new SubcommandData("with-surah-number", "Meminta ayat Al-Quran berdasarkan nomor surah")
                                        .addOption(OptionType.INTEGER, "surah_number", "Nomor urutan surah", true)));

    }

    @Override
    protected String nameInit() {
        return "random";
    }

    @Override
    protected String descriptionInit() {
        return "Meminta ayat atau surah Al-Quran secara acak";
    }
}
