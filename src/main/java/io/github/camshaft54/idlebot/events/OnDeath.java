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

import io.github.camshaft54.idlebot.util.DataValues;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static io.github.camshaft54.idlebot.util.EventsUtil.isIdle;
import static io.github.camshaft54.idlebot.util.EventsUtil.sendPlayerMessage;

public class OnDeath implements Listener {
    // If player has died, send them a message
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (isIdle(player)) {
            Bukkit.getLogger().info(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + player.getDisplayName() + " is idle and dead!");
            sendPlayerMessage(player, player.getDisplayName() + " died at " + locationCleanup(player.getLocation()) + " (" + e.getDeathMessage() + ").", DataValues.DEATH_ALERT.key());
        }
    }

    // Take player's location and return a clean string of coordinates
    private static String locationCleanup(Location l) {
        return "(" + Math.round(l.getX()) + ", " + Math.round(l.getY()) + ", " + Math.round(l.getZ()) + ")";
    }
}
