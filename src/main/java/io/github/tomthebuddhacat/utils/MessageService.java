package io.github.tomthebuddhacat.utils;

import io.github.tomthebuddhacat.config.ConfigManager;
import io.github.tomthebuddhacat.config.DataManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.MessageTopLevelComponent;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MessageService {

    private final ConfigManager configManager;

    private final DataManager dataManager;

    public MessageService(ConfigManager configManager, DataManager dataManager) {
        this.configManager = configManager;
        this.dataManager = dataManager;
    }

    /**
     * Sends a plain text message.
     *
     * @param messageChannel The channel where the message is supposed to be sent in.
     * @param messageContent The message that is supposed to published.
     * @return A published message in a specific channel.
     */
    public MessageCreateAction textMessage(
            MessageChannel messageChannel,
            String messageContent) {

        return messageChannel.sendMessage(messageContent);
    }

    /**
     * Sends a message through embeds.
     *
     * @param messageChannel The channel where the embed message is supposed to be sent in.
     * @param embedTitle The title of the embed message.
     * @param embedDescription The description of the embed message.
     * @param embedFooter The footer of the embed message.
     * @param embedColor The color which the embed message is supposed to appear in.
     * @return A published message in a specific channel.
     */
    public MessageCreateAction embedMessage(
            MessageChannel messageChannel,
            String embedTitle,
            String embedDescription,
            String embedFooter,
            String embedColor
    ) {

        return messageChannel.sendMessageEmbeds(
                new EmbedBuilder()
                        .setTitle(embedTitle)
                        .setDescription(embedDescription)
                        .setFooter(embedFooter)
                        .setColor(Color.decode(embedColor))
                        .build()
        );
    }

    /**
     * Sends a message through components.
     *
     * @param messageChannel The channel where the components message is supposed to be sent in.
     * @param messageComponents The message content.
     * @return A published message in a specific channel.
     */
    public MessageCreateAction componentsMessage(
            MessageChannel messageChannel,
            List<? extends MessageTopLevelComponent> messageComponents
    ) {

        return messageChannel.sendMessageComponents(messageComponents)
                .useComponentsV2();
    }

    /**
     * Sends a message through components.
     *
     * @param messageChannel The channel where the components message is supposed to be sent in.
     * @param messageContent The message content.
     * @return A published message in a specific channel.
     */
    public MessageCreateAction logFailedMessage(
            MessageChannel messageChannel,
            String messageContent) {

        return this.componentsMessage(
                messageChannel,
                List.of(Container.of(
                        TextDisplay.of("HoneyPot Logging Service"),
                        TextDisplay.of(messageContent)
                        )
                )
        );
    }

    /**
     * Sends a message through components.
     *
     * @param messageChannel The channel where the components message is supposed to be sent in.
     * @param messageContent The message content.
     * @return A published message in a specific channel.
     */
    public MessageCreateAction logSuccessMessage(
            MessageChannel messageChannel,
            String messageContent) {

        return this.componentsMessage(
                messageChannel,
                List.of(Container.of(
                                TextDisplay.of("HoneyPot Logging Service"),
                                TextDisplay.of(messageContent)
                        )
                )
        );
    }


    public void publishWarningMessage(TextChannel textChannel) {

        RestAction<Message> restAction;

        switch (configManager.getHoneyPotMessageFormat().toLowerCase(Locale.ROOT)) {

            case "text" -> restAction = this.textMessage(
                    textChannel,
                    configManager.getHoneyPotMessage()
            );

            case "embed" -> {
                if (configManager.useComponentsFormat()) {
                    restAction = this.componentsMessage(
                            textChannel,
                            List.of(Container.of(TextDisplay.of(
                                    configManager.getHoneyPotEmbedTitle() + "\n" +
                                            configManager.getHoneyPotEmbedDescription()
                                            )
                                    )
                            )
                    );

                } else {

                    restAction = this.embedMessage(
                            textChannel,
                            configManager.getHoneyPotEmbedTitle(),
                            configManager.getHoneyPotEmbedDescription(),
                            configManager.getHoneyPotEmbedFooter(),
                            configManager.getHoneyPotEmbedColor()
                    );
                }
            }

            default -> {
                System.out.println("Invalid HoneyPot message format used. Please use text or embed.");
                return;
            }

        }

        restAction.queue(
                this::saveWarningMessageId,
                onFailure -> System.out.println("Failed to publish HoneyPot warning message: " + onFailure.getMessage())
        );
    }


    private void saveWarningMessageId(Message message) {
        try {
            dataManager.setWarningMessageId(message.getId());
            System.out.println("Successfully published HoneyPot warning message.");
        } catch (IOException ioException) {
            System.out.println("Failed to save warning messageID: " + ioException.getMessage());
        }
    }
}
