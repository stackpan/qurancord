package com.stackpan.bot.command;

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

    protected abstract String nameInit();

    protected abstract String descriptionInit();
}
