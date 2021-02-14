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

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.MessageHelper;
import io.github.camshaft54.idlebot.util.PersistentDataUtils;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class SetAFKCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "afk";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot afk [true | false]";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (!IdleBot.getConfigManager().MANUAL_AFK_ENABLED) {
            MessageHelper.sendMessage(player, "You are not allowed to use manual AFK on this server!", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        if (args.length >= 2 && args[1].equalsIgnoreCase("true")) {
            PersistentDataUtils.setData(player, DataValues.IS_SET_AFK.key(), true);
            MessageHelper.sendMessage(player, "Set your afk status to true", MessageLevel.INFO);
        } else if (args.length >= 2 && args[1].equalsIgnoreCase("false")) {
            PersistentDataUtils.setData(player, DataValues.IS_SET_AFK.key(), false);
            MessageHelper.sendMessage(player, "Set your afk status to false", MessageLevel.INFO);
        } else {
            PersistentDataUtils.setData(player, DataValues.IS_SET_AFK.key(), !PersistentDataUtils.getBooleanData(player, DataValues.IS_SET_AFK.key()));
            MessageHelper.sendMessage(player, "Set your afk status to " + PersistentDataUtils.getBooleanData(player, DataValues.IS_SET_AFK.key()), MessageLevel.INFO);
        }
        PersistentDataUtils.setData(player, DataValues.AUTO_AFK.key(), false);
        EventUtils.clearPlayerIdleStats(player);
        return true;
    }
}
