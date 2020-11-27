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
 */

// This class makes use of some code by "Scarsz" here: https://github.com/Scarsz/Configuralize

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
