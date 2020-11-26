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
import io.github.camshaft54.idlebot.util.EventsUtil;
import io.github.camshaft54.idlebot.util.PersistentDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class IdleChecker implements Runnable {

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PersistentDataHandler.getStringData(player, "discordId") != null) {
                if (IdleBot.idlePlayers.containsKey(player)) { // If player is already in the hashmap
                    IdleBot.idlePlayers.put(player, IdleBot.idlePlayers.get(player) + 1); // Add 1 to the player's value
                }
                else {
                    // Put new player into the playersIdling hashmap with value 0 (0 seconds)
                    IdleBot.idlePlayers.put(player, 0);
                }
                if (EventsUtil.isIdle(player)) { // If player has been idle for time specified in config
                    Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle.");
                }
            }
        }
    }
}
