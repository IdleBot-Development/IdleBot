package io.github.camshaft54.idlebot;

import io.github.camshaft54.idlebot.events.DiscordEvents;
import org.bukkit.plugin.Plugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;

import java.io.IOException;

public class DiscordAPIRunnable implements Runnable {
    private final Plugin plugin;
    private final DiscordAPIManager manager;

    public DiscordAPIRunnable(Plugin plugin) {
        this.plugin = plugin;
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
