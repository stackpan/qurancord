package com.stackpan.bot.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class AbstractCommandData {

    private final String name;
    private final String description;

    protected AbstractCommandData() {
        this.name = nameInit();
        this.description = descriptionInit();
    }

    protected String getName() {
        return name;
    }

    protected String getDescription() {
        return description;
    }

    public abstract CommandData get();

    protected abstract CommandData init();
    protected abstract String nameInit();

    protected abstract String descriptionInit();
}
