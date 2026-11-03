package io.github.tomthebuddhacat.listener;

import io.github.tomthebuddhacat.config.Config;
import io.github.tomthebuddhacat.config.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;

public class ReadyListener extends ListenerAdapter {

    private final Config config;

    private final Data data;

    public ReadyListener(Config config, Data data) {
        this.config = config;
        this.data = data;
    }

    @Override
    public void onReady(ReadyEvent readyEvent) {

        TextChannel textChannel = readyEvent.getJDA().getTextChannelById(config.getHoneyPotChannelId());

        if (textChannel == null) return;

        String messageId = data.getWarningMessageId();

        if (messageId != null) {
            textChannel.retrieveMessageById(messageId).queue(
                    onSuccess -> publishMessage(textChannel),
                    onFailure -> System.out.println("HoneyPot warning message already exists.")
            );
        } else {
            publishMessage(textChannel);
        }
    }

    private void publishMessage(TextChannel textChannel) {

        RestAction<Message> restAction;

        switch (config.getHoneyPotMessageFormat().toLowerCase(Locale.ROOT)) {

            case "text" -> restAction = textChannel.sendMessage(config.getHoneyPotMessage());

            case "embed" -> restAction = textChannel.sendMessageEmbeds(
                    new EmbedBuilder()
                            .setTitle(config.getHoneyPotEmbedTitle())
                            .setDescription(config.getHoneyPotEmbedDescription())
                            .setFooter(config.getHoneyPotEmbedFooter())
                            .setColor(Color.decode(config.getHoneyPotEmbedColor()))
                            .build()
            );

            default -> {
                System.out.println("Invalid HoneyPot message format used. Please use text or embed.");
                return;
            }

        }

        restAction.queue( message -> {
                    try {
                        data.setWarningMessageId(message.getId());
                        System.out.println("Successfully published HoneyPot warning message.");
                    } catch (IOException ioException) {
                        System.out.println("Failed to save warning messageID: " + ioException.getMessage());
                    }
                }
        );
    }
}
