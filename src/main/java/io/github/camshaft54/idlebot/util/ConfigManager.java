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

package io.github.camshaft54.idlebot.util;

import github.scarsz.configuralize.DynamicConfig;
import github.scarsz.configuralize.ParseException;
import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    @Getter private final File configFile;

    // Private variables for config values
    public final String BOT_TOKEN;
    public final String ACTIVITY_TYPE;
    public final String ACTIVITY_MESSAGE;
    public final String CHANNEL_ID;
    public final int DEFAULT_IDLE_TIME;
    public final int MINIMUM_IDLE_TIME;
    public final int MAXIMUM_IDLE_TIME;

    public ConfigManager(IdleBot plugin) throws IOException, ParseException {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        //noinspection ResultOfMethodCallIgnored
        plugin.getDataFolder().mkdirs();
        DynamicConfig config = new DynamicConfig();
        config.addSource(IdleBot.class, "config", getConfigFile());
        config.saveAllDefaults();
        config.loadAll();
        BOT_TOKEN = config.getString("botToken");
        CHANNEL_ID = config.getString("channelID");
        if (BOT_TOKEN.equals("<Bot Token Here>") || CHANNEL_ID.equals("<Channel ID Here>"))
            invalidateConfig("botToken and/or channelToken need to be set in config.yml.", plugin);
        ACTIVITY_TYPE = config.getString("customBotActivity.type");
        ACTIVITY_MESSAGE = config.getString("customBotActivity.message");
        String maximumIdleTimeString = config.getString("idleTime.maximumIdleTime");
        if (!CommandUtils.isInteger(maximumIdleTimeString) || Integer.parseInt(maximumIdleTimeString) < 5 || Integer.parseInt(maximumIdleTimeString) > 86400)
            invalidateConfig("Invalid maximumIdleTime.", plugin);
        MAXIMUM_IDLE_TIME = Integer.parseInt(maximumIdleTimeString);
        String minimumIdleTimeString = config.getString("idleTime.minimumIdleTime");
        if (!CommandUtils.isInteger(minimumIdleTimeString) || Integer.parseInt(minimumIdleTimeString) < 5 || Integer.parseInt(minimumIdleTimeString) > 86400)
            invalidateConfig("Invalid minimumIdleTime.", plugin);
        MINIMUM_IDLE_TIME = Integer.parseInt(minimumIdleTimeString);
        String defaultIdleTimeString = config.getString("idleTime.defaultIdleTime");
        if (!CommandUtils.isInteger(defaultIdleTimeString) || Integer.parseInt(defaultIdleTimeString) < MINIMUM_IDLE_TIME || Integer.parseInt(defaultIdleTimeString) > MAXIMUM_IDLE_TIME)
            invalidateConfig("Invalid defaultIdleTime.", plugin);
        DEFAULT_IDLE_TIME = Integer.parseInt(defaultIdleTimeString);
    }

    private void invalidateConfig(String reason, IdleBot plugin) {
        Messenger.sendMessage("Plugin configuration file invalid! " + reason, MessageLevel.FATAL_ERROR);
        plugin.disablePlugin();
    }
}
