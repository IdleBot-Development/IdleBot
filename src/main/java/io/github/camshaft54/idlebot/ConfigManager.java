package io.github.camshaft54.idlebot;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    IdleBot plugin = IdleBot.getPlugin();

    // Private variables for config values
    @Getter private final String botToken;
    @Getter private final String activityType;
    @Getter private final String activityMessage;
    @Getter private final String channelID;

    // Private variables for config files
    private final File configFile = new File(plugin.getDataFolder(), "config.yml");
    private final FileConfiguration config = plugin.getConfig();

    public ConfigManager() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

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
}
