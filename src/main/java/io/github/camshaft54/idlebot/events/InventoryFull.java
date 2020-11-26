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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static io.github.camshaft54.idlebot.util.EventsUtil.isIdle;
import static io.github.camshaft54.idlebot.util.EventsUtil.sendPlayerMessage;

public class InventoryFull {
    public static HashMap<Player, Boolean> isFull = new HashMap<>();

    // Checks if players inventory is full and sends them a message if it is
    public static void inventoryFull() {
        for (Player player : IdleBot.idlePlayers.keySet()) {
            if (isIdle(player) && player.getInventory().firstEmpty() < 0 && !isFull.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and their inventory is full!");
                sendPlayerMessage(player, player.getDisplayName() + "'s inventory is full! ");
                isFull.put(player, true);
            }
        }
    }
}
