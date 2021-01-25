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

package io.github.camshaft54.idlebot.events;

import io.github.camshaft54.idlebot.IdleBot;
import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleCheck;
import io.github.camshaft54.idlebot.util.Messenger;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import io.github.camshaft54.idlebot.util.enums.DataValues;
import io.github.camshaft54.idlebot.util.enums.MessageLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class XLocationReached implements IdleCheck {
    @Override
    public String getDataValue() {
        return DataValues.LOCATION_ALERT_X.key();
    }

    // Sends a player a message if they have reached their desired location
    public void check(Player player) {
        String direction = PersistentDataHandler.getStringData(player, DataValues.LOCATION_X_DIRECTION.key());
        int desiredLocation = PersistentDataHandler.getIntData(player, DataValues.LOCATION_X_DESIRED.key());
        double playerLocation = player.getLocation().getX();
        if (direction != null && !IdleBot.getEventManager().locationReachedPlayersX.contains(player) && ((direction.equals("e") && playerLocation >= desiredLocation) || (direction.equals("w") && playerLocation <= desiredLocation))) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + player.getDisplayName() + " is idle and reached the desired X coordinate!");
            Messenger.sendMessage(player.getDisplayName() + " is idle and reached the desired Z coordinate!", MessageLevel.INFO);
            EventUtils.sendPlayerMessage(player, player.getDisplayName() + " has reached the desired X coordinate! ");
            IdleBot.getEventManager().locationReachedPlayersX.add(player);
        }
    }
}
