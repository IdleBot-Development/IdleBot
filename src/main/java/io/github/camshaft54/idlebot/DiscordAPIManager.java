package io.github.camshaft54.idlebot;

import org.bukkit.plugin.Plugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;

public class DiscordAPIManager {

    public static DiscordApi api;
    public static org.javacord.api.entity.user.User bot;
    public static ServerTextChannel channel;

    private final Plugin plugin;

    public DiscordAPIManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void consoleInfo() {
        plugin.getLogger().info("[IdleBot] Success! Connected to Discord as " + api.getYourself().getDiscriminatedName());
        plugin.getLogger().info("[IdleBot] Open the following url to invite the bot: " + api.createBotInvite());
    }

    public void connectToChannel() {
        if (api.getServerTextChannelById(IdleBot.getConfigManager().getchannelID()).isPresent()) {
            channel = api.getServerTextChannelById(IdleBot.getConfigManager().getchannelID()).get();
        } else {
            plugin.getLogger().warning("[IdleBot] Invalid Discord channel specified in config");
        }
    }

    public void setActivity() {
        switch (IdleBot.getConfigManager().getActivityType()) {
            case "PLAYING":
                api.updateActivity(ActivityType.PLAYING, IdleBot.getConfigManager().getActivityMessage());
                break;
            case "LISTENING":
                api.updateActivity(ActivityType.LISTENING, IdleBot.getConfigManager().getActivityMessage());
                break;
            case "WATCHING":
                api.updateActivity(ActivityType.WATCHING, IdleBot.getConfigManager().getActivityMessage());
                break;
        }
    }

    public void setDiscordIsReady() {
        IdleBot.discordAPIIsReady = true;
    }

}
