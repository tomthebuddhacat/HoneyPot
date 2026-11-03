package io.github.tomthebuddhacat.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Path;

public class Config {

    private HoconConfigurationLoader hoconConfigurationLoader;

    private CommentedConfigurationNode commentedConfigurationNode;

    /**
     * Initializes the configuration loader, loads the configuration
     * with the default values and comments.
     *
     * @throws IOException if the configuration failed to load or save.
     */
    public Config() throws IOException {

         hoconConfigurationLoader =
                HoconConfigurationLoader.builder()
                        .path(Path.of("config.conf"))
                        .build();

        this.commentedConfigurationNode = hoconConfigurationLoader.load(
                ConfigurationOptions.defaults().header(
                        "Discord Bot Configuration.\n\n" +
                        "This file contains the configuration settings used by the bot.\n" +
                        "Modify the values below as per your needs."
                )
        );

        getDefaultConfiguration();

        hoconConfigurationLoader.save(commentedConfigurationNode);
    }

    /**
     * Initializes default configuration values.
     */
    private void getDefaultConfiguration() throws SerializationException {
        CommentedConfigurationNode discordBotToken =
                commentedConfigurationNode.node("discord-bot-token");

        discordBotToken.comment(
                "Authentication Configuration.\n\n" +
                "A Discord bot token is a secret, unique string of letters, numbers,\n" +
                "and symbols that acts as a digital password or key for your Discord bot.\n\n" +
                "Reference URL: https://docs.discord.com/developers/bots/overview"
        );

        if (discordBotToken.isNull()) {
            discordBotToken.set("CHANGE_THIS_VALUE");
        }

        CommentedConfigurationNode discordBotStatus =
                commentedConfigurationNode.node("discord-bot-status");

        discordBotStatus.comment(
                "Miscellaneous Configuration.\n\n" +
                "The activity displayed under the application.\n\n" +
                "Reference URL: https://wiki.botdesignerdiscord.com/guides/introduction/botStatus.html"
        );

        if (discordBotStatus.isNull()) {
            discordBotStatus.set("CHANGE_THIS_VALUE");
        }

        CommentedConfigurationNode honeyPotChannel =
                commentedConfigurationNode.node("honey-pot-channel-id");

        honeyPotChannel.comment(
                "Moderation Configuration.\n\n" +
                "The Discord Channel that is monitored.\n" +
                "Any user who sends a message in this channel will be punished."
        );

        if (honeyPotChannel.isNull()) {
            honeyPotChannel.set("CHANGE_THIS_VALUE");
        }

        CommentedConfigurationNode honeyPotBanReason =
                commentedConfigurationNode.node("honey-pot-punishment-reason");

        honeyPotBanReason.comment(
                "Moderation Configuration.\n\n" +
                "The reason for the user's punishment, which will be logged in the Discord Audit Log."
        );
    }

    public String getAuthenticationToken() {
        return commentedConfigurationNode.node("discord-bot-token").getString();
    }

    public String getStatus() {
        return commentedConfigurationNode.node("discord-bot-status").getString();
    }

    public String getHoneyPotChannelId() {
        return commentedConfigurationNode.node("honey-pot-channel-id").getString();
    }

    public String getHoneyPotPunishmentReason() {
        return commentedConfigurationNode.node("honey-pot-punishment-reason").getString();
    }
}
