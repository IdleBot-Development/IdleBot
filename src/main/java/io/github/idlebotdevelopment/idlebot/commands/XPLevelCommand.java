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

import io.github.idlebotdevelopment.idlebot.util.CommandUtils;
import io.github.idlebotdevelopment.idlebot.util.IdleBotCommand;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValues;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.entity.Player;

public class XPLevelCommand implements IdleBotCommand {
    @Override
    public String getCommandName() {
        return "xplevel";
    }

    @Override
    public String getCommandUsage() {
        return "/idlebot xplevel <desired xp level>";
    }

    @Override
    public boolean runCommand(Player player, String[] args) {
        if (args.length >= 2 && CommandUtils.isInteger(args[1]) && Integer.parseInt(args[1]) > 0) {
            PersistentDataUtils.setData(player, DataValues.EXPERIENCE_LEVEL_DESIRED.key(), Integer.parseInt(args[1]));
            MessageHelper.sendMessage(player, "Set your desired XP level to " + args[1], MessageLevel.INFO);
            return true;
        }
        return false;
    }
}
