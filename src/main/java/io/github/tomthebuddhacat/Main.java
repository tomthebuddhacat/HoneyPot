package io.github.tomthebuddhacat;

import io.github.tomthebuddhacat.config.Config;
import io.github.tomthebuddhacat.listener.PunishmentListener;
import io.github.tomthebuddhacat.listener.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;

public class Main {

    static void main(String[] args) throws IOException, InterruptedException {

        // Load the bot configuration.
        Config config = new Config();

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
                        new ReadyListener(config)
                )

                .build();

        jda.awaitReady();
    }
}
