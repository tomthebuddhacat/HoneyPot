package io.github.tomthebuddhacat.utils;

import io.github.tomthebuddhacat.config.ConfigManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class LogService {

    private final ConfigManager configManager;

    private final MessageService messageService;

    public LogService(ConfigManager configManager, MessageService messageService) {
        this.configManager = configManager;
        this.messageService = messageService;
    }

    public void sendFailedMessage(Member member, String messageContent) {

        TextChannel textChannel =
                member.getGuild().getTextChannelById(configManager.getPunishmentLogChannelId());

        if (configManager.useLogService()) {

            if (textChannel != null) {

                messageService.logFailedMessage(
                        textChannel,
                        messageContent
                ).queue();
            }
        }
    }

    public void sendSuccessMessage(Member member, String messageContent) {

        TextChannel textChannel =
                member.getGuild().getTextChannelById(configManager.getPunishmentLogChannelId());

        if (configManager.useLogService()) {

            if (textChannel != null) {

                messageService.logSuccessMessage(
                        textChannel,
                        messageContent
                ).queue();
            }
        }
    }
}
