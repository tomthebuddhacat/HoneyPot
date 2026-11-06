package io.github.tomthebuddhacat.utils;

import io.github.tomthebuddhacat.config.ConfigManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.concurrent.TimeUnit;

public class PunishmentService {

    private final ConfigManager configManager;

    private final LogService logService;

    public PunishmentService(ConfigManager configManager, LogService logService) {
        this.configManager = configManager;
        this.logService = logService;
    }

    public void punishUser(Member member) {

        String punishmentType = configManager.getHoneyPotPunishmentType();

        switch (punishmentType) {

            case "hardban" ->

                member.getGuild().ban(member, 7, TimeUnit.DAYS)
                        .reason(configManager.getHoneyPotPunishmentReason())
                        .queue(
                                onSuccess -> {
                                    System.out.println(
                                            "User with the ID " + member.getId() + " has been punished (hard-banned)."
                                    );

                                    logService.sendSuccessMessage(
                                            member, "User with the ID " + member.getId() + " has been punished (hard-banned)."
                                    );
                                },

                                onFailure -> {
                                    System.out.println(
                                            "Failed to punish the user with the ID: " + member.getId() + "\n" + onFailure.getMessage()
                                    );

                                    logService.sendFailedMessage(
                                            member, "Failed to punish the user with the ID: " + member.getId() + "\n" + onFailure.getMessage()
                                    );
                                }
                        );

            case "softban" -> {

                TextChannel textChannel = member.getGuild().getTextChannelById(configManager.getHoneyPotChannelId());

                if (textChannel != null) {

                    textChannel.getIterableHistory()
                            .takeAsync(100)
                            .thenAccept(userMessages ->
                                    userMessages.stream()

                                            .filter(messages -> messages.getAuthor().getId().equals(member.getId()))

                                            .forEach(messages -> messages.delete()
                                                    .queue(
                                                    onSuccess -> {
                                                        System.out.println(
                                                                "User with the ID " + member.getId() + " has been punished (soft-banned)."
                                                        );

                                                        logService.sendSuccessMessage(
                                                                member, "User with the ID " + member.getId() + " has been punished (soft-banned)."
                                                        );
                                                    },

                                                    onFailure -> {
                                                        System.out.println(
                                                                "Failed to punish the user with the ID: " + member.getId() + "\n" + onFailure.getMessage()
                                                        );

                                                        logService.sendFailedMessage(
                                                                member, "Failed to punish the user with the ID: " + member.getId() + "\n" + onFailure.getMessage()
                                                        );
                                                    }
                                            )));
                }
            }


            case "timeout" ->

                member.getGuild().timeoutFor(member, 7, TimeUnit.DAYS)
                        .reason(configManager.getHoneyPotPunishmentReason())
                        .queue(
                                onSuccess -> {
                                    System.out.println(
                                            "User with the ID " + member.getId() + " has been punished (timeout)."
                                    );

                                    logService.sendSuccessMessage(
                                            member, "User with the ID " + member.getId() + " has been punished (timeout)."
                                    );
                                },

                                onFailure -> {
                                    System.out.println(
                                            "Failed to punish the user with the ID: " + member.getId() + "\n" + onFailure.getMessage()
                                    );

                                    logService.sendFailedMessage(
                                            member, "Failed to punish the user with the ID: " + member.getId() + "\n" + onFailure.getMessage()
                                    );
                                }
                        );
        }
    }
}
