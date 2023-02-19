package com.stackpan.qurancord.bot;

import com.freya02.botcommands.api.CommandsBuilder;
import com.freya02.botcommands.api.Logging;
import com.stackpan.qurancord.core.App;
import com.stackpan.qurancord.core.Bootstrapper;
import com.stackpan.qurancord.core.service.DiscordQuranService;
import com.stackpan.qurancord.core.service.QuranService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;

import java.io.IOException;

public class Bot {

    private static App app;

    private static final Logger LOGGER = Logging.getLogger();

    public static void main(String[] args) {
        Bootstrapper.checkEnv();
        Bootstrapper.boot();

        app = new App();
        app.setQuranService(new DiscordQuranService(App.HTTP_CLIENT));

        try {
            JDA jda = JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build();

            CommandsBuilder.newBuilder()
                    .build(jda, "com.stackpan.qurancord.bot.commands");
        } catch (IOException e) {
            LOGGER.error("Unable to start the bot", e);
            System.exit(-1);
        }
    }

    public static QuranService getQuranService() {
        return app.getQuranService();
    }

}
