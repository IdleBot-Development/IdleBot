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

import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
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
        return "/idlebot afk <true|false>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length >= 2 && args[1].equalsIgnoreCase("true")) {
            PersistentDataHandler.setData(player, DataValues.IS_SET_AFK.key(), true);
            Messenger.sendMessage(player, "Set your afk status to true", MessageLevel.INFO);
            return true;
        } else if (args.length >= 2 && args[1].equalsIgnoreCase("false")) {
            PersistentDataHandler.setData(player, DataValues.IS_SET_AFK.key(), false);
            EventUtils.clearPlayerIdleStats(player);
            Messenger.sendMessage(player, "Set your afk status to false", MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
