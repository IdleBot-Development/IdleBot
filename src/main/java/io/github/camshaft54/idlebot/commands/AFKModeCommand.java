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

import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class AFKModeCommand implements IdleBotCommand {

    @Override
    public String getCommandName() {
        return "afkmode";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot afkmode <auto|manual>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length >= 2 && args[1].equalsIgnoreCase("manual")) {
            PersistentDataHandler.setData(player, DataValues.AUTO_AFK.key(), false);
            Messenger.sendMessage(player, "Set your afkmode to manual", MessageLevel.INFO);
            return true;
        } else if (args.length >= 2 && args[1].equalsIgnoreCase("auto")) {
            PersistentDataHandler.setData(player, DataValues.AUTO_AFK.key(), true);
            PersistentDataHandler.setData(player, DataValues.IS_SET_AFK.key(), false);
            Messenger.sendMessage(player, "Set your afkmode to auto", MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
