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

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.EventUtils;
import io.github.idlebotdevelopment.idlebot.util.IdleBotCommand;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValues;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class AFKModeCommand implements IdleBotCommand {

    @Override
    public String getCommandName() {
        return "afkmode";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot afkmode <auto | manual>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length >= 2 && args[1].equalsIgnoreCase("manual")) {
            if (!IdleBot.getConfigManager().MANUAL_AFK_ENABLED) {
                MessageHelper.sendMessage(player, "You are not allowed to use manual AFK on this server!", MessageLevel.INCORRECT_COMMAND_USAGE);
                return true;
            }
            PersistentDataUtils.setData(player, DataValues.AUTO_AFK.key(), false);
            EventUtils.clearPlayerIdleStats(player);
            MessageHelper.sendMessage(player, "Set your afkmode to manual", MessageLevel.INFO);
            return true;
        } else if (args.length >= 2 && args[1].equalsIgnoreCase("auto")) {
            if (!IdleBot.getConfigManager().AUTO_AFK_ENABLED) {
                MessageHelper.sendMessage(player, "You are not allowed to use auto AFK on this server!", MessageLevel.INCORRECT_COMMAND_USAGE);
                return true;
            }
            PersistentDataUtils.setData(player, DataValues.AUTO_AFK.key(), true);
            PersistentDataUtils.setData(player, DataValues.IS_SET_AFK.key(), false);
            MessageHelper.sendMessage(player, "Set your afkmode to auto", MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
