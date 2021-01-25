/*
 *    Copyright (C) 2020 Camshaft54, MetalTurtle18
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

    IdleBot plugin;
    @Getter private final File configFile;

    // Private variables for config values
    public final String BOT_TOKEN;
    public final String ACTIVITY_TYPE;
    public final String ACTIVITY_MESSAGE;
    public final String CHANNEL_ID;
    public final int DEFAULT_IDLE_TIME;
    public final int MINIMUM_IDLE_TIME;
    public final int MAXIMUM_IDLE_TIME;

    private DynamicConfig config;

    public ConfigManager(IdleBot plugin) throws IOException, ParseException {
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder(), "config.yml");
        plugin.getDataFolder().mkdirs();
        config = new DynamicConfig();
        config.addSource(IdleBot.class, "config", getConfigFile());
        config.saveAllDefaults();
        config.loadAll();
        BOT_TOKEN = config.getString("botToken");
        CHANNEL_ID = config.getString("channelID");
        ACTIVITY_TYPE = config.getString("customBotActivity.type");
        ACTIVITY_MESSAGE = config.getString("customBotActivity.message");
        DEFAULT_IDLE_TIME = config.getInt("idleTime.defaultIdleTime");
        MINIMUM_IDLE_TIME = config.getInt("idleTime.minimumIdleTime");
        MAXIMUM_IDLE_TIME = config.getInt("idleTime.maximumIdleTime");
        if (BOT_TOKEN.equals("<Bot Token Here>") || CHANNEL_ID.equals("<Channel ID Here>")) {
            Messenger.sendMessage("Plugin configuration file invalid! botToken and/or channelToken need to be set in config.yml.", MessageLevel.FATAL_ERROR);
            plugin.disablePlugin();
        }
    }
}
