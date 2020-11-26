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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static io.github.camshaft54.idlebot.events.EventsUtil.isPlayerIdle;
import static io.github.camshaft54.idlebot.events.EventsUtil.sendPlayerMessage;

public class XpLevel {
    public static HashMap<Player, Boolean> atExpLevel = new HashMap<>();

    // Checks if player has reached a certain xp level and sends them a message if they have
    public static void xpLevel() {
        for (Player player : IdleChecker.playersIdling.keySet()) {
            //TODO: Replace "10" in if statement with variable for desired xp level set by player
            if (isPlayerIdle(player) && player.getLevel() == 10 && !atExpLevel.get(player)) {
                Bukkit.getLogger().info("[IdleBot]: " + player.getDisplayName() + " is idle and at the desired XP level!");
                sendPlayerMessage(player, player.getDisplayName() + " is at the desired XP level! ");
                atExpLevel.put(player, true);
            }
        }
    }
}
