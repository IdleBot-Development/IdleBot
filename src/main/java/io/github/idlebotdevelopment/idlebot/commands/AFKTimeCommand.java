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

package io.github.idlebotdevelopment.idlebot.commands;

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.*;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class AFKTimeCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "afktime";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot afktime <time in seconds (your server's admin set the minimum and maximum afk time to " + IdleBot.getPlugin().getConfigManager().MINIMUM_IDLE_TIME + " and " + IdleBot.getPlugin().getConfigManager().MAXIMUM_IDLE_TIME + ")>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        ConfigManager configManager = IdleBot.getPlugin().getConfigManager();
        if (!configManager.AUTO_AFK_ENABLED) {
            MessageHelper.sendMessage(player, "You are not allowed to use auto AFK on this server!", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        if (args.length >= 2 && IdleBotUtils.isInteger(args[1]) && Integer.parseInt(args[1]) >= configManager.MINIMUM_IDLE_TIME && Integer.parseInt(args[1]) <= configManager.MAXIMUM_IDLE_TIME) {
            PersistentDataUtils.setData(player, DataValue.AFK_TIME, Integer.parseInt(args[1]));
            MessageHelper.sendMessage(player, "Set your afktime to " + args[1], MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
