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
import io.github.camshaft54.idlebot.util.CommandUtils;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import org.bukkit.entity.Player;

public class AFKTimeCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "afktime";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot afktime <time in seconds (your server's admin set the minimum and maximum afk time to " + IdleBot.getConfigManager().MINIMUM_IDLE_TIME + " and " + IdleBot.getConfigManager().MAXIMUM_IDLE_TIME + ")>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length >= 2 && CommandUtils.isInteger(args[1]) && Integer.parseInt(args[1]) >= IdleBot.getConfigManager().MINIMUM_IDLE_TIME && Integer.parseInt(args[1]) <= IdleBot.getConfigManager().MAXIMUM_IDLE_TIME) {
            PersistentDataHandler.setData(player, DataValues.AFK_TIME.key(), Integer.parseInt(args[1]));
            return true;
        }
        return false;
    }
}
