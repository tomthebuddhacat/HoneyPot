package io.github.tomthebuddhacat.listener;

import io.github.tomthebuddhacat.config.ConfigManager;
import io.github.tomthebuddhacat.config.DataManager;
import io.github.tomthebuddhacat.utils.MessageService;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {

    private final ConfigManager configManager;

    private final DataManager dataManager;

    private final MessageService messageService;

    public ReadyListener(ConfigManager configManager, DataManager dataManager, MessageService messageService) {
        this.configManager = configManager;
        this.dataManager = dataManager;
        this.messageService = messageService;
    }

    @Override
    public void onReady(ReadyEvent readyEvent) {

        TextChannel textChannel = readyEvent.getJDA().getTextChannelById(configManager.getHoneyPotChannelId());

        if (textChannel == null) return;

        String messageId = dataManager.getWarningMessageId();

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
