package io.github.tomthebuddhacat.listener;

import io.github.tomthebuddhacat.config.Config;
import io.github.tomthebuddhacat.config.Data;
import io.github.tomthebuddhacat.utils.MessageService;
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

    private final MessageService messageService;

    public ReadyListener(Config config, Data data, MessageService messageService) {
        this.config = config;
        this.data = data;
        this.messageService = messageService;
    }

    @Override
    public void onReady(ReadyEvent readyEvent) {

        TextChannel textChannel = readyEvent.getJDA().getTextChannelById(config.getHoneyPotChannelId());

        if (textChannel == null) return;

        String messageId = data.getWarningMessageId();

        if (messageId != null) {
           textChannel.retrieveMessageById(messageId).queue(
                   onSuccess -> System.out.println("HoneyPot warning message already exists"),
                   onFailure -> messageService.publishWarningMessage(textChannel)
           );
        } else {
            messageService.publishWarningMessage(textChannel);
        }
    }
}
