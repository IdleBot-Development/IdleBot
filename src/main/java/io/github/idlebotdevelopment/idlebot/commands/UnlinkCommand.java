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

import io.github.idlebotdevelopment.idlebot.util.IdleBotCommand;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValues;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
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
        if (PersistentDataUtils.getStringData(player, DataValues.DISCORD_ID) == null) { // If they are not linked
            MessageHelper.sendMessage(player, "Your account isn't linked so you can't unlink it!", MessageLevel.INCORRECT_COMMAND_USAGE);
            return true;
        }
        PersistentDataUtils.removeAllData(player);
        MessageHelper.sendMessage(player, "Unlinked your Discord username", MessageLevel.INFO);
        return true;
    }
}
