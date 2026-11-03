package io.github.tomthebuddhacat.manager;

import io.github.tomthebuddhacat.config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;
import java.util.Locale;

public class MessageManager {

    private JDA jda;

    private Config config;

    // You may change this String to anything.
    private String checker_string = "CHANGE_THIS_STRING";

    public MessageManager(JDA jda, Config config) {
        this.jda = jda;
        this.config = config;
    }

    /**
     * Initializes the honeypot warning message.
     */
    public void sendWarningMessage() {
        String channelId = config.getHoneyPotChannelId();

        // Check whether the channel exists or not,
        // if it doesn't exist, return an error.
        TextChannel textChannel = jda.getTextChannelById(channelId);

        if (textChannel == null) {
            System.out.println("Honey pot channel does not exist!");
            return;
        }

        checkPostedMessage(textChannel);

    }

    /**
     * Checks whether the startup message has already been sent or not.
     */
    private void checkPostedMessage(TextChannel textChannel) {

        // Limit is 100 message
        textChannel.getHistory().retrievePast(99).queue( messages -> {

                    boolean messageExists = messages.stream()
                            .anyMatch(message -> message.getAuthor().equals(jda.getSelfUser())
                            && isWarningMessage(message));

                    if (!messageExists) {
                        sendWarningMessage(textChannel);
                    } else {
                        System.out.println("Honey pot warning message already exists in " + textChannel.getId());
                    }
                },
                onFailure -> System.out.println("Failed to retrieve channel history: " + onFailure.getMessage())
        );
    }

    /**
     * Checks whether a message is the honeypot warning message or not.
     */
    private boolean isWarningMessage(Message message) {

        // Check the text message.
        if (message.getContentRaw().contains(checker_string)) {
            return true;
        }

        // Check the embed message.
        return message.getEmbeds().stream()
                .anyMatch(messageEmbed ->
                        messageEmbed.getFooter() != null && messageEmbed.getFooter().getText() != null
                && messageEmbed.getFooter().getText().contains(checker_string));
    }

    /**
     * Sends the configured honey pot warning message.
     */
    private void sendWarningMessage(TextChannel textChannel) {

        String messageFormat = config.getHoneyPotMessageFormat();

        switch (messageFormat.toLowerCase(Locale.ROOT)) {

            case "text" -> {

                textChannel.sendMessage(config.getHoneyPotMessage() + "\n" + checker_string).queue(
                        onSuccess -> System.out.println("Successfully published warning message to" + textChannel.getId()),
                        onFailure -> System.out.println("Failed to publish warning message to " + textChannel.getId() + "\n" + onFailure.getMessage())
                );
            }

            case "embed" -> {

                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle(config.getHoneyPotEmbedTitle())
                        .setDescription(config.getHoneyPotEmbedDescription())
                        .setFooter(config.getHoneyPotEmbedFooter() + " | " + checker_string)
                        .setColor(Color.decode(config.getHoneyPotEmbedColor()));

                textChannel.sendMessageEmbeds(embedBuilder.build()).queue(
                        onSuccess -> System.out.println("Successfully published embed-warning message to" + textChannel.getId()),
                        onFailure -> System.out.println("Failed to publish embed-warning message to " + textChannel.getId() + "\n" + onFailure.getMessage())
                );
            }

            default -> System.out.println("Invalid honey pot message format. Please use text or embed.");
        }
    }
}
