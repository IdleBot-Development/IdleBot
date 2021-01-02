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
import io.github.camshaft54.idlebot.util.DataValues;
import io.github.camshaft54.idlebot.util.EventUtils;
import io.github.camshaft54.idlebot.util.IdleCheck;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class LocationReached implements IdleCheck {

    @Override
    public String getDataValue() {
        return DataValues.LOCATION_ALERT.key();
    }

    // Sends a player a message if they have reached their desired location
    public void check(Player player) {
        // Convert location from string to int array
        // TODO: change this to the new location checks (settings also need to be added)
        String desiredLocationString = PersistentDataHandler.getStringData(player, DataValues.LOCATION_DESIRED.key());
        if (desiredLocationString == null)
            return;
        int[] desiredLocation = Arrays.stream(desiredLocationString.split("\\s+")).mapToInt(Integer::parseInt).toArray();

        boolean reachedLocation = player.getLocation().getBlockX() == desiredLocation[0] && player.getLocation().getBlockY() == desiredLocation[1] && player.getLocation().getBlockZ() == desiredLocation[2];
        if (EventUtils.isIdle(player) && reachedLocation && !IdleBot.getEventManager().locationReachedPlayers.contains(player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + player.getDisplayName() + " is idle and they reached their desired location!");
            EventUtils.sendPlayerMessage(player, player.getDisplayName() + "'s reached the desired location! ");
            IdleBot.getEventManager().locationReachedPlayers.add(player);
        }
    }
}
