package io.github.tomthebuddhacat.listener;

import io.github.tomthebuddhacat.config.Config;
import io.github.tomthebuddhacat.manager.MessageManager;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {

    private Config config;

    public ReadyListener(Config config) {
        this.config = config;
    }

    @Override
    public void onReady(ReadyEvent readyEvent) {

        MessageManager messageManager =
                new MessageManager(readyEvent.getJDA(), config);

        // Publish the configured warning message once the bot starts successfully
        messageManager.sendWarningMessage();
    }
}
