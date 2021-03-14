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
 */

package io.github.idlebotdevelopment.idlebot.commands;

import io.github.idlebotdevelopment.idlebot.util.CommandUtils;
import io.github.idlebotdevelopment.idlebot.util.IdleBotCommand;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class LocationCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "location";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot location <x|z> <coordinate (must be between -50,000,000 and 50,000,000)>";
    }

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    public boolean runCommand(Player player, String[] args) {
        if (args.length < 3 || (!args[1].equalsIgnoreCase("x") && !args[1].equalsIgnoreCase("z"))) {
            return false;
        }
        if (CommandUtils.isInteger(args[2]) && Integer.parseInt(args[2]) >= -50_000_000 && Integer.parseInt(args[2]) <= 50_000_000) {
            int coord = Integer.parseInt(args[2]);
            if (args[1].equalsIgnoreCase("x")) {
                if ((int) player.getLocation().getX() < coord) {
                    PersistentDataUtils.setData(player, DataValue.LOCATION_X_DIRECTION, "e");
                } else {
                    PersistentDataUtils.setData(player, DataValue.LOCATION_X_DIRECTION, "w");
                }
                PersistentDataUtils.setData(player, DataValue.LOCATION_X_DESIRED, coord);
            } else {
                if ((int) player.getLocation().getZ() < coord) {
                    PersistentDataUtils.setData(player, DataValue.LOCATION_Z_DIRECTION, "s");
                } else {
                    PersistentDataUtils.setData(player, DataValue.LOCATION_Z_DIRECTION, "n");
                }
                PersistentDataUtils.setData(player, DataValue.LOCATION_Z_DESIRED, coord);
            }
            MessageHelper.sendMessage(player, "Set your desired location to " + args[1].toLowerCase() + "=" + args[2], MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
