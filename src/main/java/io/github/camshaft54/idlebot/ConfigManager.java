package io.github.camshaft54.idlebot;

import lombok.Getter;
import github.scarsz.configuralize.*;
import java.io.File;
import java.io.IOException;

public class ConfigManager {

    IdleBot plugin = IdleBot.getPlugin();
    @Getter private final File configFile = new File(plugin.getDataFolder(), "config.yml");

    // Private variables for config values
    @Getter private final String botToken;
    @Getter private final String activityType;
    @Getter private final String activityMessage;
    @Getter private final String channelID;

    // Private variables for config files
    private DynamicConfig config;

    public ConfigManager() throws IOException, ParseException {
        plugin.getDataFolder().mkdirs();
        config = new DynamicConfig();
        config.addSource(IdleBot.class, "config", getConfigFile());
        config.saveAllDefaults();
        config.loadAll();
        this.botToken = config.getString("botToken");
        this.channelID = config.getString("channelID");
        this.activityType = config.getString("customBotActivity.type");
        this.activityMessage = config.getString("customBotActivity.message");
    }
}
