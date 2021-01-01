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
import io.github.camshaft54.idlebot.util.IdleCheck;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static io.github.camshaft54.idlebot.util.EventUtils.sendPlayerMessage;

public class InventoryFull implements IdleCheck {
    public void check() {
        for (Player player : PersistentDataHandler.getIdlePlayerSetWithCertainValue(DataValues.INVENTORY_FULL_ALERT.key(), true)) {
            if (player.getInventory().firstEmpty() < 0 && !IdleBot.getEventManager().inventoryFullPlayers.contains(player)) {
                Bukkit.getLogger().info(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + player.getDisplayName() + " is idle and their inventory is full!");
                sendPlayerMessage(player, player.getDisplayName() + "'s inventory is full! ");
                IdleBot.getEventManager().inventoryFullPlayers.add(player);
            }
        }
    }
}
