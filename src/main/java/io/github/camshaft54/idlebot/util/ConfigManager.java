package io.github.camshaft54.idlebot.util;

import io.github.camshaft54.idlebot.IdleBot;
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
    @Getter private final int defaultIdleTime;

    private DynamicConfig config;

    public ConfigManager() throws IOException, ParseException {
        plugin.getDataFolder().mkdirs();
        config = new DynamicConfig();
        config.addSource(IdleBot.class, "config", getConfigFile());
        config.saveAllDefaults();
        config.loadAll();
        botToken = config.getString("botToken");
        channelID = config.getString("channelID");
        activityType = config.getString("customBotActivity.type");
        activityMessage = config.getString("customBotActivity.message");
        defaultIdleTime = config.getInt("defaultIdleTime");
    }
}
