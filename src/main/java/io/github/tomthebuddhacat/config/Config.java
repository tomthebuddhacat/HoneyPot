package io.github.tomthebuddhacat.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    private final HoconConfigurationLoader hoconConfigurationLoader;

    private final CommentedConfigurationNode commentedConfigurationNode;

    /**
     * Initializes the configuration loader, loads the configuration
     * with the default values and comments.
     *
     * @throws IOException if the configuration failed to load or save.
     */
    public Config() throws IOException {

        // Define the configuration file location.
        Path configPath = Path.of("config.conf").toAbsolutePath();

        // Create the directory if it doesn't exist.
        if (configPath.getParent() == null) {
            Files.createDirectories(configPath.getParent());
        }

         hoconConfigurationLoader =
                HoconConfigurationLoader.builder()
                        .path(configPath)
                        .build();

        this.commentedConfigurationNode = hoconConfigurationLoader.load(
                ConfigurationOptions.defaults().header(
                        """
                                Discord Bot Configuration.
                                
                                This file contains the configuration settings used by the bot.
                                Modify the values below as per your needs."""
                )
        );

        getDefaultConfiguration();

        hoconConfigurationLoader.save(commentedConfigurationNode);
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

    public String getHoneyPotMessageFormat() {
        return commentedConfigurationNode.node("honey-pot-message-format").getString();
    }

    public String getHoneyPotMessage() {
        return commentedConfigurationNode.node("honey-pot-warning-message").getString();
    }

    public String getHoneyPotEmbedTitle() {
        return commentedConfigurationNode.node("honey-pot-embed-warning-message", "embedTitle").getString();
    }

    public String getHoneyPotEmbedDescription() {
        return commentedConfigurationNode.node("honey-pot-embed-warning-message", "embedDescription").getString();
    }

    public String getHoneyPotEmbedColor() {
        return commentedConfigurationNode.node("honey-pot-embed-warning-message", "embedColor").getString();
    }

    public String getHoneyPotEmbedFooter() {
        return commentedConfigurationNode.node("honey-pot-embed-warning-message", "embedFooter").getString();
    }

    /**
     * Initializes default configuration values.
     */
    private void getDefaultConfiguration() throws SerializationException {
        CommentedConfigurationNode discordBotToken =
                commentedConfigurationNode.node("discord-bot-token");

        discordBotToken.comment(
                """
                        Authentication Configuration.
                        
                        A Discord bot token is a secret, unique string of letters, numbers,
                        and symbols that acts as a digital password or key for your Discord bot.
                        
                        Reference URL: https://docs.discord.com/developers/bots/overview"""
        );

        if (discordBotToken.isNull()) {
            discordBotToken.set("CHANGE_THIS_VALUE");
        }

        CommentedConfigurationNode discordBotStatus =
                commentedConfigurationNode.node("discord-bot-status");

        discordBotStatus.comment(
                """
                        Miscellaneous Configuration.
                        
                        The activity displayed under the application.
                        
                        Reference URL: https://wiki.botdesignerdiscord.com/guides/introduction/botStatus.html"""
        );

        if (discordBotStatus.isNull()) {
            discordBotStatus.set("CHANGE_THIS_VALUE");
        }

        CommentedConfigurationNode honeyPotChannel =
                commentedConfigurationNode.node("honey-pot-channel-id");

        honeyPotChannel.comment(
                """
                        Moderation Configuration.
                        
                        The Discord Channel that is monitored.
                        Any user who sends a message in this channel will be punished."""
        );

        if (honeyPotChannel.isNull()) {
            honeyPotChannel.set("CHANGE_THIS_VALUE");
        }

        CommentedConfigurationNode honeyPotBanReason =
                commentedConfigurationNode.node("honey-pot-punishment-reason");

        honeyPotBanReason.comment(
                """
                        Moderation Configuration.
                        
                        The reason for the user's punishment, which will be logged in the Discord Audit Log."""
        );

        CommentedConfigurationNode honeyPotMessageFormat =
                commentedConfigurationNode.node("honey-pot-message-format");

        honeyPotMessageFormat.comment(
                """
                        Moderation Configuration.
                        
                        The warning message that should be sent in the configured honey-pot channel.
                        Supported values: text, embed."""
        );

        if (honeyPotMessageFormat.isNull()) {
            honeyPotMessageFormat.set("CHANGE_THIS_VALUE");
        }

        CommentedConfigurationNode honeyPotMessage =
                commentedConfigurationNode.node("honey-pot-warning-message");

        if (honeyPotMessage.isNull()) {
            honeyPotMessage.set("CHANGE_THIS_VALUE");
        }

        CommentedConfigurationNode honeyPotEmebedMessage =
                commentedConfigurationNode.node("honey-pot-embed-warning-message");

        if (honeyPotEmebedMessage.node("embedTitle").isNull()) {
            honeyPotEmebedMessage.node("embedTitle").set("CHANGE_THIS_VALUE");
        }

        if (honeyPotEmebedMessage.node("embedDescription").isNull()) {
            honeyPotEmebedMessage.node("embedDescription").set("CHANGE_THIS_VALUE");
        }

        if (honeyPotEmebedMessage.node("embedColor").isNull()) {
            honeyPotEmebedMessage.node("embedColor").set("CHANGE_THIS_VALUE");
        }

        if (honeyPotEmebedMessage.node("embedFooter").isNull()) {
            honeyPotEmebedMessage.node("embedFooter").set("CHANGE_THIS_VALUE");
        }
    }
}
