package io.github.tomthebuddhacat.listener;

import io.github.tomthebuddhacat.config.ConfigManager;
import io.github.tomthebuddhacat.utils.PunishmentService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class MessageReceiveListener extends ListenerAdapter {

    private final PunishmentService punishmentService;

    private final ConfigManager configManager;

    public MessageReceiveListener(PunishmentService punishmentService, ConfigManager configManager) {
        this.punishmentService = punishmentService;
        this.configManager = configManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent messageReceivedEvent) {

        // Ignore bot messages.
        if (messageReceivedEvent.getAuthor().isBot()) return;

        // Ignore messages that aren't sent within the honey-pot channel.
        if (!messageReceivedEvent.getChannel().getId().equals(configManager.getHoneyPotChannelId())) return;

        // Ignore messages from members the bot can't interact with.
        if (!messageReceivedEvent.getGuild().getSelfMember().canInteract(Objects.requireNonNull(messageReceivedEvent.getMember()))) return;

        /*
         * Punish the user who sent a message in the honeypot channel.
         * On success, print the banned user's ID to the console.
         * On failure, print the error message to the console.
         *
         * The deletionTimeframe is currently set to 0 to prevent the user's
         * normal (non-scam) messages from being deleted.
         * To allow selective deletion, a method could be implemented to ensure
         * that messages from other channels are not deleted.
         */

        punishmentService.punishUser(messageReceivedEvent.getMember());

    }
}
