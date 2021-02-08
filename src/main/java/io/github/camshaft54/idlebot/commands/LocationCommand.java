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

package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.CommandUtils;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
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
                    PersistentDataHandler.setData(player, DataValues.LOCATION_X_DIRECTION.key(), "e");
                } else {
                    PersistentDataHandler.setData(player, DataValues.LOCATION_X_DIRECTION.key(), "w");
                }
                PersistentDataHandler.setData(player, DataValues.LOCATION_X_DESIRED.key(), coord);
            } else {
                if ((int) player.getLocation().getZ() < coord) {
                    PersistentDataHandler.setData(player, DataValues.LOCATION_Z_DIRECTION.key(), "s");
                } else {
                    PersistentDataHandler.setData(player, DataValues.LOCATION_Z_DIRECTION.key(), "n");
                }
                PersistentDataHandler.setData(player, DataValues.LOCATION_Z_DESIRED.key(), coord);
            }
            Messenger.sendMessage(player, "Set your desired location to " + args[1].toLowerCase() + "=" + args[2], MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
