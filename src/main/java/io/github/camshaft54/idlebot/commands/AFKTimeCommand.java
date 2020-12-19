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

package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.DataValues;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AFKTimeCommand extends IdleBotCommand {
    @Override
    public String getCommandName() {
        return "afktime";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.DARK_PURPLE + "[IdleBot]: " + ChatColor.AQUA + "to use this command, enter \"/idlebot afktime <time in seconds>\" (without the <>).");
            return;
        }
        try {
            if (Integer.parseInt(args[2]) >= IdleBot.getConfigManager().getMinIdleTime() && Integer.parseInt(args[2]) <= IdleBot.getConfigManager().getMaxIdleTime()) {
                PersistentDataHandler.setData(player, DataValues.AFK_TIME.key(), Integer.parseInt(args[2]));
            } else {
                // Say it has to be 10 seconds to 2 minutes
                return;
            }
        } catch (NumberFormatException nfe) {
            // Send the player message about needs to be "<number>"
            return;
        }
    }
}
