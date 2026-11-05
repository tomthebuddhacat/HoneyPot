package io.github.tomthebuddhacat.listener;

import io.github.tomthebuddhacat.config.ConfigManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class PunishmentListener extends ListenerAdapter {

    private final ConfigManager configManager;

    public PunishmentListener(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent messageReceivedEvent) {

        // Ignore bot messages.
        if (messageReceivedEvent.getAuthor().isBot()) return;

        // Ignore messages that aren't sent within the honey-pot channel.
        if (!messageReceivedEvent.getChannel().getId().equals(configManager.getHoneyPotChannelId())) return;

        // Ignore messages from members the bot can't interact with.
        if (!messageReceivedEvent.getGuild().getSelfMember().canInteract(messageReceivedEvent.getMember())) return;

        // Punish the user who sent a message in the honeypot channel.
        // On success, print the banned user's ID to the console.
        // On failure, print the error message to the console.
        // TODO: Replace console output with a channel logging system.
        messageReceivedEvent.getGuild().ban(messageReceivedEvent.getAuthor(), 0, TimeUnit.SECONDS)
                .reason(configManager.getHoneyPotPunishmentReason())
                .queue(
                        onSuccess -> System.out.println("User with the ID " + messageReceivedEvent.getAuthor().getId() + " has been punished."),
                        onFailure -> System.out.println("Failed to punish the user with the ID: " + messageReceivedEvent.getAuthor().getId() + "\n" + onFailure.getMessage())
                );
    }
}
