package io.github.tomthebuddhacat.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Data {

    private final HoconConfigurationLoader hoconConfigurationLoader;

    private final CommentedConfigurationNode commentedConfigurationNode;

    /**
     * Initializes the data loader and loads the data file
     * with the values and comments.
     *
     * @throws IOException if the data file failed to load or save.
     */
    public Data() throws IOException {

        // Define the data file location.
        Path dataPath = Path.of("data.conf").toAbsolutePath();

        // Create the directory if it doesn't exist.
        if (dataPath.getParent() != null) {
            Files.createDirectories(dataPath.getParent());
        }

        hoconConfigurationLoader =
                HoconConfigurationLoader.builder()
                        .path(dataPath)
                        .build();

        this.commentedConfigurationNode = hoconConfigurationLoader.load(
                ConfigurationOptions.defaults().header(
                        """
                                HoneyPot Data File.
                                
                                This file contains data stored by the bot.
                                Values in this file are automatically updated by the bot."""
                )
        );

        getDefaultData();

        hoconConfigurationLoader.save(commentedConfigurationNode);
    }

    /**
     * Retrieves the stored HoneyPot warning message ID.
     *
     * @return the warning message ID.
     */
    public String getWarningMessageId() {
        return commentedConfigurationNode.node("honey-pot-warning-message-id").getString();
    }

    /**
     * Updates the stored HoneyPot warning message ID.
     *
     * @param messageId The Discord Message ID.
     * @throws IOException if the data file failed to save.
     */
    public void setWarningMessageId(String messageId) throws IOException {
        commentedConfigurationNode.node("honey-pot-warning-message-id").set(messageId);
        hoconConfigurationLoader.save(commentedConfigurationNode);
    }

    /**
     * Initializes default data vaules.
     */
    private void getDefaultData() throws SerializationException {

        CommentedConfigurationNode warningMessageId =
                commentedConfigurationNode.node("honey-pot-warning-message-id");

        warningMessageId.comment(
                """
                        HoneyPot Warning Message ID.
                        
                        Stores the Discord Message ID of the published HoneyPot warning message.
                        This is used to check whether the warning message exists or not."""
        );
    }
}
