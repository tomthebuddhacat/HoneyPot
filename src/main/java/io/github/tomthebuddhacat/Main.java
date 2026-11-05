package io.github.tomthebuddhacat;

import io.github.tomthebuddhacat.config.ConfigManager;
import io.github.tomthebuddhacat.config.DataManager;
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

        // Initialize the configuration manager class.
        ConfigManager configManager =
                new ConfigManager();

        // Initialize the data manager class.
        DataManager dataManager =
                new DataManager();

        // Initialize the message service class.
        MessageService messageService =
                new MessageService(configManager, dataManager);

        // Initialize the Discord bot.
        JDA jda = JDABuilder.createDefault(configManager.getAuthenticationToken())

                // Enable the required gateway intents.
                .enableIntents(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT
                )

               // Set the bot's activity.
                .setActivity(Activity.watching(configManager.getStatus()))

               // Register event listeners.
                .addEventListeners(
                        new PunishmentListener(configManager),
                        new ReadyListener(configManager, dataManager, messageService)
                )

                .build();

        jda.awaitReady();
    }
}
