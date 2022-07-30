/*
 * Copyright (C) 2020-2022 Camshaft54, MetalTurtle18
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.idlebotdevelopment.idlebot.util;

import github.scarsz.configuralize.DynamicConfig;
import github.scarsz.configuralize.ParseException;
import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final DynamicConfig config;

    // Public variables for config values
    public String BOT_TOKEN;
    public String ACTIVITY_TYPE;
    public String ACTIVITY_MESSAGE;
    public String CHANNEL_ID;
    public String DEFAULT_MESSAGE_CHANNEL;
    public String DEFAULT_AFK_MODE;

    public int DEFAULT_IDLE_TIME;
    public int MINIMUM_IDLE_TIME;
    public int MAXIMUM_IDLE_TIME;

    public int DEFAULT_ALERT_REPEAT_TIMEOUT;
    public int MINIMUM_ALERT_REPEAT_TIMEOUT;
    public int MAXIMUM_ALERT_REPEAT_TIMEOUT;

    public boolean PUBLIC_CHANNEL_MESSAGES_ENABLED;
    public boolean PRIVATE_CHANNEL_MESSAGES_ENABLED;
    public boolean AUTO_AFK_ENABLED;
    public boolean MANUAL_AFK_ENABLED;

    public boolean ALERT_AUTO_TIMEOUT_ENABLED;
    public boolean DISCORDSRV_MODE;

    public ConfigManager() throws IOException, ParseException, InvalidConfigurationException {
        IdleBot plugin = IdleBot.getPlugin();
        // Set up config
        //noinspection ResultOfMethodCallIgnored
        plugin.getDataFolder().mkdirs();
        config = new DynamicConfig();
        config.addSource(IdleBot.class, "config", new File(plugin.getDataFolder(), "config.yml"));
        config.saveAllDefaults();
        config.loadAll();

        // Save values into variables
        loadMiscellaneousSettings();
        loadMessageVisibilitySettings();
        loadBotInfo();
        loadAFKSettings();
        loadIdleTimeSettings();
        loadAlertRepeatTimeoutSettings();
    }

    private void loadMiscellaneousSettings() {
        DISCORDSRV_MODE = config.getBooleanElse("discordSRV", false);
    }

    private void loadBotInfo() throws InvalidConfigurationException {
        ACTIVITY_TYPE = config.getStringElse("customBotActivity.type", "watching");
        ACTIVITY_MESSAGE = config.getStringElse("customBotActivity.message", "idle players");
        BOT_TOKEN = config.getStringElse("botToken", "<Bot Token Here>");
        CHANNEL_ID = config.getStringElse("channelID", "<Channel ID Here>");
        if (BOT_TOKEN.equals("<Bot Token Here>") && !DISCORDSRV_MODE)
            invalidateConfig("botToken needs to be set in config.yml");
        if (CHANNEL_ID.equals("<Channel ID Here>") && PUBLIC_CHANNEL_MESSAGES_ENABLED)
            invalidateConfig("channelID needs to be set in config.yml");
    }

    private void loadAFKSettings() throws InvalidConfigurationException {
        AUTO_AFK_ENABLED = config.getBooleanElse("AFKMode.autoAFKEnabled", true);
        MANUAL_AFK_ENABLED = config.getBooleanElse("AFKMode.manualAFKEnabled", true);
        ALERT_AUTO_TIMEOUT_ENABLED = config.getBooleanElse("AFKMode.alertAutoTimeoutEnabled", false);
        DEFAULT_AFK_MODE = config.getStringElse("AFKMode.defaultAFKMode", "auto");
        if ((DEFAULT_AFK_MODE.equals("auto") && !AUTO_AFK_ENABLED) || (DEFAULT_AFK_MODE.equals("manual") && !MANUAL_AFK_ENABLED) || !(DEFAULT_AFK_MODE.equals("auto") || DEFAULT_AFK_MODE.equals("manual")))
            invalidateConfig("AFKMode settings invalid");
    }

    private void loadMessageVisibilitySettings() throws InvalidConfigurationException {
        PUBLIC_CHANNEL_MESSAGES_ENABLED = config.getBooleanElse("messageChannels.publicChannelMessagesEnabled", true);
        PRIVATE_CHANNEL_MESSAGES_ENABLED = config.getBooleanElse("messageChannels.privateChannelMessagesEnabled", true);
        DEFAULT_MESSAGE_CHANNEL = config.getStringElse("messageChannels.defaultMessageChannel", "public");
        if ((DEFAULT_MESSAGE_CHANNEL.equals("public") && !PUBLIC_CHANNEL_MESSAGES_ENABLED) || (DEFAULT_MESSAGE_CHANNEL.equals("private") && !PRIVATE_CHANNEL_MESSAGES_ENABLED) || !(DEFAULT_MESSAGE_CHANNEL.equals("private") || DEFAULT_MESSAGE_CHANNEL.equals("public")))
            invalidateConfig("messageChannels are invalid");
    }

    private void loadIdleTimeSettings() throws InvalidConfigurationException {
        MAXIMUM_IDLE_TIME = config.getIntElse("idleTime.maximumIdleTime", 120);
        MINIMUM_IDLE_TIME = config.getIntElse("idleTime.minimumIdleTime", 20);
        DEFAULT_IDLE_TIME = config.getIntElse("idleTime.defaultIdleTime", 600);
        if (MAXIMUM_IDLE_TIME < MINIMUM_IDLE_TIME || DEFAULT_IDLE_TIME < MINIMUM_IDLE_TIME || DEFAULT_IDLE_TIME > MAXIMUM_IDLE_TIME)
            invalidateConfig("idleTimes are invalid");
    }

    private void loadAlertRepeatTimeoutSettings() throws InvalidConfigurationException {
        MAXIMUM_ALERT_REPEAT_TIMEOUT = config.getIntElse("alertRepeatTimeout.maximumAlertRepeatTimeout", 120);
        MINIMUM_ALERT_REPEAT_TIMEOUT = config.getIntElse("alertRepeatTimeout.minimumAlertRepeatTimeout", 10);
        DEFAULT_ALERT_REPEAT_TIMEOUT = config.getIntElse("alertRepeatTimeout.defaultAlertRepeatTimeout", 20);
        if (MAXIMUM_ALERT_REPEAT_TIMEOUT < MINIMUM_ALERT_REPEAT_TIMEOUT || DEFAULT_ALERT_REPEAT_TIMEOUT < MINIMUM_ALERT_REPEAT_TIMEOUT || DEFAULT_ALERT_REPEAT_TIMEOUT > MAXIMUM_ALERT_REPEAT_TIMEOUT)
            invalidateConfig("alertRepeatTimeouts are invalid");
    }

    private void invalidateConfig(String reason) throws InvalidConfigurationException {
        MessageHelper.sendMessage("Plugin configuration file invalid! " + reason, MessageLevel.FATAL_ERROR);
        throw new InvalidConfigurationException();
    }
}
