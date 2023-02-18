package com.stackpan.qurancord.bot.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class AbstractSlashCommandData extends AbstractCommandData {

    private final SlashCommandData commandData;

    public AbstractSlashCommandData() {
        super();
        this.commandData = init();
    }

    public CommandData get() {
        return commandData;
    }

    protected abstract SlashCommandData init();

}
