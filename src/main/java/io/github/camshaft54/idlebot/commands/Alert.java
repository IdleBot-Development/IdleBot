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

import io.github.camshaft54.idlebot.util.DataValues;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Alert implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "alert";
    }

    @Override
    public void runCommand(Player player, String[] args) {
        String dataKey;
        if (args.length < 2) {
            player.sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.BLUE + "placeholder for the command usage");
            return;
        }
        switch (args[1].toLowerCase()) {
            case "damage":
                dataKey = DataValues.DAMAGE_ALERT.key();
                break;
            case "death":
                dataKey = DataValues.DEATH_ALERT.key();
                break;
            case "location": // This alert has more complicated settings
                locationAlertCommand(player, args);
                return;
            case "xp":
                dataKey = DataValues.EXPERIENCE_ALERT.key();
                break;
            case "inventory":
                dataKey = DataValues.INVENTORY_FULL_ALERT.key();
                break;
            default:
                // WRONG
                return;
        }
        if (args.length < 3) {
            // send message what it is
            return;
        }
        if (args[2].equalsIgnoreCase("true")) {
            PersistentDataHandler.setData(player, dataKey,true);
        } else if (args[2].equalsIgnoreCase("false")) {
            PersistentDataHandler.setData(player, DataValues.DAMAGE_ALERT.key(), false);
        } else {
            player.sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.BLUE + "invalid value for alert " + args[1].toLowerCase() + ". To use this command, type \"/idlebot alert " + args[1].toLowerCase() + " <true/false>\" (where <true/false> is the value you want it to be.");
        }
    }

    private void locationAlertCommand(Player player, String[] args) {
        /* Example command:
         * /idlebot alert location x 500
         * args length: 4
         * axis index: 2
         * coordinate index: 3
         */
        if (args.length < 3 || (!args[2].equalsIgnoreCase("x") && !args[2].equalsIgnoreCase("z"))) {
            // blurb about needing axis (x/z) AND coordinate
        }

    }
}
