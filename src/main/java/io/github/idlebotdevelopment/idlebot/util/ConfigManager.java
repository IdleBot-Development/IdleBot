/*
 *    Copyright (C) 2020-2021 Camshaft54, MetalTurtle18
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 *    This class makes use of some code by "Scarsz" here: https://github.com/Scarsz/Configuralize
 */

package io.github.idlebotdevelopment.idlebot.util;

import github.scarsz.configuralize.DynamicConfig;
import github.scarsz.configuralize.ParseException;
import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final IdleBot plugin;

    // Public variables for config values
    public final String BOT_TOKEN;
    public final String ACTIVITY_TYPE;
    public final String ACTIVITY_MESSAGE;
    public final String CHANNEL_ID;
    public final String DEFAULT_MESSAGE_CHANNEL;
    public final String DEFAULT_AFK_MODE;

    public final int DEFAULT_IDLE_TIME;
    public final int MINIMUM_IDLE_TIME;
    public final int MAXIMUM_IDLE_TIME;

    public final boolean PUBLIC_CHANNEL_MESSAGES_ENABLED;
    public final boolean PRIVATE_CHANNEL_MESSAGES_ENABLED;
    public final boolean AUTO_AFK_ENABLED;
    public final boolean MANUAL_AFK_ENABLED;
    public final boolean DISCORDSRV_MODE;

    public ConfigManager(IdleBot plugin) throws IOException, ParseException {
        this.plugin = plugin;
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        //noinspection ResultOfMethodCallIgnored
        plugin.getDataFolder().mkdirs();
        DynamicConfig config = new DynamicConfig();
        config.addSource(IdleBot.class, "config", configFile);
        config.saveAllDefaults();
        config.loadAll();

        ACTIVITY_TYPE = config.getStringElse("customBotActivity.type", "watching");
        ACTIVITY_MESSAGE = config.getStringElse("customBotActivity.message", "idle players");
        DISCORDSRV_MODE = config.getBooleanElse("discordSRV", false);

        MAXIMUM_IDLE_TIME = config.getIntElse("idleTime.maximumIdleTime", 120);
        MINIMUM_IDLE_TIME = config.getIntElse("idleTime.minimumIdleTime", 20);
        DEFAULT_IDLE_TIME = config.getIntElse("idleTime.defaultIdleTime", 600);
        if (MAXIMUM_IDLE_TIME <= MINIMUM_IDLE_TIME || DEFAULT_IDLE_TIME < MINIMUM_IDLE_TIME || DEFAULT_IDLE_TIME > MAXIMUM_IDLE_TIME)
            invalidateConfig("idleTimes are invalid");

        PUBLIC_CHANNEL_MESSAGES_ENABLED = config.getBooleanElse("messageChannels.publicChannelMessagesEnabled", true);
        PRIVATE_CHANNEL_MESSAGES_ENABLED = config.getBooleanElse("messageChannels.privateChannelMessagesEnabled", true);
        DEFAULT_MESSAGE_CHANNEL = config.getStringElse("messageChannels.defaultMessageChannel", "public");
        if ((DEFAULT_MESSAGE_CHANNEL.equals("public") && !PUBLIC_CHANNEL_MESSAGES_ENABLED) || (DEFAULT_MESSAGE_CHANNEL.equals("private") && !PRIVATE_CHANNEL_MESSAGES_ENABLED) || !(DEFAULT_MESSAGE_CHANNEL.equals("private") || DEFAULT_MESSAGE_CHANNEL.equals("public")))
            invalidateConfig("messageChannels are invalid");

        AUTO_AFK_ENABLED = config.getBooleanElse("AFKMode.autoAFKEnabled", true);
        MANUAL_AFK_ENABLED = config.getBooleanElse("AFKMode.manualAFKEnabled", true);
        DEFAULT_AFK_MODE = config.getStringElse("AFKMode.defaultAFKMode", "auto");
        if ((DEFAULT_AFK_MODE.equals("auto") && !AUTO_AFK_ENABLED) || (DEFAULT_AFK_MODE.equals("manual") && !MANUAL_AFK_ENABLED) || !(DEFAULT_AFK_MODE.equals("auto") || DEFAULT_AFK_MODE.equals("manual")))
            invalidateConfig("AFKMode settings invalid");

        BOT_TOKEN = config.getStringElse("botToken", "<Bot Token Here>");
        CHANNEL_ID = config.getStringElse("channelID", "<Channel ID Here>");
        if (BOT_TOKEN.equals("<Bot Token Here>") && !DISCORDSRV_MODE)
            invalidateConfig("botToken needs to be set in config.yml");
        if (CHANNEL_ID.equals("<Channel ID Here>") && PUBLIC_CHANNEL_MESSAGES_ENABLED)
            invalidateConfig("channelID needs to be set in config.yml");
    }

    private void invalidateConfig(String reason) {
        MessageHelper.sendMessage("Plugin configuration file invalid! " + reason, MessageLevel.FATAL_ERROR);
        plugin.disablePlugin();
    }
}
