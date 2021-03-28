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

package io.github.idlebotdevelopment.idlebot.events;

import io.github.idlebotdevelopment.idlebot.IdleBot;
import io.github.idlebotdevelopment.idlebot.util.IdleBotUtils;
import io.github.idlebotdevelopment.idlebot.util.IdleCheck;
import io.github.idlebotdevelopment.idlebot.util.MessageHelper;
import io.github.idlebotdevelopment.idlebot.util.PersistentDataUtils;
import io.github.idlebotdevelopment.idlebot.util.enums.DataValue;
import io.github.idlebotdevelopment.idlebot.util.enums.MessageLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class XLocationReached implements IdleCheck {
    @Override
    public DataValue getDataValue() {
        return DataValue.LOCATION_ALERT_X;
    }

    // Sends a player a message if they have reached their desired location
    public void check(Player player) {
        String direction = PersistentDataUtils.getStringData(player, DataValue.LOCATION_X_DIRECTION);
        int desiredLocation = PersistentDataUtils.getIntData(player, DataValue.LOCATION_X_DESIRED);
        double playerLocation = player.getLocation().getX();
        if (direction != null && !IdleBot.getEventManager().locationReachedPlayersX.contains(player) && ((direction.equals("e") && playerLocation >= desiredLocation) || (direction.equals("w") && playerLocation <= desiredLocation))) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + player.getDisplayName() + " is idle and reached the desired X coordinate!");
            MessageHelper.sendMessage(player.getDisplayName() + " is idle and reached the desired Z coordinate!", MessageLevel.INFO);
            IdleBotUtils.sendPlayerMessage(player, player.getDisplayName() + " has reached the desired X coordinate!", player.getDisplayName() + " reached the desired location!");
            IdleBot.getEventManager().locationReachedPlayersX.add(player);
        }
    }
}
