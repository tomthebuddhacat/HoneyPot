package io.github.tomthebuddhacat;

import io.github.tomthebuddhacat.config.Config;
import io.github.tomthebuddhacat.config.Data;
import io.github.tomthebuddhacat.listener.PunishmentListener;
import io.github.tomthebuddhacat.listener.ReadyListener;
import io.github.tomthebuddhacat.utils.MessageService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;

public class Main {

    static void main(String[] args) throws IOException, InterruptedException {

        // Initialize the configuration class.
        Config config =
                new Config();

        // Initialize the data class.
        Data data =
                new Data();

        // Initialize the message service class.
        MessageService messageService =
                new MessageService(config, data);

        // Initialize the Discord bot.
        JDA jda = JDABuilder.createDefault(config.getAuthenticationToken())

                // Enable the required gateway intents.
                .enableIntents(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT
                )

               // Set the bot's activity.
                .setActivity(Activity.watching(config.getStatus()))

               // Register event listeners.
                .addEventListeners(
                        new PunishmentListener(config),
                        new ReadyListener(config, data, messageService)
                )

                .build();

        jda.awaitReady();
    }
}
