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
import io.github.camshaft54.idlebot.util.MessageHelper;
import io.github.camshaft54.idlebot.util.PersistentDataUtils;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class ChannelCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "channel";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot channel <public|private>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length < 2) return false;
        if (args[1].equalsIgnoreCase("public")) {
            PersistentDataUtils.setData(player, DataValues.DIRECT_MESSAGE_MODE.key(), false);
            MessageHelper.sendMessage(player, "Set your alerts channel to public", MessageLevel.INFO);
            return true;
        } else if (args[1].equalsIgnoreCase("private")) {
            PersistentDataUtils.setData(player, DataValues.DIRECT_MESSAGE_MODE.key(), true);
            MessageHelper.sendMessage(player, "Set your alerts channel to private", MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
