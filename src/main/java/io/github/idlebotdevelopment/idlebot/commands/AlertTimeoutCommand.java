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

public class AlertTimeoutCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "alerttimeout";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot alerttimeout [seconds (0 for false)]";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        ConfigManager configManager = IdleBot.getPlugin().getConfigManager();
        if (!configManager.ALERT_AUTO_TIMEOUT_ENABLED) {
            MessageHelper.sendMessage(player, "You are not allowed to change your auto alert timeout settings on this server!", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        if (args.length >= 2 && IdleBotUtils.isInteger(args[1]) && Integer.parseInt(args[1]) >= configManager.MINIMUM_ALERT_REPEAT_TIMEOUT && Integer.parseInt(args[1]) <= configManager.MAXIMUM_ALERT_REPEAT_TIMEOUT) {
            PersistentDataUtils.setData(player, DataValue.ALERT_REPEAT_TIMEOUT, Integer.parseInt(args[1]));
            MessageHelper.sendMessage(player, "Set your auto alert timeout to " + args[1], MessageLevel.INFO);
            return true;
        } else if (args.length >= 2 && IdleBotUtils.isInteger(args[1])) {
            MessageHelper.sendMessage(player, "Your auto alert timeout must be between " + configManager.MINIMUM_ALERT_REPEAT_TIMEOUT + " and " + configManager.MAXIMUM_ALERT_REPEAT_TIMEOUT + " seconds!", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        return false;
    }
}
