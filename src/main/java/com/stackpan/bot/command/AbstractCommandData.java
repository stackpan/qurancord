package com.stackpan.bot.command;

public abstract class AbstractCommandData implements InsatiableCommandData {

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

    public abstract String nameInit();

    public abstract String descriptionInit();
}
