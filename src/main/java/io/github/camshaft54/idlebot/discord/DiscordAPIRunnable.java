package io.github.camshaft54.idlebot.discord;

import io.github.camshaft54.idlebot.IdleBot;
import org.bukkit.plugin.Plugin;
import org.javacord.api.DiscordApiBuilder;

public class DiscordAPIRunnable implements Runnable {
    private final DiscordAPIManager manager;

    public DiscordAPIRunnable(Plugin plugin) {
        this.manager = new DiscordAPIManager(plugin);
    }

    @Override
    public void run() {
        DiscordAPIManager.api = new DiscordApiBuilder().setToken(IdleBot.getConfigManager().getBotToken()).login().join(); // Call #onConnectToDiscord(...) after a successful login
        DiscordAPIManager.api.addListener(new DiscordEvents());
        DiscordAPIManager.bot = DiscordAPIManager.api.getYourself();
        manager.consoleInfo(); // Send some information to the console once the API is functional
        manager.connectToChannel(); // Connect the bot to the text channel specified in config
        manager.setActivity(); // Set the status of the bot in Discord
        manager.setDiscordIsReady();
    }
}
