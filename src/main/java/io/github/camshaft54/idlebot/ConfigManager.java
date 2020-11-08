package io.github.camshaft54.idlebot;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    IdleBot plugin = IdleBot.getPlugin();

    // Private variables for config values
    private String botToken;
    private String activityType;
    private String activityMessage;
    private String channelID;

    // Private variables for config files
    private final File configFile = new File(plugin.getDataFolder(), "config.yml");
    private final FileConfiguration config = plugin.getConfig();

    public ConfigManager() {
        try {
            config.load(configFile);
            config.save(configFile);
        }
        catch (IOException | InvalidConfigurationException e) {
            plugin.disablePlugin();
        }
        this.botToken = config.getString("botToken");
        this.channelID = config.getString("channelID");
        this.activityType = config.getString("customBotActivity.type");
        this.activityMessage = config.getString("customBotActivity.message");
    }

    public String getBotToken() {
        return botToken;
    }

    public String getchannelID() {
        return channelID;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getActivityMessage() {
        return activityMessage;
    }

}
