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
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class IdleChecker implements Runnable {

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PersistentDataHandler.getStringData(player, DataValues.DISCORD_ID.key()) != null) {
                /* If the player:
                 * Is not already idle
                 * Is in the list of idle players (not in auto mode)`
                 */
                if (!EventUtils.isIdle(player) && IdleBot.idlePlayers.containsKey(player)) {
                    IdleBot.idlePlayers.put(player, IdleBot.idlePlayers.get(player) + 1); // Increase by one
                }
                /* If the player:
                 * Is not already idle
                 * Has auto afk mode on
                 */
                else if (!EventUtils.isIdle(player) && PersistentDataHandler.getBooleanData(player, DataValues.AUTO_AFK.key())) {
                    IdleBot.idlePlayers.put(player, 0);
                }
                // Debugging only; TODO: remove this
                if (EventUtils.isIdle(player)) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[IdleBot] " + ChatColor.AQUA + player.getDisplayName() + " is idle.");
                }
            }
        }
    }
}
