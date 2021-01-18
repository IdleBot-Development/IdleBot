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

import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.IdleBotCommand;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UnlinkCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "unlink";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot unlink";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (PersistentDataHandler.getStringData(player, DataValues.DISCORD_ID.key()) == null) { // If they are not linked
            Messenger.sendMessage(player, "Your account isn't linked so you can't unlink it!", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        PersistentDataHandler.removeData(player, DataValues.DISCORD_ID.key());
        return true;
    }
}
