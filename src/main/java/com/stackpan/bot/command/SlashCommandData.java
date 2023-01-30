package com.stackpan.bot.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class SlashCommandData extends AbstractCommandData {

    private final net.dv8tion.jda.api.interactions.commands.build.SlashCommandData commandData;

    public SlashCommandData() {
        super();
        this.commandData = init();
    }

    public CommandData get() {
        return commandData;
    }

    protected abstract net.dv8tion.jda.api.interactions.commands.build.SlashCommandData init();

}
